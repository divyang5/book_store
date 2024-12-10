package com.example.bookstore

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bookstore.database.ApplicationDatabase
import com.example.bookstore.database.ItemDao
import com.example.bookstore.databinding.ActivityDetailBookBinding
import kotlinx.coroutines.launch

class DetailBookActivity : AppCompatActivity() {

    // Binding object to access the UI components
    private lateinit var binding: ActivityDetailBookBinding

    // Database and DAO objects for interacting with the database
    private lateinit var db: ApplicationDatabase
    private lateinit var itemDao: ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout and set the content view
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title of the ActionBar for this activity
        supportActionBar!!.setTitle("Detail Book")

        // Enable the back button in the ActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize the database and DAO objects to interact with the data
        db = ApplicationDatabase.getInstance(this)
        itemDao = db.itemDao()

        // Get the book ID passed from the previous activity
        val book_id: Int = intent.getIntExtra("book_id", 0)

        // Use a coroutine to fetch the book details from the database
        lifecycleScope.launch {
            // Fetch the book data using the book ID
            val data = itemDao.getBook(book_id)

            // Set the book details into the corresponding UI components
            binding.tvBookTitle.text = data.book_name.toString()
            binding.tvBookAuthor.text = "~${data.author.toString()}"

            // Check if the book description exists and is not empty, then display it
            if (data.description != null && data.description.isNotBlank()) {
                binding.tvBookDescription.visibility = View.VISIBLE
                binding.tvBookDescription.text = "Description : ${data.description.toString()} "
            }

            // Display the book price and quantity
            binding.tvBookPrice.text = "Price : $${data.price}"
            binding.tvBookQuantity.text = "Quantity : ${data.quantity}"

            // Load the book cover image using Glide
            Glide.with(this@DetailBookActivity)   // The context is the current Activity
                .load(data.url)  // Load the image URL from the database
                .error(R.drawable.noimg)  // Show a default image if there's an error loading the image
                .into(binding.ivBookCover)  // Set the image into the ImageView
        }
    }

    // Handle the selection of items in the ActionBar menu (specifically the back button)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Close the activity and go back to the previous screen
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
