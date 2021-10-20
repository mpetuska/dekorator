package dev.petuska.dekorator.plugin

import com.google.auto.service.AutoService
import dev.petuska.dekorator.plugin.util.DekoratorOption
import dev.petuska.dekorator.plugin.util.DekoratorSettings
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.cli.js.messageCollectorLogger
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Component Registrar responsible for validating command line inputs and registering required IR
 * extensions
 */
@AutoService(ComponentRegistrar::class)
class DekoratorComponentRegistrar : ComponentRegistrar {
  override fun registerProjectComponents(
      project: MockProject,
      configuration: CompilerConfiguration
  ) {
    val messageCollector =
        configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
    IrGenerationExtension.registerExtension(
        project,
        DekoratorIrGenerationExtension(
            settings =
                DekoratorSettings(
                    enabled = configuration[DekoratorOption.Enabled.key] == true,
                ),
            logger = messageCollectorLogger(messageCollector),
        ))
  }
}
