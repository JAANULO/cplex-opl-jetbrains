import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.intelliJPlatform)
    alias(libs.plugins.changelog)
    alias(libs.plugins.qodana)
    alias(libs.plugins.kover)
    // Aktualizacja tej linii:
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)

    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })
        bundledModules(providers.gradleProperty("platformBundledModules").map { it.split(',') })
        testFramework(TestFrameworkType.Platform)
    }
}

sourceSets {
    main {
        kotlin.srcDirs("src/main/gen")
        java.srcDirs("src/main/gen")
    }
}

intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")

        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map { text ->
            val startTag = ""
            val endTag = ""

            if (text.contains(startTag) && text.contains(endTag)) {
                text.substringAfter(startTag)
                    .substringBefore(endTag)
                    .trim()
                    .let(::markdownToHTML)
            } else {
                // Zapasowy opis, jeśli tagi w README zostaną uszkodzone
                "Wtyczka wspierająca język IBM ILOG CPLEX OPL w IntelliJ IDEA."
            }
        }

        val changelog = project.changelog
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        channels = providers.gradleProperty("pluginVersion").map {
            listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" })
        }
    }

    pluginVerification {
        ides { recommended() }
    }
}

changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
    versionPrefix = ""
}

kover {
    reports {
        total {
            xml { onCheck = true }
        }
    }
}

tasks {
    named<org.jetbrains.grammarkit.tasks.GenerateLexerTask>("generateLexer") {
        sourceFile.set(layout.projectDirectory.file("src/main/grammars/OplLexer.flex"))
        // Powrót do nowszej nazwy i typu Directory
        targetOutputDir.set(layout.projectDirectory.dir("src/main/gen/com/github/cplexopl/lexer"))
        // targetClass usunięty - wtyczka odczyta go z pliku .flex
        purgeOldFiles.set(true)
    }

    named<org.jetbrains.grammarkit.tasks.GenerateParserTask>("generateParser") {
        sourceFile.set(layout.projectDirectory.file("src/main/grammars/OplGrammar.bnf"))
        // Powrót do nowszej nazwy i typu Directory
        targetRootOutputDir.set(layout.projectDirectory.dir("src/main/gen"))
        pathToParser.set("com/github/cplexopl/parser/OplParser.java")
        pathToPsiRoot.set("com/github/cplexopl/psi")
        purgeOldFiles.set(true)
    }

    compileKotlin {
        dependsOn("generateLexer", "generateParser")
    }

    compileJava {
        dependsOn("generateLexer", "generateParser")
    }

    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    publishPlugin {
        dependsOn(patchChangelog)
    }
}
