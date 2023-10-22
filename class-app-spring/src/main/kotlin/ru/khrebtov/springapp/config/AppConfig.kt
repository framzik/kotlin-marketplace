package ru.khrebtov.springapp.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.khrebtov.backend.repository.inmemory.ClassRepoStub
import ru.khrebtov.biz.DoYogaClassProcessor
import ru.khrebtov.do_yoga.backend.repo.sql.RepoClassSQL
import ru.khrebtov.do_yoga.backend.repo.sql.SqlProperties
import ru.khrebtov.do_yoga.common.DoYogaCorSettings
import ru.khrebtov.do_yoga.common.repo.IClassRepository
import ru.khrebtov.repo.inmemory.ClassRepoInMemory
import ru.otus.otuskotlin.marketplace.app.common.DoYogaAppSettings

@Configuration
@EnableConfigurationProperties(SqlPropertiesEx::class)
class AppConfig {

    @Bean
    fun processor(corSettings: DoYogaCorSettings) = DoYogaClassProcessor(corSettings)
    @Bean(name = ["prodRepository"])
    @ConditionalOnProperty(value = ["prod-repository"], havingValue = "sql")
    fun prodRepository(sqlProperties: SqlProperties) = RepoClassSQL(sqlProperties)

    @Bean
    fun corSettings(
        @Qualifier("prodRepository") prodRepository: IClassRepository?,
        @Qualifier("testRepository") testRepository: IClassRepository,
        @Qualifier("stubRepository") stubRepository: IClassRepository,
    ): DoYogaCorSettings = DoYogaCorSettings(
        repoStub = stubRepository,
        repoProd = prodRepository ?: testRepository,
        repoTest = testRepository,
    )

    @Bean
    fun appSettings(corSettings: DoYogaCorSettings, processor: DoYogaClassProcessor) = DoYogaAppSettings(
        processor = processor,
        corSettings = corSettings
    )
    @Bean
    fun testRepository() = ClassRepoInMemory()

    @Bean
    fun stubRepository() = ClassRepoStub()
}
