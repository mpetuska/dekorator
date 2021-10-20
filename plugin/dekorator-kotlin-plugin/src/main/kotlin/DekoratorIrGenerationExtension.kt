package dev.petuska.dekorator.plugin

import dev.petuska.dekorator.plugin.transformer.DekoratorFnIrTransformer
import dev.petuska.dekorator.plugin.util.DekoratorSettings
import org.jetbrains.kotlin.backend.common.FileLoweringPass
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid
import org.jetbrains.kotlin.ir.visitors.acceptVoid
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.util.Logger

/** An orchestrator to manage processing flows via transformers. */
class DekoratorIrGenerationExtension(
    private val settings: DekoratorSettings,
    private val logger: Logger,
) : IrGenerationExtension {
  private val dekoratorFqName = FqName("dev.petuska.dekorator.core.Dekorator")

  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    val dekoratorClass = pluginContext.referenceClass(dekoratorFqName)
    if (settings.enabled && dekoratorClass != null) {

      val transformer =
          DekoratorFnIrTransformer(
              context = pluginContext,
              logger = logger,
              settings = settings,
              klipContextClass = dekoratorClass,
          )
      for (file in moduleFragment.files) {
        transformer.runOnFileInOrder(file)
      }
    } else {
      logger.log(
          "Dekorator plugin disabled [enabled=${settings.enabled}, DekoratorClass=$dekoratorClass]")
    }
  }

  private fun FileLoweringPass.runOnFileInOrder(irFile: IrFile) {
    irFile.acceptVoid(
        object : IrElementVisitorVoid {
          override fun visitElement(element: IrElement) {
            element.acceptChildrenVoid(this)
          }

          override fun visitFile(declaration: IrFile) {
            lower(declaration)
            super.visitFile(declaration)
          }
        })
  }
}
