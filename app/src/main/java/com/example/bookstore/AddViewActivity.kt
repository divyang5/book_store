package com.example.bookstore

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bookstore.database.ApplicationDatabase
import com.example.bookstore.database.ItemDao
import com.example.bookstore.databinding.ActivityAddViewBinding
import com.example.bookstore.model.Books
import kotlinx.coroutines.launch

class AddViewActivity : AppCompatActivity() {

    // Binding object to access UI components in the layout
    private lateinit var binding: ActivityAddViewBinding

    // Database and DAO instances for accessing book data in the local database
    private lateinit var database: ApplicationDatabase
    private lateinit var itemDao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout and set up the content view
        binding = ActivityAddViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title of the action bar
        supportActionBar!!.setTitle("Add Book")

        // Enable the back button in the action bar to return to the previous activity
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize the database and DAO for accessing book data
        database = ApplicationDatabase.getInstance(this)
        itemDao = database.itemDao()

        // Handle the "Add Book" button click
        binding.btnAddBook.setOnClickListener {
            // Validate the user input before adding the book
            if (validateEditText()) {
                lifecycleScope.launch {
                    // Create a new book object using the data entered by the user
                    val book = Books(
                        book_name = binding.etBookName.text.toString(),
                        author = binding.etAuthorName.text.toString(),
                        price = binding.etBookPrice.text.toString().toDouble(),
                        quantity = binding.etBookQuantity.text.toString().toInt(),
                        url = binding.etImageUrl.text.toString(),
                        description = binding.etBookDescription.text.toString()
                    )

                    // Add the new book to the database
                    itemDao.addBook(book)

                    // Log the added book's details for debugging
                    Log.d("BOOK", "THE BOOK DESCRIPTION IS ${book.toString()}")

                    // Show a success message to the user and finish the activity
                    Toast.makeText(this@AddViewActivity, "Book added!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                // Show an error message if the validation fails
                Toast.makeText(this@AddViewActivity, "Please fix the errors and try again", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle the "Reset" button click to clear the input fields
        binding.btnResetBook.setOnClickListener {
            resetBookEditText()
        }
    }

    // Resets the EditText fields to empty
    private fun resetBookEditText() {
        binding.etBookName.setText("")
        binding.etAuthorName.setText("")
        binding.etBookPrice.setText("")
        binding.etBookQuantity.setText("")
    }

    // Handle the action bar item clicks (specifically the back button)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Finish the current activity and return to the previous one
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Validates the input fields for the book form
    private fun validateEditText(): Boolean {
        var isValid = true

        // Check if the book name is empty
        if (binding.etBookName.text?.isBlank() == true) {
            binding.etBookName.error = "Book name is required"
            isValid = false
        }

        // Check if the author name is empty
        if (binding.etAuthorName.text?.isBlank() == true) {
            binding.etAuthorName.error = "Author name is required"
            isValid = false
        }

        // Validate that the price is a valid positive number
        val priceText = binding.etBookPrice.text.toString()
        val price = priceText.toDoubleOrNull()
        if (priceText.isBlank() || price == null || price <= 0) {
            binding.etBookPrice.error = "Enter a valid positive price"
            isValid = false
        }

        // Validate that the quantity is a valid positive number
        val quantityText = binding.etBookQuantity.text.toString()
        val quantity = quantityText.toIntOrNull()
        if (quantityText.isBlank() || quantity == null || quantity <= 0) {
            binding.etBookQuantity.error = "Enter a valid positive quantity"
            isValid = false
        }

        return isValid
    }
}
