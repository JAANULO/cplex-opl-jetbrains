package com.github.cplexopl.completion

import com.github.cplexopl.OplLanguage
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

// CompletionContributor = klasa dodająca podpowiedzi do autouzupełniania (Ctrl+Space)
// LookupElementBuilder = budowniczy elementu na liście podpowiedzi

class OplCompletionContributor : CompletionContributor() {

    init {
        // Rejestrujemy dostawcę podpowiedzi dla języka OPL
        // PlatformPatterns.psiElement() = wzorzec dopasowania miejsca kursora
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(OplLanguage),
            OplKeywordCompletionProvider()
        )
    }
}

class OplKeywordCompletionProvider : CompletionProvider<CompletionParameters>() {

    // Słowa kluczowe z opisami - pojawią się na liście podpowiedzi
    private val keywords = listOf(
        // Typy danych
        "int" to "Integer type",
        "float" to "Float type",
        "boolean" to "Boolean type",
        "string" to "String type",
        "range" to "Range type (e.g. range R = 1..10)",

        // Zmienne decyzyjne
        "dvar" to "Decision variable",
        "dexpr" to "Decision expression",

        // Cel optymalizacji
        "minimize" to "Minimize objective function",
        "maximize" to "Maximize objective function",

        // Ograniczenia
        "subject to" to "Constraint section",
        "forall" to "Universal quantifier",
        "exists" to "Existential quantifier",

        // Operacje
        "sum" to "Summation operator",
        "all" to "All elements",

        // Struktury
        "tuple" to "Tuple type definition",
        "execute" to "Scripting block",
        "include" to "Include another file",
        "assert" to "Assertion check",

        // Inne
        "in" to "Membership / range operator",
        "using" to "Using constraint programming",
        "with" to "With clause"
    )

    // Funkcje wbudowane CPLEX OPL
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
        // Dodaj słowa kluczowe (bold = pogrubione, bo to słowa kluczowe)
        keywords.forEach { (keyword, description) ->
            result.addElement(
                LookupElementBuilder.create(keyword)
                    .withTypeText(description)
                    .bold()
            )
        }

        // Dodaj funkcje wbudowane
        builtinFunctions.forEach { (func, description) ->
            result.addElement(
                LookupElementBuilder.create(func)
                    .withTypeText(description)
                    .withTailText("()", true)  // Dodaj () jako podpowiedź
            )
        }

        // --- ZADANIE 2.2 PRO: Dynamiczne skanowanie drzewa PSI ---
        val file = parameters.originalFile
        val foundVariables = mutableSetOf<String>()

        // PsiRecursiveElementVisitor: Wzorzec projektowy Visitor. Rekurencyjnie przechodzi po każdym węźle drzewa składniowego.
        file.accept(object : com.intellij.psi.PsiRecursiveElementVisitor() {
            override fun visitElement(element: com.intellij.psi.PsiElement) {
                super.visitElement(element)
                // Sprawdzamy, czy dany węzeł to identyfikator (ID), odwołując się bezpośrednio do wygenerowanego typu
                if (element.node?.elementType == com.github.cplexopl.psi.OplTypes.ID) {
                    val text = element.text
                    // Dummy Identifier: "IntellijIdeaRulezzz" to techniczny ciąg wstawiany przez IDE w miejscu kursora podczas wpisywania. Musimy go zignorować.
                    if (text.isNotBlank() && !text.contains("IntellijIdeaRulezzz")) {
                        foundVariables.add(text)
                    }
                }
            }
        })

        // Dodajemy wszystkie zebrane identyfikatory do wyników
        foundVariables.forEach { variable ->
            result.addElement(
                LookupElementBuilder.create(variable)
                    .withIcon(com.intellij.icons.AllIcons.Nodes.Variable)
                    .withTypeText("Local Variable")
            )
        }
    }
}
