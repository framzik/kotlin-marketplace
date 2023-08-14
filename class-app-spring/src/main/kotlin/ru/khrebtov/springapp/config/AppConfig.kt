package ru.khrebtov.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.repo.inmemory.ClassRepoInMemory
import ru.otus.otuskotlin.marketplace.common.DoYogaCorSettings

@Configuration
class AppConfig {

    @Bean
    fun processor() = DoYogaClassProcessor(corSettings())

    @Bean
    fun corSettings(): DoYogaCorSettings = DoYogaCorSettings(
        repoStub = ClassRepoInMemory(),
        repoProd = ClassRepoInMemory(),
        repoTest = ClassRepoInMemory(),
    )
}
