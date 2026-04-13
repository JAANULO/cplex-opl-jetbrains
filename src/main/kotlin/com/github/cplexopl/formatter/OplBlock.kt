package com.github.cplexopl.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.formatter.common.AbstractBlock
import com.github.cplexopl.psi.OplTypes

class OplBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {

    override fun buildChildren(): List<Block> {
        val blocks = mutableListOf<Block>()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType != com.intellij.psi.TokenType.WHITE_SPACE) {
                blocks.add(OplBlock(child, null, null, spacingBuilder))
            }
            child = child.treeNext
        }
        return blocks
    }

    override fun getIndent(): Indent? {
        val parent = myNode.treeParent
        // Dodaj wcięcie, jeśli element jest wewnątrz klamer {} (np. w subject to lub forall)
        return if (parent != null && (parent.elementType == OplTypes.CONSTRAINT_SECTION || parent.elementType == OplTypes.CONSTRAINT_ITEM)) {
            if (myNode.elementType != OplTypes.LBRACE && myNode.elementType != OplTypes.RBRACE) {
                Indent.getNormalIndent()
            } else {
                Indent.getNoneIndent()
            }
        } else {
            Indent.getNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? = spacingBuilder.getSpacing(this, child1, child2)
    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}