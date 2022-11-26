@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.detekt.gradle)
    alias(libs.plugins.kover.gradle)
}

dependencies {
    api(libs.tuprolog.unify)
    api(libs.tuprolog.solve.classic)

    implementation(libs.kotlin.stdlib)
    testImplementation(libs.bundles.kotlin.testing)

    api(project(":framework"))
    api(project(":dsl"))
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config = files("${rootDir.path}/config/detekt.yml")
    source = files(kotlin.sourceSets.map { it.kotlin.sourceDirectories })
}
