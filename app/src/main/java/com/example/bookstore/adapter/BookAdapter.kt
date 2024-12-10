package com.example.bookstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookstore.R
import com.example.bookstore.clickInterfacce.ClickDetectorInterface
import com.example.bookstore.databinding.SampleBookLayoutBinding
import com.example.bookstore.model.Books

// BookAdapter is responsible for binding data to the RecyclerView and handling item click actions.
class BookAdapter(
    val myItems: MutableList<Books>, // List of books that will be displayed in the RecyclerView
    val clickInterface: ClickDetectorInterface // Interface to handle click actions
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    // ViewHolder holds the views for each item, this is where we bind UI elements for each book.
    inner class ViewHolder(val binding: SampleBookLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    // This method is called when a new ViewHolder is created. It inflates the item layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val binding = SampleBookLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) // Return a ViewHolder instance with the inflated layout
    }

    // This method returns the total number of items (books) in the list.
    override fun getItemCount(): Int {
        return myItems.size
    }

    // This method is called to bind data to each item in the RecyclerView.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the current book object based on the position
        val currData: Books = this.myItems[position]

        // Load the book image using Glide library
        Glide.with(holder.itemView.context)
            .load(currData.url) // Image URL from the book data
            .placeholder(R.drawable.noimg) // Placeholder image while loading
            .error(R.drawable.noimg) // Error image if loading fails
            .into(holder.binding.ivBookImage) // Set the image into the ImageView

        // Bind the other book details to the respective TextViews
        holder.binding.tvBookname.text = currData.book_name
        holder.binding.tvAuthor.text = "~${currData.author}"
        holder.binding.tvPrice.text = "$${currData.price}"
        holder.binding.tvQuantity.text = "Available Books : ${currData.quantity}"
        holder.binding.tvDescription.text = currData.description.toString()

        // Set onClickListeners for the different actions (delete, update, view details)
        holder.binding.imageView2.setOnClickListener {
            // This triggers when the delete icon is clicked, calling the deleteClick method in the interface
            clickInterface.deleteClick(position)
        }

        holder.binding.imageView.setOnClickListener {
            // This triggers when the update icon is clicked, calling the updateClick method in the interface
            clickInterface.updateClick(position)
        }

        holder.binding.linear1.setOnClickListener {
            // This triggers when the whole item (book) is clicked, calling the itemClick method in the interface
            clickInterface.itemClick(position)
        }
    }
}
