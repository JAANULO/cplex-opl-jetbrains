package com.github.cplexopl.error

import com.github.cplexopl.OplBundle
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class OplErrorReportSubmitterTest : BasePlatformTestCase() {

    fun testErrorReportSubmitterActionText() {
        val submitter = OplErrorReportSubmitter()
        assertNotNull("ErrorReportSubmitter nie powinien być null", submitter)
        assertEquals(OplBundle.message("error.submitter.reportAction"), submitter.reportActionText)
    }
}
