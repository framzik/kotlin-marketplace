package ru.khrebtov.biz

import ru.khrebtov.biz.groups.operation
import ru.khrebtov.biz.groups.stubs
import ru.khrebtov.biz.validation.finishClassFilterValidation
import ru.khrebtov.biz.validation.finishClassValidation
import ru.khrebtov.biz.validation.validateIdNotEmpty
import ru.khrebtov.biz.validation.validateIdProperFormat
import ru.khrebtov.biz.validation.validateOfficeAddressHasContent
import ru.khrebtov.biz.validation.validateOfficeAddressNotEmpty
import ru.khrebtov.biz.validation.validateTrainerHasContent
import ru.khrebtov.biz.validation.validateTrainerNotEmpty
import ru.khrebtov.biz.validation.validation
import ru.khrebtov.biz.workers.initStatus
import ru.khrebtov.biz.workers.stubCreateSuccess
import ru.khrebtov.biz.workers.stubDbError
import ru.khrebtov.biz.workers.stubDeleteSuccess
import ru.khrebtov.biz.workers.stubNoCase
import ru.khrebtov.biz.workers.stubReadSuccess
import ru.khrebtov.biz.workers.stubSearchSuccess
import ru.khrebtov.biz.workers.stubSignUpSuccess
import ru.khrebtov.biz.workers.stubUpdateSuccess
import ru.khrebtov.biz.workers.stubValidationBadId
import ru.khrebtov.biz.workers.stubValidationBadOfficeAddress
import ru.khrebtov.biz.workers.stubValidationBadTrainer
import ru.khrebtov.cor.rootChain
import ru.khrebtov.cor.worker
import ru.otus.otuskotlin.marketplace.common.DoYogaContext
import ru.otus.otuskotlin.marketplace.common.models.DoYogaClassId
import ru.otus.otuskotlin.marketplace.common.models.DoYogaCommand

class DoYogaClassProcessor {
    suspend fun exec(ctx: DoYogaContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<DoYogaContext> {
            initStatus("Инициализация статуса")

            operation("Создание класса", DoYogaCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadOfficeAddress("Имитация ошибки валидации адреса")
                    stubValidationBadTrainer("Имитация ошибки валидации тренера")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в classValidating") { classValidating = classRequest.deepCopy() }
                    worker("Очистка id") { classValidating.id = DoYogaClassId.NONE }
                    worker("Очистка адреса") { classValidating.officeAddress = classValidating.officeAddress?.trim() }
                    worker("Очистка тренера") { classValidating.trainer = classValidating.trainer?.trim() }
                    validateOfficeAddressNotEmpty("Проверка, что адрес не пуст")
                    validateOfficeAddressHasContent("Проверка символов")
                    validateTrainerNotEmpty("Проверка, что тренер не пуст")
                    validateTrainerHasContent("Проверка символов")

                    finishClassValidation("Завершение проверок")
                }
            }
            operation("Получить класс", DoYogaCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в classValidating") { classValidating = classRequest.deepCopy() }
                    worker("Очистка id") { classValidating.id = DoYogaClassId(classValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishClassValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить класс", DoYogaCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadOfficeAddress("Имитация ошибки валидации адреса")
                    stubValidationBadTrainer("Имитация ошибки валидации тренера")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в classValidating") { classValidating = classRequest.deepCopy() }
                    worker("Очистка id") { classValidating.id = DoYogaClassId(classValidating.id.asString().trim()) }
                    worker("Очистка адреса") { classValidating.officeAddress = classValidating.officeAddress?.trim() }
                    worker("Очистка тренера") { classValidating.trainer = classValidating.trainer?.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateOfficeAddressNotEmpty("Проверка, что адрес не пуст")
                    validateOfficeAddressHasContent("Проверка символов")
                    validateTrainerNotEmpty("Проверка, что тренер не пуст")
                    validateTrainerHasContent("Проверка символов")

                    finishClassValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Удалить класс", DoYogaCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в classValidating") {
                        classValidating = classRequest.deepCopy()
                    }
                    worker("Очистка id") { classValidating.id = DoYogaClassId(classValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishClassValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск объявлений", DoYogaCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в classFilterValidating") {
                        classFilterValidating = classFilterRequest.copy()
                    }

                    finishClassFilterValidation("Успешное завершение процедуры валидации")
                }

            }
            operation("Поиск подходящих классов для занятий", DoYogaCommand.SIGN_UP) {
                stubs("Обработка стабов") {
                    stubSignUpSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в classValidating") { classValidating = classRequest.deepCopy() }
                    worker("Очистка id") { classValidating.id = DoYogaClassId(classValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishClassValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}
