@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.tuprolog.unify)
    api(libs.tuprolog.solve.classic)

    implementation(libs.kotlin.stdlib)
    testImplementation(libs.bundles.kotlin.testing)

    api(project(":framework"))
    api(project(":dsl"))
}
