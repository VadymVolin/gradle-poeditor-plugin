package com.poeditor.plugin.entities

class Translation implements Serializable {
    final String lang
    final String file
    final List<String> tags

    Translation(params) {
        this.lang = params.get('lang', 'en')
        this.file = params.file
        this.tags = params.get('tags', [])
    }
}