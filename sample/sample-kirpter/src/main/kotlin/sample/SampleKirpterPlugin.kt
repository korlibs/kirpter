package sample

import com.soywiz.kirpter.api.*
import org.jetbrains.kotlin.backend.common.*
import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.incremental.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.interpreter.*

class SampleKirpterPlugin : KirpterPlugin {
	override fun createGeneratorExtension(context: KirpterContext): IrGenerationExtension? =
		SampleIrGenerationExtension(context)
}

class SampleIrGenerationExtension(
	val context: KirpterContext
) : IrGenerationExtension {
	override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
		//context.messageCollector.report(CompilerMessageSeverity.ERROR, "Kotlin version: ${KotlinVersion.CURRENT}")
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
