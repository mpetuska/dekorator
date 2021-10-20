package dev.petuska.dekorator.plugin

import dev.petuska.dekorator.plugin.delegate.propertyDelegate
import dev.petuska.dekorator.plugin.util.DekoratorOption
import org.gradle.api.Project

/** Gradle extension to manage dekorator plugin properties */
open class DekoratorExtension(project: Project) {
  /** Whether plugin is enabled */
  var enabled: Boolean by project.propertyDelegate(default = DekoratorOption.Enabled.default) {
    it.toBoolean()
  }

  companion object {
    /** Extension name */
    const val NAME = dev.petuska.dekorator.plugin.config.NAME
  }
}

/**
 * Dekorator plugin extension
 * @throws IllegalStateException if the plugin did not register an extension yet
 */
val Project.dekorator: DekoratorExtension
  get() =
      extensions.findByType(DekoratorExtension::class.java)
          ?: throw IllegalStateException("${DekoratorExtension.NAME} is not of the correct type")

/**
 * Configure dekorator plugin extension
 * @throws IllegalStateException if the plugin did not register an extension yet
 */
internal fun Project.dekorator(config: DekoratorExtension.() -> Unit): DekoratorExtension =
    dekorator.apply(config)
