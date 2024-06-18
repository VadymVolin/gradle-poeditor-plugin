package com.poeditor.plugin

class UpdateRunnable implements Runnable {
    private final String apiKey
    private final String projectId
    private final String lang
    private final String type
    private final File file
    private final List<String> tags

    UpdateRunnable(String apiKey, String projectId, String lang, String type, File file, List<String> tags) {
        this.apiKey = apiKey
        this.projectId = projectId
        this.lang = lang
        this.type = type
        this.file = file
        this.tags = tags
    }

    @Override
    void run() {
        TranslationsUpdater.updateLanguageTranslations(apiKey, projectId, lang, file.path, type)
    }
}
