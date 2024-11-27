package com.example.intellicateradmin

import MenuItemAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intellicateradmin.databinding.ActivityAllItemsBinding
import com.example.intellicateradmin.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemsActivity : AppCompatActivity() {
    private lateinit var  databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()

    private lateinit var binding: ActivityAllItemsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            finish()
        }





    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")

        // fetch data from data base
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                menuItems.clear()

                // loop for through each food item
                for (foodSnapshot in  snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }
    private fun setAdapter() {

        val adapter = MenuItemAdapter(this@AllItemsActivity,menuItems,databaseReference){ position ->
            deleteMenuItems(position)
        }

        binding.MenuRecyclerView1.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView1.adapter = adapter
    }

    private fun deleteMenuItems(position: Int) {
        val menuItemToDelete= menuItems[position]
        val menuItemKey = menuItemToDelete.menuKey
        val foodMenuReference = database.reference.child("menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener {task ->
            if (task.isSuccessful){
                menuItems.removeAt(position)
                binding.MenuRecyclerView1.adapter?.notifyItemRemoved(position)
            }else
            {
                Toast.makeText(this, "Item not Deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}