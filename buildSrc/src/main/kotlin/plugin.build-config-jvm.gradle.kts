plugins {
  kotlin("jvm")
  id("plugin.common")
  id("com.github.gmazzo.buildconfig")
}

buildConfig {
  useKotlinOutput {
    internalVisibility = true
    topLevelConstants = true
  }
  packageName("dev.petuska.dekorator.plugin.config")
  buildConfigField("String", "GROUP", "\"${rootProject.group}\"")
  buildConfigField("String", "NAME", "\"${rootProject.name}\"")
  buildConfigField("String", "VERSION", "\"${rootProject.version}\"")
  buildConfigField(
      "String",
      "GRADLE_PLUGIN_ARTEFACT_ID",
      "\"${project(":plugin:dekorator-gradle-plugin").name}\"")
  buildConfigField(
      "String",
      "KOTLIN_PLUGIN_ARTEFACT_ID",
      "\"${project(":plugin:dekorator-kotlin-plugin").name}\"")
  buildConfigField(
      "String", "KOTLIN_NATIVE_PLUGIN_ARTEFACT_ID", "\"\$KOTLIN_PLUGIN_ARTEFACT_ID-native\"")
  buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"\$GROUP.\$KOTLIN_PLUGIN_ARTEFACT_ID\"")
}
