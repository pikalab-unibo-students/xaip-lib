plugins {
    kotlin("jvm") version "1.7.10"
    id("io.kotest") version "0.3.9"
}

group = "me.giuliabrugnatti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("it.unibo.tuprolog:unify-jvm:0.20.9")
    implementation("it.unibo.tuprolog:solve-classic-jvm:0.20.9")
    testImplementation("io.kotest:kotest-framework-api-jvm:5.4.2")
    testImplementation("io.kotest:kotest-runner-junit5:5.4.2")
}
