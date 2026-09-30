package com.github.cplexopl

import com.github.cplexopl.completion.OplCompletionTest
import com.github.cplexopl.console.OplConsoleFilterTest
import com.github.cplexopl.error.OplErrorReportSubmitterTest
import com.github.cplexopl.features.OplCommenterTest
import com.github.cplexopl.formatter.OplFormattingTest
import com.github.cplexopl.highlighting.OplHighlightingTest
import com.github.cplexopl.parser.OplParsingTest
import com.github.cplexopl.parser.OplPiecewiseParsingTest
import com.github.cplexopl.performance.OplAnnotatorPerformanceTest
import com.github.cplexopl.performance.OplConsoleFilterPerformanceTest
import com.github.cplexopl.performance.OplParserPerformanceTest
import com.github.cplexopl.reference.OplIncludeTest
import com.github.cplexopl.reference.OplReferenceTest
import com.github.cplexopl.run.OplPathTranslatorTest
import com.github.cplexopl.run.OplRunConfigurationIntegrationTest
import com.github.cplexopl.run.OplRunConfigurationTest
import com.github.cplexopl.settings.OplSettingsTest
import com.github.cplexopl.structure.OplStructureViewTest
import com.github.cplexopl.templates.OplLiveTemplatesTest
import com.github.cplexopl.utils.CplexPathFinderTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    OplParsingTest::class,
    OplFormattingTest::class,
    OplCommenterTest::class,
    OplStructureViewTest::class,
    OplLiveTemplatesTest::class,
    OplReferenceTest::class,
    OplHighlightingTest::class,
    OplConsoleFilterTest::class,
    OplRunConfigurationTest::class,
    OplSettingsTest::class,
    OplCompletionTest::class,
    CplexPathFinderTest::class,
    OplPiecewiseParsingTest::class,
    OplIncludeTest::class,
    OplErrorReportSubmitterTest::class,
    OplPathTranslatorTest::class,
    OplRunConfigurationIntegrationTest::class,
    OplParserPerformanceTest::class,
    OplConsoleFilterPerformanceTest::class,
    OplAnnotatorPerformanceTest::class
)
class OplAllTestSuite
