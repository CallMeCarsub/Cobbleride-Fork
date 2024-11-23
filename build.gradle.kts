plugins {
    id("java")
    id("java-library")
    kotlin("jvm") version("1.9.23")

    id("dev.architectury.loom") version("1.7-SNAPSHOT") apply false
    id("architectury-plugin") version("3.4-SNAPSHOT") apply false
}

allprojects {
    group = "${rootProject.property("group")}"
    version = "${rootProject.property("mod_version")}"

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        maven("https://maven.impactdev.net/repository/development/")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

//subprojects {
//    java {
//        // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
//        // if it is present.
//        // If you remove this line, sources will not be generated.
//        withSourcesJar()
//
//        sourceCompatibility = JavaVersion.VERSION_21
//        targetCompatibility = JavaVersion.VERSION_21
//    }
//}

