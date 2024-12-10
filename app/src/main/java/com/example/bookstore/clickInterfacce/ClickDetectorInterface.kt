package com.example.bookstore.clickInterfacce

interface ClickDetectorInterface {
    fun deleteClick(position:Int)
    fun updateClick(position:Int)
    fun itemClick(position: Int)
}