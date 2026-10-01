package com.github.cplexopl.completion

import com.github.cplexopl.OplLanguage
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import com.github.cplexopl.OplBundle

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
        "int" to OplBundle.message("completion.keyword.int"),
        "int+" to OplBundle.message("completion.keyword.int_plus"),
        "float" to OplBundle.message("completion.keyword.float"),
        "float+" to OplBundle.message("completion.keyword.float_plus"),
        "boolean" to OplBundle.message("completion.keyword.boolean"),
        "string" to OplBundle.message("completion.keyword.string"),
        "range" to OplBundle.message("completion.keyword.range"),

        // Decision variables & expressions
        "dvar" to OplBundle.message("completion.keyword.dvar"),
        "dexpr" to OplBundle.message("completion.keyword.dexpr"),

        // Optimization objective
        "minimize" to OplBundle.message("completion.keyword.minimize"),
        "maximize" to OplBundle.message("completion.keyword.maximize"),

        // Constraints
        "subject to" to OplBundle.message("completion.keyword.subject.to"),
        "constraints" to OplBundle.message("completion.keyword.constraints"),
        "constraint" to OplBundle.message("completion.keyword.constraint"),
        "forall" to OplBundle.message("completion.keyword.forall"),
        "exists" to OplBundle.message("completion.keyword.exists"),

        // Operators & aggregates
        "sum" to OplBundle.message("completion.keyword.sum"),
        "prod" to OplBundle.message("completion.keyword.prod"),
        "all" to OplBundle.message("completion.keyword.all"),
        "setof" to OplBundle.message("completion.keyword.setof"),
        "union" to OplBundle.message("completion.keyword.union"),
        "inter" to OplBundle.message("completion.keyword.inter"),
        "diff" to OplBundle.message("completion.keyword.diff"),
        "symdiff" to OplBundle.message("completion.keyword.symdiff"),
        "div" to OplBundle.message("completion.keyword.div"),
        "mod" to OplBundle.message("completion.keyword.mod"),

        // Structures
        "tuple" to OplBundle.message("completion.keyword.tuple"),
        "struct" to OplBundle.message("completion.keyword.struct"),
        "key" to OplBundle.message("completion.keyword.key"),
        "execute" to OplBundle.message("completion.keyword.execute"),
        "main" to OplBundle.message("completion.keyword.main"),
        "include" to OplBundle.message("completion.keyword.include"),
        "assert" to OplBundle.message("completion.keyword.assert"),

        // Engine & control
        "in" to OplBundle.message("completion.keyword.in"),
        "using" to OplBundle.message("completion.keyword.using"),
        "with" to OplBundle.message("completion.keyword.with"),
        "CP" to OplBundle.message("completion.keyword.cp"),
        "CPLEX" to OplBundle.message("completion.keyword.cplex"),
        "if" to OplBundle.message("completion.keyword.if"),
        "then" to OplBundle.message("completion.keyword.then"),
        "else" to OplBundle.message("completion.keyword.else"),

        // CP & Scheduling
        "interval" to OplBundle.message("completion.keyword.interval"),
        "sequence" to OplBundle.message("completion.keyword.sequence"),
        "cumulFunction" to OplBundle.message("completion.keyword.cumulFunction"),
        "stateFunction" to OplBundle.message("completion.keyword.stateFunction"),
        "stepFunction" to OplBundle.message("completion.keyword.stepFunction"),
        "piecewise" to OplBundle.message("completion.keyword.piecewise"),
        "pwlFunction" to OplBundle.message("completion.keyword.pwlFunction"),
        "stepwise" to OplBundle.message("completion.keyword.stepwise"),
        "size" to OplBundle.message("completion.keyword.size"),
        "optional" to OplBundle.message("completion.keyword.optional"),
        "intensity" to OplBundle.message("completion.keyword.intensity"),
        "types" to OplBundle.message("completion.keyword.types"),

        // I/O & Database
        "SheetConnection" to OplBundle.message("completion.keyword.SheetConnection"),
        "SheetRead" to OplBundle.message("completion.keyword.SheetRead"),
        "SheetWrite" to OplBundle.message("completion.keyword.SheetWrite"),
        "DBConnection" to OplBundle.message("completion.keyword.DBConnection"),
        "DBRead" to OplBundle.message("completion.keyword.DBRead"),
        "DBExecute" to OplBundle.message("completion.keyword.DBExecute"),
        "DBUpdate" to OplBundle.message("completion.keyword.DBUpdate"),
        "prepare" to OplBundle.message("completion.keyword.prepare"),
        "invoke" to OplBundle.message("completion.keyword.invoke"),

        // Ordering & Constants
        "ordered" to OplBundle.message("completion.keyword.ordered"),
        "sorted" to OplBundle.message("completion.keyword.sorted"),
        "reversed" to OplBundle.message("completion.keyword.reversed"),
        "infinity" to OplBundle.message("completion.keyword.infinity"),
        "maxint" to OplBundle.message("completion.keyword.maxint"),
        "true" to OplBundle.message("completion.keyword.true"),
        "false" to OplBundle.message("completion.keyword.false")
    )

    // CPLEX OPL built-in functions
    private val builtinFunctions = listOf(
        "abs" to OplBundle.message("completion.func.abs"),
        "ceil" to OplBundle.message("completion.func.ceil"),
        "floor" to OplBundle.message("completion.func.floor"),
        "round" to OplBundle.message("completion.func.round"),
        "sqrt" to OplBundle.message("completion.func.sqrt"),
        "log" to OplBundle.message("completion.func.log"),
        "exp" to OplBundle.message("completion.func.exp"),
        "max" to OplBundle.message("completion.func.max"),
        "min" to OplBundle.message("completion.func.min"),
        "card" to OplBundle.message("completion.func.card"),
        "ord" to OplBundle.message("completion.func.ord"),
        "item" to OplBundle.message("completion.func.item"),
        "first" to OplBundle.message("completion.func.first"),
        "last" to OplBundle.message("completion.func.last"),
        "ftoi" to OplBundle.message("completion.func.ftoi"),
        "itof" to OplBundle.message("completion.func.itof"),
        "rand" to OplBundle.message("completion.func.rand"),
        "srand" to OplBundle.message("completion.func.srand"),
        "trunc" to OplBundle.message("completion.func.trunc"),
        "ln" to OplBundle.message("completion.func.ln"),
        "log10" to OplBundle.message("completion.func.log10"),
        "sin" to OplBundle.message("completion.func.sin"),
        "cos" to OplBundle.message("completion.func.cos"),
        "tan" to OplBundle.message("completion.func.tan"),
        "asin" to OplBundle.message("completion.func.asin"),
        "acos" to OplBundle.message("completion.func.acos"),
        "atan" to OplBundle.message("completion.func.atan"),
        "allDifferent" to OplBundle.message("completion.func.allDifferent"),
        "pack" to OplBundle.message("completion.func.pack"),
        "pulse" to OplBundle.message("completion.func.pulse"),
        "step" to OplBundle.message("completion.func.step"),
        "startOf" to OplBundle.message("completion.func.startOf"),
        "endOf" to OplBundle.message("completion.func.endOf"),
        "lengthOf" to OplBundle.message("completion.func.lengthOf"),
        "sizeOf" to OplBundle.message("completion.func.sizeOf"),
        "presenceOf" to OplBundle.message("completion.func.presenceOf"),
        "noOverlap" to OplBundle.message("completion.func.noOverlap"),
        "startBeforeEnd" to OplBundle.message("completion.func.startBeforeEnd"),
        "endBeforeStart" to OplBundle.message("completion.func.endBeforeStart"),
        "startAtEnd" to OplBundle.message("completion.func.startAtEnd"),
        "endAtStart" to OplBundle.message("completion.func.endAtStart"),
        "startAtStart" to OplBundle.message("completion.func.startAtStart"),
        "endAtEnd" to OplBundle.message("completion.func.endAtEnd")
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

        // --- Semantic scanning of declarations from PSI tree ---
        val file = parameters.originalFile
        val declaredVariables = mutableSetOf<String>()

        // Helper function: extracts ID only from checked declaration nodes
        fun extractId(psiElement: com.intellij.psi.PsiElement) {
            psiElement.node.findChildByType(com.github.cplexopl.psi.OplTypes.ID)?.text?.let { declaredVariables.add(it) }
        }

        // Get only nodes that are formal declarations
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplVarDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplDvarDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplDexprDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplTupleDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplConstraintDeclaration::class.java).forEach { extractId(it) }
        com.intellij.psi.util.PsiTreeUtil.findChildrenOfType(file, com.github.cplexopl.psi.OplPiecewiseDeclaration::class.java).forEach { extractId(it) }

        // Add confirmed variables to autocomplete results
        declaredVariables.forEach { variable ->
            if (!variable.contains("IntellijIdeaRulezzz")) {
                result.addElement(
                    LookupElementBuilder.create(variable)
                        .withIcon(com.intellij.icons.AllIcons.Nodes.Variable)
                        .withTypeText(OplBundle.message("completion.local.variable"))
                )
            }
        }
    }
}
