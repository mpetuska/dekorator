pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
  }
}

plugins {
  id("de.fayard.refreshVersions") version "0.40.2"
  id("com.gradle.enterprise") version "3.7.1"
}

rootProject.name = "dekorator"

include(
    ":library:dekorator-core",
    ":plugin:dekorator-gradle-plugin",
    ":plugin:dekorator-kotlin-plugin",
    ":plugin:dekorator-kotlin-plugin-native",
)
