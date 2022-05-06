import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("io.kotest") version "0.3.8"
}

group = "me.giuliabrugnatti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    //testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("io.mockk:mockk:1.12.3")
    //testImplementation("io.kotest:kotest-assertions-core-jvm:$version")
    //testImplementation("io.kotest:kotest-framework-engine-jvm:$version")
    compileOnly("io.kotest:kotest-framework-api-jvm:4.4.3")
    implementation("io.kotest:kotest-framework-engine-jvm:4.4.3")
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}