package com.github.cplexopl

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object OplIcons {
    // Make sure .svg files with these names exist in src/main/resources/icons/
    @JvmField
    val MOD_FILE = IconLoader.getIcon("/icons/oplMod.svg", OplIcons::class.java)

    @JvmField
    val DAT_FILE = IconLoader.getIcon("/icons/oplDat.svg", OplIcons::class.java)
}