package com.example.intellicateradmin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.intellicateradmin.databinding.ActivityAddItemBinding
import com.example.intellicateradmin.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddItemActivity : AppCompatActivity() {
    // Food item Details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String

    // Firebase
//    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.AddItemButton.setOnClickListener {


            // Initialize Firebase database instance
            database = FirebaseDatabase.getInstance()
            // Get Data from Filed
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient= binding.ingredint.text.toString().trim()

            if (!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        val menuRef = database.getReference("menu")
        // Generate a unique key for the new menu item
        val newMenuKey = menuRef.push().key

        //Create a new menu item without the image
        val newItem = AllMenu(
            newMenuKey,
            foodName = foodName,
            foodPrice = foodPrice,
            foodDescription = foodDescription,
            foodIngredient = foodIngredient,
        )

        newMenuKey?.let { menukey ->
            menuRef.child(menukey).setValue(newItem).addOnSuccessListener {
                Toast.makeText(this, "data uploaded successfully", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(this, "data uploaded failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

}