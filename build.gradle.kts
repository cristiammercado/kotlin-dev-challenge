import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import java.lang.Integer.parseInt

plugins {
    jacoco
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
    kotlin("kapt") version "1.7.10"
}

group = "com.mcontigo"
version = File("VERSION").readText(Charsets.UTF_8)
description = "MContigo API"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

extra["springCloudVersion"] = "2021.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("io.github.openfeign:feign-httpclient:11.9.1")
    implementation("org.mapstruct:mapstruct:1.5.2.Final")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.github.tomakehurst:wiremock:2.27.2")
    testImplementation("com.h2database:h2")
    testImplementation("net.datafaker:datafaker:1.5.0")

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
        allWarningsAsErrors = true
    }
}

tasks.withType<Test> {
	useJUnitPlatform()
    testLogging {
        events = setOf(PASSED, SKIPPED, FAILED)
        showExceptions = true
        exceptionFormat = TestExceptionFormat.FULL
    }
	maxParallelForks = 10
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.register("upgradeVersion") {
    group = PublishingPlugin.PUBLISH_TASK_GROUP
    description = "Upgrade application version"

    doLast {
        val currentVersion = File("VERSION").readText(Charsets.UTF_8)

        if (currentVersion.endsWith("SNAPSHOT")) {
            logger.info("Current version: $currentVersion, deleting SNAPSHOT and upgrade version...")
            val newVersion = currentVersion.substring(0, currentVersion.indexOf("SNAPSHOT") - 1)
            logger.info("New version: $newVersion")
            File("VERSION").writeText(newVersion, Charsets.UTF_8)
        } else {
            logger.info("Current version: $currentVersion, adding SNAPSHOT and upgrade version...")
            val parts = currentVersion.split(".").toMutableList()

            if (parseInt(parts[2]) == 9) {
                parts[2] = "0"

                if (parseInt(parts[1]) == 9) {
                    parts[1] = "0"
                    parts[0] = (parseInt(parts[0]) + 1).toString()
                } else {
                    parts[1] = (parseInt(parts[1]) + 1).toString()
                }
            } else {
                parts[2] = (parseInt(parts[2]) + 1).toString()
            }

            val newVersion = "${parts.joinToString(separator = ".")}-SNAPSHOT"
            logger.info("New version: $newVersion")
            File("VERSION").writeText(newVersion, Charsets.UTF_8)
        }

    }
}
