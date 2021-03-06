plugins { `kotlin-dsl` }

repositories {
  mavenCentral()
  google()
}

dependencies {
  //  implementation("io.kotest:kotest-framework-multiplatform-plugin-gradle:_")
  implementation("com.android.tools.build:gradle:_")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:_")
  implementation("dev.petuska:klip-gradle-plugin:_")
  implementation("org.jetbrains.kotlin:kotlin-serialization:_")
  implementation("com.diffplug.spotless:spotless-plugin-gradle:_")
  implementation("io.github.gradle-nexus:publish-plugin:_")
  implementation("com.github.gmazzo:gradle-buildconfig-plugin:_")
}
