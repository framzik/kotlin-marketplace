package ru.otus.otuskotlin.marketplace.common.exceptions

import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassLock

class RepoConcurrencyException(expectedLock: DoYogaClassLock, actualLock:DoYogaClassLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
