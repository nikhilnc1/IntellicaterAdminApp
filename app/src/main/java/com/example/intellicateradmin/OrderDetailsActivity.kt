package com.example.intellicateradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intellicateradmin.adapter.OrderDetailsAdapter
import com.example.intellicateradmin.databinding.ActivityOrderDetailsBinding
import com.example.waveoffoodadmin.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var tokenNum: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backeButton.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails?.let { orderDetails ->
            tokenNum = receivedOrderDetails.tokenNum
            foodNames = receivedOrderDetails.foodNames as ArrayList<String>
            foodQuantity = receivedOrderDetails.foodQuantities as ArrayList<Int>
            foodPrices = receivedOrderDetails.foodPrices as ArrayList<String>
            totalPrice = receivedOrderDetails.totalPrice

            setUserDetail()
            setAdapter()
        }
    }

    private fun setUserDetail() {
        binding.name.text = tokenNum
        binding.totalPay.text = totalPrice
    }

    private fun setAdapter() {
        binding.orderDetailRecyclerVew.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, foodNames, foodQuantity, foodPrices)
        binding.orderDetailRecyclerVew.adapter = adapter
    }
}
