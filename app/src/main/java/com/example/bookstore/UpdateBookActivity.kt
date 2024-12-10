package com.example.bookstore

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bookstore.database.ApplicationDatabase
import com.example.bookstore.database.ItemDao
import com.example.bookstore.databinding.ActivityUpdateBookBinding
import com.example.bookstore.model.Books
import kotlinx.coroutines.launch

class UpdateBookActivity : AppCompatActivity() {

    // Binding object to access the UI components
    private lateinit var binding: ActivityUpdateBookBinding

    // Database and DAO objects to interact with the database
    private lateinit var database: ApplicationDatabase
    private lateinit var itemDao: ItemDao

    // Holds the book data to be edited
    private lateinit var data: Books

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout and set the content view
        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title for the action bar
        supportActionBar!!.setTitle("Update Book")

        // Enable the back button in the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize the database and DAO objects to interact with the book data
        database = ApplicationDatabase.getInstance(this)
        itemDao = database.itemDao()

        // Get the book ID passed from the previous activity
        val book_id: Int = intent.getIntExtra("book_id", 0)

        // Fetch the book details from the database in a coroutine
        lifecycleScope.launch {
            data = itemDao.getBook(book_id)
            // Populate the fields with the current book data
            resetBookEditText()
        }

        // Handle the "Edit" button click for updating the book
        binding.btnEditBook.setOnClickListener {
            // Validate the input before updating the book data
            if (validateEditText()) {
                // Update the book details in the database if the validation is successful
                lifecycleScope.launch {
                    itemDao.updateBook(
                        Books(
                            id = book_id,
                            book_name = binding.etBookName.text.toString(),
                            author = binding.etAuthor.text.toString(),
                            price = binding.etPrice.text.toString().toDouble(),
                            quantity = binding.etQuantity.text.toString().toInt(),
                            url = binding.etImageURL.text.toString(),
                            description = binding.etDescription.text.toString()
                        )
                    )
                    // Close the activity and show a success message
                    finish()
                    Toast.makeText(this@UpdateBookActivity, "Book Updated!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show an error message if the input is invalid
                Toast.makeText(this@UpdateBookActivity, "Invalid Information Entered", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle the "Reset" button click to reset the fields to their original values
        binding.btnResetBook.setOnClickListener {
            resetBookEditText()
        }
    }

    // Reset the EditText fields to the current book data
    private fun resetBookEditText() {
        binding.etBookName.setText(data.book_name)
        binding.etAuthor.setText(data.author)
        binding.etPrice.setText(data.price.toString())
        binding.etQuantity.setText(data.quantity.toString())
        binding.etImageURL.setText(data.url.toString())
        binding.etDescription.setText(data.description.toString())
    }

    // Validate the input data for the book fields
    private fun validateEditText(): Boolean {
        var isValid = true

        // Validate book name
        if (binding.etBookName.text?.isBlank() == true) {
            binding.etBookName.error = "Book name is required"
            isValid = false
        }

        // Validate author name
        if (binding.etAuthor.text?.isBlank() == true) {
            binding.etAuthor.error = "Author name is required"
            isValid = false
        }

        // Validate price input
        val priceText = binding.etPrice.text.toString()
        val price = priceText.toDoubleOrNull()
        if (priceText.isBlank() || price == null || price <= 0) {
            binding.etPrice.error = "Enter a valid positive price"
            isValid = false
        }

        // Validate quantity input
        val quantityText = binding.etQuantity.text.toString()
        val quantity = quantityText.toIntOrNull()
        if (quantityText.isBlank() || quantity == null || quantity <= 0) {
            binding.etQuantity.error = "Enter a valid positive quantity"
            isValid = false
        }

        return isValid
    }

    // Handle the selection of items in the action bar menu (specifically the back button)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Close the activity and return to the previous screen
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
