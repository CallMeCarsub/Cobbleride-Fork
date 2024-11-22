plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.properties["minecraft_version"]}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-language-kotlin:1.12.3+kotlin.2.0.21")
    modImplementation("net.fabricmc:fabric-loader:${rootProject.properties["fabric_loader_version"]}")

    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${rootProject.properties["fabric_api_version"]}")
    modImplementation(fabricApi.module("fabric-command-api-v2", "${rootProject.properties["fabric_api_version"]}"))
    modImplementation("com.cobblemon:fabric:${rootProject.properties["cobblemon_version"]}")

    implementation(project(":common", configuration = "namedElements"))
    "developmentFabric"(project(":common", configuration = "namedElements"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(project.properties)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}