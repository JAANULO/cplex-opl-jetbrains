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
        val parent = myNode.treeParent ?: return Indent.getNoneIndent()
        val parentType = parent.elementType
        val elementType = myNode.elementType

        // 1. Elementy przylegające do lewej (klamry, nawiasy zamykające blok)
        if (elementType == OplTypes.LBRACE ||
            elementType == OplTypes.RBRACE ||
            elementType == OplTypes.LBRACKET ||
            elementType == OplTypes.RBRACKET ||
            elementType == OplTypes.SUBJECT_TO ||
            elementType == OplTypes.EXECUTE ||
            (parentType == OplTypes.EXECUTE_BLOCK && elementType == OplTypes.ID)) {
            return Indent.getNoneIndent()
        }

        // 2. Wcięcia dla głównych bloków (ograniczenia, skrypty, struktury w tablicach)
        if (parentType == OplTypes.CONSTRAINT_SECTION ||
            parentType == OplTypes.EXECUTE_BODY ||
            parentType == OplTypes.FACTOR) {
            return Indent.getNormalIndent()
        }

        // 3. Obsługa zagnieżdżeń w pętlach forall i blokach warunkowych
        if (parentType == OplTypes.CONSTRAINT_ITEM) {
            // Zawartość ograniczenia ma być wcięta, z wyjątkiem ewentualnych klamer otaczających
            if (elementType != OplTypes.LBRACE && elementType != OplTypes.RBRACE) {
                return Indent.getNormalIndent()
            }
            return Indent.getNoneIndent()
        }

        return Indent.getNoneIndent()
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        val elementType = myNode.elementType
        // Enter automatycznie nadaje odpowiedni poziom wcięć
        if (elementType == OplTypes.CONSTRAINT_SECTION ||
            elementType == OplTypes.CONSTRAINT_ITEM ||
            elementType == OplTypes.EXECUTE_BODY ||
            elementType == OplTypes.FACTOR) {
            return ChildAttributes(Indent.getNormalIndent(), null)
        }
        return ChildAttributes(Indent.getNoneIndent(), null)
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? = spacingBuilder.getSpacing(this, child1, child2)
    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}