package com.example.bookstore.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Books(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val book_name: String,
    val author: String,
    val price: Double,
    val quantity: Int,
    val url: String?,
    val description: String?
)
