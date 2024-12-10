package com.example.bookstore

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookstore.adapter.BookAdapter
import com.example.bookstore.clickInterfacce.ClickDetectorInterface
import com.example.bookstore.database.ApplicationDatabase
import com.example.bookstore.database.ItemDao
import com.example.bookstore.databinding.ActivityMainBinding
import com.example.bookstore.model.Books
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ClickDetectorInterface {

    // Binding object to access the UI elements
    private lateinit var binding: ActivityMainBinding

    // Database and DAO objects for interacting with the database
    private lateinit var db: ApplicationDatabase
    private lateinit var itemDao: ItemDao

    // List to hold the books fetched from the database
    private var rvData: MutableList<Books> = mutableListOf()

    // Adapter to bind the data to the RecyclerView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout and set the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title of the ActionBar
        supportActionBar!!.setTitle("Bookstore")

        // Initialize the database and DAO
        db = ApplicationDatabase.getInstance(this)
        itemDao = db.itemDao()

        // Set up the RecyclerView with the adapter
        bookAdapter = BookAdapter(rvData, this)
        binding.rvBooks.adapter = bookAdapter
        binding.rvBooks.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        // Reload the books when the activity is resumed
        loadBooks()
    }

    // Inflate the options menu (top-right three dots)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handle the item selected from the options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // When the add item button is clicked, open AddViewActivity
            R.id.addItem -> {
                val intent = Intent(this@MainActivity, AddViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to load all books from the database and update the RecyclerView
    private fun loadBooks() {
        lifecycleScope.launch {
            // Clear the existing data and fetch fresh data
            rvData.clear()
            rvData.addAll(itemDao.getAllBooks().toMutableList())

            // Notify the adapter that the data has changed
            bookAdapter.notifyDataSetChanged()

            // Show or hide the "No Books" text based on whether the list is empty
            if (rvData.isEmpty()) {
                binding.tvNoBooks.visibility = android.view.View.VISIBLE
                binding.rvBooks.visibility = android.view.View.GONE
            } else {
                binding.tvNoBooks.visibility = android.view.View.GONE
                binding.rvBooks.visibility = android.view.View.VISIBLE
            }
        }
    }

    // Delete the book from the database when the delete button is clicked
    override fun deleteClick(position: Int) {
        lifecycleScope.launch {
            itemDao.deleteBook(rvData[position])
            Toast.makeText(this@MainActivity, "${rvData[position].book_name} Book Deleted", Toast.LENGTH_SHORT).show()
            loadBooks()  // Refresh the list after deletion
        }
    }

    // Update the book when the update button is clicked
    override fun updateClick(position: Int) {
        val intent = Intent(this, UpdateBookActivity::class.java)
        // Pass the book ID to the UpdateBookActivity to identify which book to update
        intent.putExtra("book_id", rvData[position].id)
        startActivity(intent)  // Start the UpdateBookActivity
    }

    // Show details of the book when clicked
    override fun itemClick(position: Int) {
        val intent = Intent(this, DetailBookActivity::class.java)
        // Pass the book ID to the DetailBookActivity to fetch book details
        intent.putExtra("book_id", rvData[position].id)
        startActivity(intent)  // Start the DetailBookActivity
    }
}
