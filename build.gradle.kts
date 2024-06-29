plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "me.levansj01"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.elmakers.com/repository/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public")
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    implementation("io.github.classgraph:classgraph:4.6.32")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")
        archiveClassifier.set(classifier)
    }
}