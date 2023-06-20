package com.example.all_in_one_sm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class Orders : AppCompatActivity() {

    private lateinit var orders: List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Sample orders
        orders = listOf(
            Order(
                orderNumber = 12345,
                sellerName = "Seller 1",
                items = listOf(
                    Item(
                        imageUrl = "http://example.com/path-to-your-image.jpg",
                        name = "Item 1",
                        price = 10.99
                    ),
                    Item(
                        imageUrl = "http://example.com/path-to-your-image.jpg",
                        name = "Item 2",
                        price = 20.99
                    )
                ),
                orderDate = Date(2023,1,10)
            ),
            Order(
                orderNumber = 12346,
                sellerName = "Seller 2",
                items = listOf(
                    Item(
                        imageUrl = "https://cdn.pixabay.com/photo/2016/12/06/09/31/blank-1886008_640.png",
                        name = "Item 3",
                        price = 15.99
                    ),
                    Item(
                        imageUrl = "https://media.istockphoto.com/id/488160041/photo/mens-shirt.jpg?s=612x612&w=0&k=20&c=xVZjKAUJecIpYc_fKRz_EB8HuRmXCOOPOtZ-ST6eFvQ=",
                        name = "Item 4",
                        price = 25.99
                    ),
                    Item(
                        imageUrl = "https://media.istockphoto.com/id/488160041/photo/mens-shirt.jpg?s=612x612&w=0&k=20&c=xVZjKAUJecIpYc_fKRz_EB8HuRmXCOOPOtZ-ST6eFvQ=",
                        name = "Item 5",
                        price = 25.99
                    ),
                    Item(
                        imageUrl = "https://media.istockphoto.com/id/488160041/photo/mens-shirt.jpg?s=612x612&w=0&k=20&c=xVZjKAUJecIpYc_fKRz_EB8HuRmXCOOPOtZ-ST6eFvQ=",
                        name = "Item 6",
                        price = 25.99
                    )
                ),
                orderDate = Date(2023,2,10)
            )
        )

        val orderListRecyclerView: RecyclerView = findViewById(R.id.orderListRecyclerView)
        orderListRecyclerView.layoutManager = LinearLayoutManager(this)

        // Adding divider
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        orderListRecyclerView.addItemDecoration(dividerItemDecoration)

        orderListRecyclerView.adapter = OrderAdapter(orders)
    }
}


