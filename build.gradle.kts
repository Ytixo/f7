plugins {
    kotlin("jvm") version "2.3.0"
    application
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "iut.info1"
version = "1.0"

repositories {
    // 1. Dépôt public officiel sur internet (indispensable depuis chez toi)
    mavenCentral()

    // 2. Dépôt de l'IUT (sera ignoré chez toi mais fonctionnera à l'IUT)

    maven {
        url = uri("http://nexus.dep-info.iut-nantes.univ-nantes.prive/repository/public/")
        isAllowInsecureProtocol = true
    }


}

dependencies {
    // Bibliothèque Flip7 fournie sous forme de .jar (cf. libs/iut.info1.flip7-<version>.jar).
    implementation(fileTree("libs") { include("iut.info1.flip7-*.jar") })
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.4")
    testImplementation("io.mockk:mockk:1.14.9")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}
