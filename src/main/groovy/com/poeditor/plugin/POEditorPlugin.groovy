package com.poeditor.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class POEditorPlugin implements Plugin<Project> {

    void apply(Project target) {
        def extension = target.extensions.create('poeditor', POEditorExtension, target)
        target.tasks.register('downloadTranslations', DownloadTranslations) {
            apiKey = extension.apiKey
            projectId = extension.projectId
            type = extension.type
            trans = extension.trans
        }
    }
}
