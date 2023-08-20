package ru.khrebtov.springapp

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.khrebtov.do_yoga.backend.repo.sql.RepoClassSQL

@SpringBootTest
class ApplicationTests {
    @MockkBean
    private lateinit var repo: RepoClassSQL
    @Test
    fun contextLoads() {
    }
}
