package dev.petuska.dekorator.plugin.test

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import dev.petuska.dekorator.plugin.DekoratorCommandLineProcessor
import dev.petuska.dekorator.plugin.DekoratorComponentRegistrar
import dev.petuska.dekorator.plugin.config.KOTLIN_PLUGIN_ID
import dev.petuska.dekorator.plugin.util.DekoratorOption
import java.io.File

object Compiler {
  val kotlinRoot = File("build/tmp/src/commonMain/kotlin")
  const val enabled = "true"
  const val update = "false"
  val klipAnnotations = listOf("test.Klippable", "test.CustomKlippable")
  val scopeAnnotations = listOf("test.Test", "test.CustomTest")

  private val annotationsFile =
      SourceFile.kotlin(
          "annotations.kt",
          """
    package test
    annotation class Klippable
    annotation class CustomKlippable
    annotation class Test
    annotation class CustomTest
    """.trimIndent())

  fun compile(
      vararg sourceFiles: SourceFile,
  ): KotlinCompilation.Result {
    return KotlinCompilation()
        .apply {
          sources = sourceFiles.toList() + annotationsFile
          useIR = true
          compilerPlugins = listOf(DekoratorComponentRegistrar())
          commandLineProcessors = listOf(DekoratorCommandLineProcessor())
          inheritClassPath = true
          workingDir = kotlinRoot
          pluginOptions =
              listOf(
                  PluginOption(KOTLIN_PLUGIN_ID, DekoratorOption.Enabled.name, enabled),
              )
        }
        .compile()
  }
}
