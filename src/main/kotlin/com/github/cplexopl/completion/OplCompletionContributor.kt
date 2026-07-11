package com.github.cplexopl.completion

import com.github.cplexopl.OplLanguage
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

// CompletionContributor = class adding autocomplete hints (Ctrl+Space)
// LookupElementBuilder = builder for hint list element

class OplCompletionContributor : CompletionContributor() {

    init {
        // Register hint provider for OPL language
        // PlatformPatterns.psiElement() = pattern for matching cursor location
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            OplKeywordCompletionProvider()
        )
    }
}

class OplKeywordCompletionProvider : CompletionProvider<CompletionParameters>() {

    // Keywords with descriptions - will appear on hint list
    private val keywords = listOf(
        // Data types
        "int" to "Integer type",
        "float" to "Float type",
        "boolean" to "Boolean type",
        "string" to "String type",
        "range" to "Range type (e.g. range R = 1..10)",

        // Decision variables
        "dvar" to "Decision variable",
        "dexpr" to "Decision expression",

        // Optimization objective
        "minimize" to "Minimize objective function",
        "maximize" to "Maximize objective function",

        // Constraints
        "subject to" to "Constraint section",
        "forall" to "Universal quantifier",
        "exists" to "Existential quantifier",

        // Operators
        "sum" to "Summation operator",
        "all" to "All elements",

        // Structures
        "tuple" to "Tuple type definition",
        "execute" to "Scripting block",
        "include" to "Include another file",
        "assert" to "Assertion check",

        // Other
        "in" to "Membership / range operator",
        "using" to "Using constraint programming",
        "with" to "With clause"
    )

    // CPLEX OPL built-in functions
    private val builtinFunctions = listOf(
        "abs" to "Absolute value",
        "ceil" to "Ceiling function",
        "floor" to "Floor function",
        "round" to "Round function",
        "sqrt" to "Square root",
        "log" to "Natural logarithm",
        "exp" to "Exponential function",
        "max" to "Maximum value",
        "min" to "Minimum value",
        "card" to "Cardinality (set size)"
    )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        // Add keywords (bold = bold because these are keywords)
        keywords.forEach { (keyword, description) ->
            result.addElement(
                LookupElementBuilder.create(keyword)
                    .withTypeText(description)
                    .bold()
            )
        }

        // Add built-in functions
        builtinFunctions.forEach { (func, description) ->
            result.addElement(
                LookupElementBuilder.create(func)
                    .withTypeText(description)
                    .withTailText("()", true)  // Add () as hint
            )
        }

// --- TASK 2.2 PRO: Semantic scanning of declarations from PSI tree ---
        val file = parameters.originalFile
        val declaredVariables = mutableSetOf<String>()

        // Helper function: extracts ID only from checked declaration nodes
        fun extractId(psiElement: com.intellij.psi.PsiElement) {
            psiElement.node.findChildByType(com.github.cplexopl.psi.OplTypes.ID)?.text?.let { declaredVariables.add(it) }
        }

        // Get only nodes that are formal declarations
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplVarDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplDvarDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplTupleDeclaration::class.java).forEach { extractId(it) }

        // Add confirmed variables to autocomplete results
        declaredVariables.forEach { variable ->
            if (!variable.contains("IntellijIdeaRulezzz")) {
                result.addElement(
                    LookupElementBuilder.create(variable)
                        .withIcon(com.intellij.icons.AllIcons.Nodes.Variable)
                        .withTypeText("Local Variable")
                )
            }
        }
    }
}
