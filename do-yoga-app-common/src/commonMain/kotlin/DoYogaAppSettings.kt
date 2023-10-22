package ru.otus.otuskotlin.marketplace.app.common

import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.common.DoYogaCorSettings

data class DoYogaAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: DoYogaCorSettings = DoYogaCorSettings(),
    val processor: DoYogaClassProcessor = DoYogaClassProcessor(settings = corSettings),
    val auth: AuthConfig = AuthConfig.NONE,
)