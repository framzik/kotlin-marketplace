package ru.khrebtov.springapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import ru.khrebtov.do_yoga.backend.repo.sql.SqlProperties

@ConfigurationProperties("sql")
class SqlPropertiesEx @ConstructorBinding constructor(
    url: String,
    user: String,
    password: String,
    schema: String,
    dropDatabase: Boolean
) : SqlProperties(url, user, password, schema, dropDatabase)