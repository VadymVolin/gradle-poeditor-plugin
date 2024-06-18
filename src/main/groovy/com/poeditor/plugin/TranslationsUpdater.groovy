package com.poeditor.plugin

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.gradle.api.GradleException

import java.nio.charset.StandardCharsets


/**
 * Loads translations
 */
class TranslationsUpdater {

    static void updateLanguageTranslations(String apiToken, String projectId, String language, String filePath, String translationsType) {
        try {
            // converts map to form data body key=value&key1=value1&...
            def body = [
                    api_token: apiToken,
                    id       : projectId,
                    language : language,
                    type     : translationsType
            ].collect { key, value ->
                "${key}=${value}"
            }.join('&')

            def file = new File(filePath) // output file

            // creates POST request to POEditor with form data
            def req = (HttpURLConnection) new URL('https://api.poeditor.com/v2/projects/export').openConnection()
            req.setRequestMethod("POST")
            req.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            req.setDoOutput(true)
            req.setDoInput(true)
            req.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8))

            println "Status code: ${req.getResponseCode()}" // HTTP request done on first read

            def resp = new JsonSlurper().parseText(req.getInputStream().getText()) // parse response

            req.disconnect()

            println "Response: ${resp}"

            if (req.getResponseCode() == HttpURLConnection.HTTP_OK) {
                println "Start loading [$language] translations -> ${resp.result.url}"
                def headers = [Accept: 'application/json']
                def jsonText = new URL(resp.result.url).getText(requestProperties: headers)
                // load json data
                println "Saving [${language}] translations to file [${file.path}]..."
                file.write JsonOutput.prettyPrint(jsonText) // save json to file
                println "[$language] translations update -> DONE!"
                println "=================================================================================================================="
            } else {
                throw new GradleException("Failure code: ${req.getResponseCode()}  message: ${req.getResponseMessage()}")
            }
        } catch (Exception e) {
            println(e)
        }
    }
}
