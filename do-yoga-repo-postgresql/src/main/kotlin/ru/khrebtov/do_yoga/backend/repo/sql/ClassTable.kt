package ru.khrebtov.do_yoga.backend.repo.sql

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.khrebtov.do_yoga.common.models.DoYogaClass
import ru.khrebtov.do_yoga.common.models.DoYogaClassId
import ru.khrebtov.do_yoga.common.models.DoYogaClassLock
import ru.khrebtov.do_yoga.common.models.DoYogaType
import ru.khrebtov.do_yoga.common.models.DoYogaVisibility

object ClassTable : Table("class") {
    val id = varchar("id", 128)
    val officeAddress = varchar("officeAddress", 128)
    val trainer = varchar("description", 128)
    val visibility = enumeration("visibility", DoYogaVisibility::class)
    val classType = enumeration("class_type", DoYogaType::class)
    val students = varchar("students", 2500)
    var time = datetime("classTime")
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = DoYogaClass(
        id = DoYogaClassId(res[id].toString()),
        officeAddress = res[this.officeAddress],
        trainer = res[trainer],
        visibility = res[visibility],
        classType = res[classType],
        students = res[students].let { res[students].split(',').filter{it.isNotBlank()}.sorted().toSet() },
        time = res[time].toKotlinLocalDateTime(),
        lock = DoYogaClassLock(res[lock])
    )

    fun from(res: ResultRow) = DoYogaClass(
        id = DoYogaClassId(res[id].toString()),
        officeAddress = res[this.officeAddress],
        trainer = res[trainer],
        visibility = res[visibility],
        classType = res[classType],
        students = res[students].let { res[students].split(',').filter{it.isNotBlank()}.sorted().toSet() },
        time = res[time].toKotlinLocalDateTime(),
        lock = DoYogaClassLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, doYogaClass: DoYogaClass, randomUuid: () -> String) {
        it[id] = doYogaClass.id.takeIf { it != DoYogaClassId.NONE }?.asString() ?: randomUuid()
        it[this.officeAddress] = doYogaClass.officeAddress ?: ""
        it[trainer] = doYogaClass.trainer ?: ""
        it[visibility] = doYogaClass.visibility
        it[classType] = doYogaClass.classType
        it[students] = initStudents(doYogaClass)
        it[time] = doYogaClass.time?.toJavaLocalDateTime() ?: java.time.LocalDateTime.now()
        it[lock] = doYogaClass.lock.takeIf { it != DoYogaClassLock.NONE }?.asString() ?: randomUuid()
    }

    private fun initStudents(doYogaClass: DoYogaClass): String {
        val sb = StringBuilder()
        doYogaClass.students?.forEach { sb.append("$it,") }

        return sb.toString()
    }
}
