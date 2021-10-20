package dev.petuska.dekorator.plugin.util

import org.jetbrains.kotlin.config.CompilerConfigurationKey

/**
 * Internal-use class containing information about command line options passed by gradle plugin to
 * kotlin plugin
 */
sealed class DekoratorOption<T>(
    val name: String,
    val valueDescription: String,
    val description: String,
) {
  val key: CompilerConfigurationKey<T> = CompilerConfigurationKey(name)

  /** Toggles the compiler processing on/off */
  object Enabled :
      DekoratorOption<Boolean>(
          name = "enabled",
          valueDescription = "<true|false>",
          description = "whether the plugin is enabled",
      )
}
