package com.github.cplexopl

import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class OplFileTypeTest : BasePlatformTestCase() {

    fun testOplFileTypeRegistration() {
        assertEquals("OPL Model File", OplFileType.name)
        assertEquals("mod", OplFileType.defaultExtension)
        assertNotNull(OplFileType.icon)
        
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("mod")
        assertEquals(OplFileType, fileType)
    }

    fun testOplDatFileTypeRegistration() {
        assertEquals("OPL Data File", OplDatFileType.name)
        assertEquals("dat", OplDatFileType.defaultExtension)
        assertNotNull(OplDatFileType.icon)
        
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("dat")
        assertEquals(OplDatFileType, fileType)
    }

    fun testOplOpsFileTypeRegistration() {
        assertEquals("OPL Settings File", OplOpsFileType.name)
        assertEquals("ops", OplOpsFileType.defaultExtension)
        assertNotNull(OplOpsFileType.icon)
        
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("ops")
        assertEquals(OplOpsFileType, fileType)
    }
}
