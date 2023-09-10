package ru.khrebtov.do_yoga.common.permissions

import ru.khrebtov.do_yoga.common.models.DoYogaUserId


data class DoYogaPrincipalModel(
    val id: DoYogaUserId = DoYogaUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<DoYogaUserGroups> = emptySet()
) {
    companion object {
        val NONE = DoYogaPrincipalModel()
    }
}
