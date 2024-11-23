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

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.wispforest.io/releases/")
    maven("https://cursemaven.com") {
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${rootProject.property("fabric_api_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${rootProject.property("fabric_lang_kotl_version")}")

    modImplementation("com.cobblemon:fabric:${rootProject.property("cobblemon_version")}")

    modImplementation("io.wispforest:owo-lib:0.12.15+1.21")
    annotationProcessor("io.wispforest:owo-lib:0.12.15+1.21")

    implementation(project(":common", configuration = "namedElements"))
    "developmentFabric"(project(":common", configuration = "namedElements"))

    modRuntimeOnly("curse.maven:keybind-fix-640307:3860971")
    modRuntimeOnly("curse.maven:modmenu-308702:5810603")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(inputs.properties)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}