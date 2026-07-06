package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplStructureViewTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/structure"

    fun testStructureView() {
        myFixture.configureByFile("structure.mod")
        myFixture.testStructureView { structureViewComponent ->
            val tree = structureViewComponent.tree
            val root = tree.model.root as javax.swing.tree.DefaultMutableTreeNode
            
            // Oczekujemy że korzeń ma 5 dzieci (Item, x, y, minimize, subject to)
            assertEquals(5, root.childCount)
            
            // Sprawdzamy czy struktura zawiera wszystkie oczekiwane elementy niezależnie od kolejności sortowania
            val childrenTexts = (0 until root.childCount).map { root.getChildAt(it).toString() }
            
            assertTrue("Brak 'Item' w strukturze: $childrenTexts", childrenTexts.any { it.contains("Item") })
            assertTrue("Brak 'x' w strukturze: $childrenTexts", childrenTexts.any { it.contains("x") })
            assertTrue("Brak 'y' w strukturze: $childrenTexts", childrenTexts.any { it.contains("y") })
            assertTrue("Brak 'minimize' lub 'maximize' w strukturze: $childrenTexts", childrenTexts.any { it.contains("minimize") || it.contains("maximize") })
            assertTrue("Brak 'subject to' w strukturze: $childrenTexts", childrenTexts.any { it.contains("subject to") })
        }
    }
}
