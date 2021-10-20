plugins {
  id("plugin.library-mpp")
  id("plugin.publishing-mpp")
}

description = "Kotlin multiplatform dekorators support. Runtime dependency."

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(kotlin("test"))
        api(kotlin("test-annotations-common"))
      }
    }
    all { languageSettings { optIn("kotlin.contracts.ExperimentalContracts") } }
  }
}
