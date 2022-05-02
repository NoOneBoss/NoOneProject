package ru.mirea.istornikov.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Employee {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String = ""
    var salary = 0
}