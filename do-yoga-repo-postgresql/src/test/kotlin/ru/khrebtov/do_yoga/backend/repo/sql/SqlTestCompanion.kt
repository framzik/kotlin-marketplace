package ru.khrebtov.do_yoga.backend.repo.sql

import com.benasher44.uuid.uuid4
import java.time.Duration
import org.testcontainers.containers.PostgreSQLContainer
import ru.khrebtov.do_yoga.common.models.DoYogaClass

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:14")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "postgres"
    private const val SCHEMA = "do-yoga"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        initObjects: Collection<DoYogaClass> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoClassSQL {
        return RepoClassSQL(
            SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjects, randomUuid = randomUuid
        )
    }
}
