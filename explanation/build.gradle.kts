import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("io.kotest") version "0.3.9"

    id("pl.droidsonroids.jacoco.testkit") version "1.0.9"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    // id("io.gitlab.arturbosch.detekt") version "1.21.0"
    id("org.jetbrains.dokka") version "1.7.10"
}

group = "me.giuliabrugnatti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.mockk:mockk:1.12.5")

    implementation("it.unibo.tuprolog:unify-jvm:0.20.9")
    implementation("it.unibo.tuprolog:solve-classic-jvm:0.20.9")
    testImplementation("io.kotest:kotest-framework-api-jvm:5.4.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.4.1")

    // detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.10")

    api(project(":framework"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
    useJUnitPlatform() // Use JUnit 5 engine
    testLogging.showStandardStreams = true
    testLogging {
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
    }
}

// Disable JaCoCo on Windows, see: https://issueexplorer.com/issue/koral--/jacoco-gradle-testkit-plugin/9
tasks.jacocoTestCoverageVerification {
    enabled = !org.apache.tools.ant.taskdefs.condition.Os.isFamily(
        org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS
    )
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = false // true solo se vuoi morire
    }
}
/*
detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    config = files(File(projectDir, "config/detekt.yml"))
}

 */

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}
