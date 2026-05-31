package com.arbiradar.mobile.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "todos")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val dueDate: String? = null,
    val priority: String = "medium", // low, medium, high
    val category: String = "general",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class TodoResponse(
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val dueDate: String? = null,
    val priority: String = "medium",
    val category: String = "general",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
