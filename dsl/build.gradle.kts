@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
}

dependencies {
    api(libs.tuprolog.unify)
    api(libs.tuprolog.solve.classic)

    implementation(libs.kotlin.stdlib)
    testImplementation(libs.bundles.kotlin.testing)

    api(project(":framework"))
    testImplementation(project(":domain"))
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = true
                freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
            }
        }
    }
}

@Suppress("SpreadOperator")
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
