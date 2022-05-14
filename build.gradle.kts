import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("io.kotest") version "0.3.8"
}

group = "me.giuliabrugnatti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.12.3")
    implementation("it.unibo.tuprolog:unify-jvm:0.20.4")
    compileOnly("io.kotest:kotest-framework-api-jvm:5.3.0")
    implementation("io.kotest:kotest-framework-engine-jvm:5.3.0")
    testImplementation("junit:junit:4.13")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
