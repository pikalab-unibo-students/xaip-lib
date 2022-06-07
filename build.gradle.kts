import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("io.kotest") version "0.3.9"
}

group = "me.giuliabrugnatti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.mockk:mockk:1.12.4")

    implementation("it.unibo.tuprolog:unify-jvm:0.20.4")
    implementation("it.unibo.tuprolog:solve-classic-jvm:0.20.4")
    testImplementation("io.kotest:kotest-framework-api-jvm:5.3.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
