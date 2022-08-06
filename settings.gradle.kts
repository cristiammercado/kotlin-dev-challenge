import enforcer.rules.RequireGradleVersion
import enforcer.rules.RequireJavaVersion
import org.kordamp.gradle.plugin.enforcer.BuildEnforcerPlugin
import org.kordamp.gradle.plugin.enforcer.api.BuildEnforcerExtension

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.kordamp.gradle:enforcer-gradle-plugin:0.10.0")
    }
}
apply<BuildEnforcerPlugin>()

configure<BuildEnforcerExtension> {
    val minimalGradleVersion = "7"
    val minimalJavaVersion = "17"

    rule(RequireGradleVersion::class.java) {
        this.version.set(minimalGradleVersion)
        this.message.set("Please, check Gradle version is $minimalGradleVersion")
    }
    rule(RequireJavaVersion::class.java) {
        this.version.set(minimalJavaVersion)
        this.message.set("Please, check Java version is $minimalJavaVersion")
    }
}

rootProject.name = "api"
