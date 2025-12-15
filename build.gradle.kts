import com.hypherionmc.modpublisher.properties.CurseEnvironment
import com.hypherionmc.modpublisher.properties.ModLoader

plugins {
    idea
    `java-library`
    kotlin("jvm") version "2.1.0"
    id("net.neoforged.moddev") version "2.0.107"
    id("com.hypherionmc.modutils.modpublisher") version "2.1.6"
}

val modId: String by project
val modVersion: String by project
val modGroupId: String by project
val modName: String by project
val mcVersion: String by project
val minecraftVersionRange: String by project
val neoforgeVersion: String by project
val neoforgeVersionRange: String by project
val loaderVersionRange: String by project
val parchmentMappingsVersion: String by project
val parchmentMinecraftVersion: String by project

version = modVersion
group = modGroupId

base {
    archivesName = modId
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)
kotlin.jvmToolchain(21)

neoForge {
    version = neoforgeVersion

    parchment {
        mappingsVersion = parchmentMappingsVersion
        minecraftVersion = parchmentMinecraftVersion
    }

    runs {
        create("client") {
            client()
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            data()
            programArguments.addAll(
                "--mod",
                modId,
                "--all",
                "--output",
                file("src/generated/resources/").absolutePath,
                "--existing",
                file("src/main/resources/").absolutePath
            )
        }

        configureEach {
            gameDirectory.set(file("run-$name"))
            systemProperty("forge.logging.markers", "REGISTRIES")
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

val localRuntime: Configuration by configurations.creating

configurations {
    configurations.named("runtimeClasspath") {
        extendsFrom(localRuntime)
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
        content { includeGroup("thedarkcolour") }
    }
    maven("https://maven.ithundxr.dev/snapshots")
    maven("https://maven.createmod.net")
    maven("https://modmaven.dev")
}

dependencies {
    val kotlinForForgeVersion = "5.10.0"
    val flywheelVersion = "1.0.5"
    val registrateVersion = "MC1.21-1.3.0+66"
    val kotlinCoroutine = "1.10.2"

    implementation("thedarkcolour:kotlinforforge-neoforge:$kotlinForForgeVersion")
    compileOnly("dev.engine-room.flywheel:flywheel-neoforge-api-${mcVersion}:${flywheelVersion}")
    runtimeOnly("dev.engine-room.flywheel:flywheel-neoforge-${mcVersion}:${flywheelVersion}")
    implementation("com.tterrag.registrate:Registrate:${registrateVersion}")
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinCoroutine}")
}

/*
mixin {
    add(sourceSets.main.get(), "${modId}.refmap.json")
    config("${modId}.mixins.json")
}

tasks.jar {
    manifest.attributes(mapOf(
        "MixinConfigs" to "${modId}.mixins.json"
    ))
}
*/

publisher {
    apiKeys {
        curseforge(System.getenv("CURSE_FORGE_API_KEY"))
        modrinth(System.getenv("MODRINTH_API_KEY"))
    }

    curseID.set("")
    modrinthID.set("")
    versionType.set("release")
    changelog.set(file("changelog.md"))
    version.set(project.version.toString())
    displayName.set("$modName $modVersion")
    setGameVersions(mcVersion)
    setLoaders(ModLoader.NEOFORGE)
    setCurseEnvironment(CurseEnvironment.BOTH)
    artifact.set("build/libs/${base.archivesName.get()}-${project.version}.jar")

    curseDepends {
        required("kotlin-for-forge")
    }
    modrinthDepends {
        required("kotlin-for-forge")
    }
}

tasks.withType<ProcessResources>().configureEach {
    val modLicense: String by project
    val modAuthors: String by project
    val modDescription: String by project

    val replaceProperties = mapOf(
        "minecraftVersion" to mcVersion,
        "minecraftVersionRange" to minecraftVersionRange,
        "neoforgeVersion" to neoforgeVersion,
        "neoforgeVersionRange" to neoforgeVersionRange,
        "loaderVersionRange" to loaderVersionRange,
        "modId" to modId,
        "modName" to modName,
        "modLicense" to modLicense,
        "modVersion" to modVersion,
        "modAuthors" to modAuthors,
        "modDescription" to modDescription,
    )

    inputs.properties(replaceProperties)
    filesMatching(listOf("META-INF/neoforge.mods.toml")) {
        expand(replaceProperties)
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")
neoForge.ideSyncTask(tasks.processResources)

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
