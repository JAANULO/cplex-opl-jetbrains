package com.github.cplexopl.settings

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

object OplSettingsParser {

    fun generateExecuteBlock(settingsFilePath: String): String {
        if (settingsFilePath.isEmpty()) return ""
        val settingsFile = File(settingsFilePath)
        if (!settingsFile.exists()) return ""

        return try {
            val factory = DocumentBuilderFactory.newInstance()
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            factory.isXIncludeAware = false
            factory.isExpandEntityReferences = false
            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(settingsFile)
            
            val result = StringBuilder()
            result.appendLine(com.github.cplexopl.OplBundle.message("error.run.tempFileComment"))
            result.appendLine("execute {")
            
            val settings = doc.getElementsByTagName("setting")
            val nameRegex = Regex("^[a-zA-Z0-9_]+$")
            
            for (i in 0 until settings.length) {
                val element = settings.item(i)
                val name = element.attributes.getNamedItem("name")?.nodeValue ?: continue
                
                // Security: Prevent code injection via invalid property names
                if (!name.matches(nameRegex)) continue
                
                val value = element.attributes.getNamedItem("value")?.nodeValue ?: continue
                
                val decodedValue = decodeXmlEntities(value)
                
                val isNumericOrBoolean = decodedValue.toDoubleOrNull() != null || 
                                         decodedValue.toLongOrNull() != null || 
                                         decodedValue == "true" || 
                                         decodedValue == "false"
                
                val escapedValue = decodedValue
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                val formattedValue = if (isNumericOrBoolean) escapedValue else "\"${escapedValue}\""
                result.appendLine("  cplex.${name} = ${formattedValue};")
            }
            result.appendLine("}")
            result.append("\n")
            result.toString()
        } catch (e: Exception) {
            ""
        }
    }
    
    private fun decodeXmlEntities(value: String): String {
        return value
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&apos;", "'")
            .replace("&quot;", "\"")
            .replace("&amp;", "&")
    }
}
