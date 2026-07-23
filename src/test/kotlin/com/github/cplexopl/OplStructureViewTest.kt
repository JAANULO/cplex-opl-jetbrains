package com.github.cplexopl

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplStructureViewTest : BasePlatformTestCase() {

    fun testStructureView() {
        myFixture.configureByText(
            OplFileType,
            """
            tuple Item {
              int id;
            };
            
            dvar int x;
            int y;
            
            minimize x + y;
            
            subject to {
              ct1: x <= 10;
            }
            """.trimIndent()
        )
        myFixture.testStructureView { structureViewComponent ->
            val tree = structureViewComponent.tree
            val root = tree.model.root as javax.swing.tree.DefaultMutableTreeNode
            
            // Expecting that root has 5 children (tuple Item, dvar x, var y, minimize, subject to)
            val children = (0 until root.childCount).map { root.getChildAt(it) as javax.swing.tree.DefaultMutableTreeNode }
            
            // Check if structure contains all expected elements regardless of sorting order
            val names = children.map { it.userObject.toString() }
            assertEquals(5, names.size)
            assertTrue("Missing 'tuple Item' in structure: $names", names.contains("tuple Item"))
            assertTrue("Missing 'dvar x' in structure: $names", names.contains("dvar x"))
            assertTrue("Missing 'var y' in structure: $names", names.contains("var y"))
            assertTrue("Missing 'minimize' in structure: $names", names.contains("minimize"))
            assertTrue("Missing 'subject to' in structure: $names", names.contains("subject to"))
        }
    }
}
