package ru.khrebtov.do_yoga.common.exceptions

import ru.khrebtov.do_yoga.common.models.DoYogaClassLock

class RepoConcurrencyException(expectedLock: DoYogaClassLock, actualLock: DoYogaClassLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
