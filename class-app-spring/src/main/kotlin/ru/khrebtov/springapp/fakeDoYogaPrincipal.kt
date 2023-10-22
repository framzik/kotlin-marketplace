package ru.khrebtov.springapp

import ru.khrebtov.do_yoga.common.models.DoYogaUserId
import ru.khrebtov.do_yoga.common.permissions.DoYogaPrincipalModel
import ru.khrebtov.do_yoga.common.permissions.DoYogaUserGroups

// TODO в наше приложение на спринге сейчас не прикручена авторизация
fun fakeDoYogaPrincipal() = DoYogaPrincipalModel(
    id = DoYogaUserId("user-1"),
    fname = "Ivan",
    mname = "Ivanovich",
    lname = "Ivanov",
    groups = setOf(DoYogaUserGroups.USER),
)