import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
  id("dev.petuska.klip")
  id("dev.petuska.dekorator")
  kotlin("js")
}

kotlin {
  js {
    useCommonJs()
    nodejs()
  }
  sourceSets {
    main { dependencies { implementation("dev.petuska:dekorator") } }
    test {
      dependencies {
        implementation("dev.petuska:klip:_")
        implementation(kotlin("test-js"))
      }
    }
  }
}

tasks {
  val mainPluginSourceSets = {
    rootProject.extensions.getByType(KotlinMultiplatformExtension::class).sourceSets
  }
  fun Sync.registerSources(sourceSet: KotlinSourceSet, root: File) {
    destinationDir = root
    from(sourceSet.kotlin.sourceDirectories) { into("kotlin") }
    from(sourceSet.resources) { into("resources") }
  }

  val syncSourceMain by
      registering(Sync::class) {
        registerSources(mainPluginSourceSets()["commonMain"], projectDir.resolve("src/main"))
      }
  named("compileKotlinJs") { dependsOn(syncSourceMain) }
  val syncSourceTest by
      registering(Sync::class) {
        registerSources(mainPluginSourceSets()["commonTest"], projectDir.resolve("src/test"))
      }
  named("compileTestKotlinJs") { dependsOn(syncSourceTest) }
}
