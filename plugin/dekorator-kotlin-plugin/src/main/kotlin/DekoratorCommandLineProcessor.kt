package dev.petuska.dekorator.plugin

import com.google.auto.service.AutoService
import dev.petuska.dekorator.plugin.config.KOTLIN_PLUGIN_ID
import dev.petuska.dekorator.plugin.util.DekoratorOption
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CliOptionProcessingException
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Command line processor responsible for registering and retrieving values passed by gradle plugin
 */
@AutoService(CommandLineProcessor::class)
class DekoratorCommandLineProcessor : CommandLineProcessor {
  override val pluginId: String = KOTLIN_PLUGIN_ID

  override val pluginOptions: Collection<CliOption> =
      setOf(
          CliOption(
              optionName = DekoratorOption.Enabled.name,
              valueDescription = DekoratorOption.Enabled.valueDescription,
              description = DekoratorOption.Enabled.description,
              required = true),
      )

  override fun processOption(
      option: AbstractCliOption,
      value: String,
      configuration: CompilerConfiguration
  ) {
    when (option.optionName) {
      DekoratorOption.Enabled.name ->
          configuration.put(DekoratorOption.Enabled.key, value.toBoolean())
      else -> throw CliOptionProcessingException("Unknown option: ${option.optionName}")
    }
  }
}
