package ru.khrebtov.do_yoga.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to DoYogaContext")
