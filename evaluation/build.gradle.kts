@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.detekt.gradle)
}

dependencies {
    api(libs.tuprolog.unify)
    api(libs.tuprolog.solve.classic)

    implementation(libs.kotlin.stdlib)
    implementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    implementation("junit:junit:4.13.1")
    testImplementation(libs.bundles.kotlin.testing)
    api(project(":framework"))
    api(project(":explanation"))
    api(project(":dsl"))
    api(project(":domain"))
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = false
                freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        showCauses = true
        showStackTraces = true
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config = files("${rootDir.path}/config/detekt.yml")
    source = files(kotlin.sourceSets.map { it.kotlin.sourceDirectories })
}
