package com.github.cplexopl

import com.github.cplexopl.performance.OplAnnotatorPerformanceTest
import com.github.cplexopl.performance.OplConsoleFilterPerformanceTest
import com.github.cplexopl.performance.OplParserPerformanceTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    OplParserPerformanceTest::class,
    OplConsoleFilterPerformanceTest::class,
    OplAnnotatorPerformanceTest::class
)
class OplPerformanceTestSuite
