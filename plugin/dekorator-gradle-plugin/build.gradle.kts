plugins {
  kotlin("kapt")
  id("com.gradle.plugin-publish")
  `java-gradle-plugin`
  id("plugin.publishing-jvm")
  id("plugin.build-config-jvm")
}

description = """Gradle plugin to manage dekorations, processors and dependencies"""

java { withSourcesJar() }

gradlePlugin {
  plugins {
    create(name) {
      id = "$group.dekorator"
      displayName = "Kotlin decorators support"
      description = project.description
      implementationClass = "$id.plugin.DekoratorPlugin"
    }
  }
}

pluginBundle {
  website = "https://github.com/mpetuska/dekorator"
  vcsUrl = "https://github.com/mpetuska/dekorator.git"
  tags = listOf("multiplatform", "test", "kotlin", "snapshots")
}

kotlin {
  sourceSets {
    main { dependencies { compileOnly(kotlin("gradle-plugin-api")) } }

    test {
      dependencies {
        implementation(kotlin("gradle-plugin-api"))
        implementation(kotlin("test-junit5"))
      }
    }
  }
}
