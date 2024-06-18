package com.poeditor.plugin

import com.poeditor.plugin.entities.Translation
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

import java.nio.file.Paths

class DownloadTranslations extends DefaultTask {

    @Input
    final Property<String> apiKey = project.objects.property(String)

    @Input
    final Property<String> projectId = project.objects.property(String)

    @Input
    final Property<String> type = project.objects.property(String)

    @Input
    final ListProperty<Translation> trans = project.objects.listProperty(Translation)

    DownloadTranslations() {
        setDescription('Download translations from POEditor.')
        setGroup('poeditor')
    }

    @TaskAction
    def pullTranslations() {
        // @See https://poeditor.com/docs/api#projects_export
        trans.get().each {
            TranslationsUpdater.updateLanguageTranslations(apiKey.get(), projectId.get(), it.lang, it.file, type.get())
//            new UpdateRunnable(apiKey.get(), projectId.get(), it.lang, type.get(), project.file(it.file), it.tags).run()
        }
    }
}
