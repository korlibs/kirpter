package sample

import com.google.auto.service.*
import com.soywiz.kirpter.api.*
import org.jetbrains.kotlin.backend.common.*
import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.interpreter.*

@Suppress("unused")
@AutoService(KirpterPlugin::class)
class SampleKirpterPlugin : KirpterPlugin {
	override fun createGeneratorExtension(context: KirpterContext): IrGenerationExtension? =
		SampleIrGenerationExtension(context)
}

class SampleIrGenerationExtension(
	val context: KirpterContext
) : IrGenerationExtension {
	override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
		SampleTransformer(pluginContext, context).visitModuleFragment(moduleFragment)
	}
}

class SampleTransformer(
	private val pluginContext: IrPluginContext,
	private val context: KirpterContext,
) : IrElementTransformerVoidWithContext() {
	override fun <T> visitConst(expression: IrConst<T>): IrExpression {
		if (expression.type == pluginContext.irBuiltIns.stringType && expression.value == "Hello") {
			return "World".toIrConst(pluginContext.irBuiltIns.stringType)
		}

		return super.visitConst(expression)
	}
}
