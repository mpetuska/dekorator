[![Slack chat](https://img.shields.io/badge/kotlinlang-chat-green?logo=slack&style=flat-square)](https://kotlinlang.slack.com/team/UL1A5BA2X)
[![Dokka docs](https://img.shields.io/badge/docs-dokka-orange?style=flat-square)](http://mpetuska.github.io/dekorator)
[![Version gradle-plugin-portal](https://img.shields.io/maven-metadata/v?label=gradle%20plugin%20portal&style=flat-square&logo=gradle&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fdev.petuska%2Fdekorator-gradle-plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/dev.petuska.dekorator)
[![Version maven-central](https://img.shields.io/maven-central/v/dev.petuska/dekorator-gradle-plugin?logo=apache-maven&style=flat-square)](https://mvnrepository.com/artifact/dev.petuska/dekorator-gradle-plugin/latest)

# DEKORATOR [WIP]
Decorator support for Kotlin! Built with ❤, powered by Kotlin compiler plugin.

# Support
The plugin only works on targets using new IR kotlin compiler (which is pretty much all of them since kotlin 1.5 except
JS that still defaults to legacy compiler).

# Versions
The current version was built using the following tooling versions and is guaranteed to work with this setup. Given the
experimental nature of kotlin compiler plugin API, the plugin powering this library is likely to stop working on
projects using newer/older kotlin versions.
* Kotlin: `1.5.31`
* Gradle: `7.2.0`
* JDK: `11`

# Targets
Bellow is a list of currently supported targets and planned targets:
- [x] android
- [x] js
- [x] jvm
- [x] linuxX64
- [x] mingwX64
- [x] macosX64
- [x] macosArm64
- [x] iosArm32
- [x] iosArm64
- [x] iosSimulatorArm64
- [x] iosX64
- [x] watchosX86
- [x] watchosX64
- [x] watchosArm64
- [x] watchosSimulatorArm64
- [x] watchosArm32
- [x] tvosArm64
- [x] tvosSimulatorArm64
- [x] tvosX64
- [x] androidNativeArm32
- [x] androidNativeArm64
- [x] linuxArm32Hfp
- [x] linuxMips32
- [x] linuxMipsel32
- [x] linuxArm64
- [x] mingwX86

# Usage
1. Apply the plugin and add a runtime dependency.
```kotlin
plugins {
  kotlin("multiplatform")
  id("dev.petuska.dekorator") version "_"

  kotlin {
    sourceSets {
      commonTest {
        dependencies {
          implementation("dev.petuska:dekorator:_")
        }
      }
    }
  }
}
```
2. (Optional) Configure the plugin extension (shown with default values). For property descriptions.
   see [Gradle Properties](#gradle-properties)
```kotlin
dekorator {
  enabled = true // Turns the compiler plugin on/off
}
```

## Gradle Properties
Most of the DSL configuration options can also be set/overridden via gradle properties
`./gradlew <some-task> -Pprop.name=propValue`, `gradle.properties` or `~/.gradle/gradle.properties`. Environment
variables are also supported, however gradle properties take precedence over them. Bellow is the full list of supported
properties:
* `dekorator.enabled (DEKORATOR_ENABLED)` - toggles the compiler processing on/off.

# Modules
* `:library:dekorator-core` - main runtime library
* `:plugin:dekorator-gradle-plugin` - gradle plugin to manage kotlin compiler plugins
* `:plugin:dekorator-kotlin-plugin` - kotlin compiler plugin for jvm & js that does the actual work
* `:plugin:dekorator-kotlin-plugin:dekorator-kotlin-plugin-native` - kotlin compiler plugin for native that does the
  actual work
* `sandbox` - a playground to test local changes from consumer end
