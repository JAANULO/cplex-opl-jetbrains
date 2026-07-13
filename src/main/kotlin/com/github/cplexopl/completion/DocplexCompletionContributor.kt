package com.github.cplexopl.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiFile
import com.intellij.util.ProcessingContext

class DocplexCompletionContributor : CompletionContributor() {

    init {
        // Active only in Python files
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            DocplexCompletionProvider()
        )
    }
}

class DocplexCompletionProvider : CompletionProvider<CompletionParameters>() {

    // doopl and docplex methods with descriptions
    private val docplexMethods = listOf(
        "create_opl_model"  to "create_opl_model(model='m.mod', data='d.dat')",
        "run"               to "opl.run()",
        "report"            to "opl.report",
        "set_input"         to "opl.set_input('name', dataframe)",
        "continuous_var"    to "mdl.continuous_var(lb=0, ub=1e30, name='x')",
        "integer_var"       to "mdl.integer_var(lb=0, ub=100, name='n')",
        "binary_var"        to "mdl.binary_var(name='b')",
        "minimize"          to "mdl.minimize(expr)",
        "maximize"          to "mdl.maximize(expr)",
        "add_constraint"    to "mdl.add_constraint(ct, name='ct_name')",
        "solve"             to "mdl.solve(log_output=True)"
    )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val file = parameters.originalFile
        if (file.language.id != "Python") return
        
        if (!hasDocplexImport(file)) return

        docplexMethods.forEach { (method, signature) ->
            result.addElement(
                LookupElementBuilder.create(method)
                    .withTypeText("doopl/docplex")
                    .withTailText(" $signature", true)
                    .bold()
            )
        }
    }

    private fun hasDocplexImport(file: PsiFile): Boolean {
        val text = file.text
        return text.contains("from doopl") || text.contains("import doopl") ||
               text.contains("from docplex") || text.contains("import docplex")
    }
}
