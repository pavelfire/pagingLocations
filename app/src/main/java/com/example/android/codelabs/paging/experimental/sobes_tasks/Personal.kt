package com.example.android.codelabs.paging.experimental.sobes_tasks

class Personal {
}

data class Person(
    val name: String = "Jack"
) {

    var lastName: String = "Norton"
}

val set = hashSetOf(
    Person().apply { lastName = "Brown" },
    Person("John"),
    Person().apply { lastName = "Miller" }
)


/*
"Дано неотрицательное целое. Нужно найти округленный
 до меньшего целого квадратный корень из х.
Результат должен быть неотрицательным.
Нельзя использовать встроенную экспоненту или квадратный корень"
 */