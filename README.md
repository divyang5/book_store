
# Bookstore Android App

This is a simple Android application for managing a bookstore, allowing users to add, update, view, and delete books from the store's database. The app includes features for displaying book details, editing book information, and managing book inventory.

## Features

- **Add a Book**: Allows users to add new books to the inventory.
- **View Book Details**: Displays detailed information about a book.
- **Update Book Information**: Enables users to update details of existing books.
- **Delete Books**: Allows users to remove books from the inventory.
- **RecyclerView for List**: A dynamic list of books with features like viewing and editing book details.

## Tech Stack

- **Kotlin**: The primary programming language for Android development.
- **Room Database**: A local SQLite database for storing book information.
- **Glide**: A powerful image loading and caching library for handling book cover images.
- **ViewBinding**: Provides direct access to views in layout files.
- **RecyclerView**: Displays a scrollable list of items (books in this case).
- **LifecycleScope**: Used for managing background tasks like database operations.

## File Structure

### 1. **Activity Classes**

#### `DetailBookActivity.kt`
- Displays the details of a selected book.
- Retrieves book information from the database using the `book_id` passed through the intent.
- Uses **Glide** to display the book cover image and sets other details like title, author, price, and quantity.

#### `UpdateBookActivity.kt`
- Allows the user to update book information.
- Retrieves the existing book details, allows editing, and saves the updated information back to the database.
- Validates the input fields before saving data.

#### `AddViewActivity.kt`
- Provides a form for adding a new book to the database.
- Collects details like book name, author, price, quantity, and description.
- Validates input before adding the book to the database.

### 2. **Adapter Classes**

#### `BookAdapter.kt`
- Binds book data to each item in a RecyclerView.
- Handles item clicks such as viewing details, updating, and deleting books using an interface (`ClickDetectorInterface`).
- Displays book images using **Glide** and handles the click events for each book in the list.

### 3. **Database Classes**

#### `ApplicationDatabase.kt`
- The singleton class that provides access to the database.
- Manages the creation of the database and its DAO (Data Access Object).

#### `ItemDao.kt`
- Interface that defines the CRUD operations for the `Books` entity (add, update, delete, fetch).

### 4. **Models**

#### `Books.kt`
- Data model representing a book with properties like name, author, price, quantity, and description.
- This is the entity class that is used by Room Database for storing book information.

### 5. **Interfaces**

#### `ClickDetectorInterface.kt`
- An interface that defines methods for detecting item click actions such as viewing, updating, and deleting books.
- The adapter calls these methods when the user interacts with a book item.

## Setup Instructions

1. **Open the project in Android Studio**.

2. **Install dependencies**:
    Ensure you have the following dependencies in your `build.gradle` file:
    ```gradle
    dependencies {
        implementation "androidx.recyclerview:recyclerview:1.2.1"
        implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        implementation "com.bumptech.glide:glide:4.13.2"
        implementation "androidx.room:room-runtime:2.4.2"
        annotationProcessor "androidx.room:room-compiler:2.4.2"
    }
    ```

3. **Run the app**:
    - Build and run the app on an Android emulator or physical device.

## How to Use

1. **Add a Book**: Navigate to the "Add Book" screen and fill in the required details (name, author, price, etc.), then click on the "Add Book" button to save it in the database.
2. **View Book Details**: Tap on any book in the list to view its details (name, author, price, description).
3. **Update Book Information**: Tap on the "Edit" button next to any book to update its details. Enter new values and save the changes.
4. **Delete a Book**: Tap on the "Delete" button next to any book to remove it from the list.

## Project Structure




<img width="511" alt="image" src="https://github.com/user-attachments/assets/f058323a-8ca1-466f-8de9-0e00bdb8a3ae">

<img width="510" alt="image" src="https://github.com/user-attachments/assets/5fc2ad15-70d3-428b-9ca4-70458935e255">

<img width="509" alt="image" src="https://github.com/user-attachments/assets/21d8bae5-7b17-48bd-8807-12d91035fd67">

<img width="513" alt="image" src="https://github.com/user-attachments/assets/d1dcb307-47fa-47f3-b4a4-a0cf5ebc1667">
