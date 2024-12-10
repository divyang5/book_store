package com.example.bookstore.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.bookstore.model.Books

@Dao
interface ItemDao {
    @Insert
    suspend fun addBook(book: Books)

    @Delete
    suspend fun deleteBook(book: Books)

    @Update
    suspend fun updateBook(book: Books)

    @Query("SELECT id,url,book_name,author,price,quantity,description FROM books")
    suspend fun getAllBooks(): List<Books>

    @Query("SELECT * FROM books WHERE id=:book_id")
    suspend fun getBook(book_id:Int): Books
}