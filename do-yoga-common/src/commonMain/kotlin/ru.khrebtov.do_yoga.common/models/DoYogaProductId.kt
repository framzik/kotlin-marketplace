package ru.khrebtov.do_yoga.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class DoYogaProductId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = DoYogaProductId("")
    }
}
