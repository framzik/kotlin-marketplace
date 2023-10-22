package ru.khrebtov.do_yoga.common.permissions

@Suppress("unused")
enum class DoYogaUserPermissions {
    CREATE,

    READ_GROUP,
    READ_PUBLIC,
    READ_CANDIDATE,

    UPDATE_CANDIDATE,
    UPDATE_PUBLIC,

    DELETE_CANDIDATE,
    DELETE_PUBLIC,

    SEARCH_PUBLIC,
    SEARCH_REGISTERED,
    SEARCH_DRAFTS,
}
