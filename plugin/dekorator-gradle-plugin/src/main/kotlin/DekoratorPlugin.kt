package dev.petuska.dekorator.plugin

import dev.petuska.dekorator.plugin.config.GROUP
import dev.petuska.dekorator.plugin.config.KOTLIN_NATIVE_PLUGIN_ARTEFACT_ID
import dev.petuska.dekorator.plugin.config.KOTLIN_PLUGIN_ARTEFACT_ID
import dev.petuska.dekorator.plugin.config.KOTLIN_PLUGIN_ID
import dev.petuska.dekorator.plugin.config.VERSION
import dev.petuska.dekorator.plugin.util.DekoratorOption
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

/** A kotlin gradle sub-plugin to manage dekorator kotlin compiler plugin */
class DekoratorPlugin : KotlinCompilerPluginSupportPlugin {
  override fun apply(target: Project) {
    with(target) {
      val extension = createExtension()
      tasks.withType(KotlinCompile::class.java) {
        it.inputs.property("dekorator.enabled", "${extension.enabled}")
      }
    }
  }

  private fun Project.createExtension() =
      extensions.findByType(DekoratorExtension::class.java)
          ?: extensions.create(
              DekoratorExtension.NAME, DekoratorExtension::class.java, this@createExtension)

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean =
      kotlinCompilation.target.project.plugins.hasPlugin(DekoratorPlugin::class.java)

  override fun getCompilerPluginId(): String = KOTLIN_PLUGIN_ID

  override fun getPluginArtifact(): SubpluginArtifact =
      SubpluginArtifact(
          groupId = GROUP,
          artifactId = KOTLIN_PLUGIN_ARTEFACT_ID,
          version = VERSION,
      )

  override fun getPluginArtifactForNative(): SubpluginArtifact =
      SubpluginArtifact(
          groupId = GROUP,
          artifactId = KOTLIN_NATIVE_PLUGIN_ARTEFACT_ID,
          version = VERSION,
      )

  override fun applyToCompilation(
      kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {
    val project = kotlinCompilation.target.project
    val extension = project.dekorator

    return project.provider {
      listOf(
          SubpluginOption(
              key = DekoratorOption.Enabled.name,
              lazyValue = lazy { extension.enabled.toString() }),
      )
    }
  }
}
