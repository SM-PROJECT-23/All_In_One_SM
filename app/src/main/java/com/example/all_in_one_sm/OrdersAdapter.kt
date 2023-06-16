package com.example.all_in_one_sm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class OrderAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderNumberTextView: TextView = view.findViewById(R.id.orderNumberTextView)
        val sellerNameTextView: TextView = view.findViewById(R.id.sellerNameTextView)
        val itemListRecyclerView: RecyclerView = view.findViewById(R.id.itemListRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderNumberTextView.text = "Order #${order.orderNumber}"
        holder.sellerNameTextView.text = order.sellerName

        val itemAdapter = ItemAdapter(order.items)
        holder.itemListRecyclerView.layoutManager =
            LinearLayoutManager(holder.itemListRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.itemListRecyclerView.adapter = itemAdapter
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

class ItemAdapter(private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImageView: ImageView = view.findViewById(R.id.itemImageView)
        val itemNameTextView: TextView = view.findViewById(R.id.itemNameTextView)
        val itemPriceTextView: TextView = view.findViewById(R.id.itemPriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameTextView.text = item.name
        holder.itemPriceTextView.text = item.price.toString()

        // load image with Picasso
        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.itemImageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

