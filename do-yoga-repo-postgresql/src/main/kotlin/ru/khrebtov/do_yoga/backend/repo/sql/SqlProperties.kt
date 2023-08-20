package ru.khrebtov.do_yoga.backend.repo.sql

open class SqlProperties(
    val url: String = "//localhost:54321/easy_yoga_db",
    val user: String = "postgres",
    val password: String = "postgres",
    val schema: String = "do-yoga",
    val dropDatabase: Boolean = false,
)
