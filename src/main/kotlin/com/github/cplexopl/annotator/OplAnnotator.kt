package com.github.cplexopl.annotator

import com.github.cplexopl.psi.OplTypes
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

// Annotator = klasa sprawdzająca kod "w locie" podczas pisania
// Oznacza błędy/ostrzeżenia bez potrzeby uruchamiania kompilatora
// HighlightSeverity = poziom ważności: ERROR (czerwony), WARNING (żółty), INFO (niebieski)

class OplAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // Sprawdzamy tylko identyfikatory
        if (element.elementType != OplTypes.ID) return

        val text = element.text

        // Reguła 1: Identyfikatory nie powinny zaczynać się wielką literą
        // (konwencja OPL: zmienne małą literą, zbiory/parametry wielką)
        // Pomijamy sprawdzenie, jeśli tekst jest pisany samymi wielkimi literami (zbiory/stałe)
        val isAllUpperCase = text.length > 1 && text.all { it.isUpperCase() || !it.isLetter() }
        if (text[0].isUpperCase() && !isAllUpperCase) {
            holder.newAnnotation(HighlightSeverity.WEAK_WARNING, "Variable name starts with an uppercase letter")
                .range(element)
                .create()
        }

        // Reguła 2: Zarezerwowane słowa IBM OPL których nie powinno się używać jako nazw
        val reservedButNotKeyword = setOf("Opl", "IloOplModel", "IloConstraint", "IloNumVar")
        if (text in reservedButNotKeyword) {
            holder.newAnnotation(
                HighlightSeverity.WARNING,
                "'$text' is a reserved IBM OPL API name"
            )
                .range(element)
                .create()
        }

        // Reguła 3: Zbyt krótkie nazwy zmiennych (1 znak) - ostrzeżenie stylistyczne
        if (text.length == 1 && text != "i" && text != "j" && text != "k" && text != "n" && text != "x" && text != "y") {
            holder.newAnnotation(
                HighlightSeverity.WEAK_WARNING,
                "Single-character variable name: consider a more descriptive name"
            )
                .range(element)
                .create()
        }
    }
}
