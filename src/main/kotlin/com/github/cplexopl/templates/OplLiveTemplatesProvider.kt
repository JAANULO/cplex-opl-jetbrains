package com.github.cplexopl.templates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider
import com.intellij.psi.PsiFile

// Live Templates = skróty klawiszowe do wstawiania gotowych bloków kodu
// np. wpisz "model" + Tab → generuje szkielet całego modelu OPL
// TemplateContextType = określa w jakim języku/kontekście szablon jest dostępny

class OplTemplateContextType : TemplateContextType("OPL", "OPL") {
    override fun isInContext(file: PsiFile, offset: Int): Boolean {
        // Szablon działa gdy plik ma rozszerzenie .mod
        return file.name.endsWith(".mod") || file.name.endsWith(".dat")
    }
}

// DefaultLiveTemplatesProvider = wskazuje plik XML z definicjami szablonów
class OplLiveTemplatesProvider : DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles(): Array<String> {
        // Ścieżka do pliku XML z szablonami (w resources/)
        return arrayOf("/liveTemplates/OPL")
    }

    override fun getHiddenLiveTemplateFiles(): Array<String>? = null
}
