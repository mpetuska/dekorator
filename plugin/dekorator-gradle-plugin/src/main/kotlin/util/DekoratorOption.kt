package dev.petuska.dekorator.plugin.util

/**
 * Internal-use class containing information about command line options passed by gradle plugin to
 * kotlin plugin
 */
sealed class DekoratorOption<T>(
    val name: String,
    val default: T,
) {

  /** Toggles the compiler processing on/off */
  object Enabled :
      DekoratorOption<Boolean>(
          name = "enabled",
          default = true,
      )
}
