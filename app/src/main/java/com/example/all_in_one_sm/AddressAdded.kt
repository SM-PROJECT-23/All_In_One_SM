package com.example.all_in_one_sm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddressAdded : AppCompatActivity() {

    private lateinit var addressList: ArrayList<Address>  // Replace with your Address class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_added)

        // TODO: Retrieve your list of addresses here
        addressList = arrayListOf()  // Fill with your data

        val addressListView: ListView = findViewById(R.id.addressListView)
        addressListView.adapter = AddressAdapter()

        val addAddressButton: Button = findViewById(R.id.addAddressButton)
        addAddressButton.setOnClickListener {
            // Start your Add Address activity here
            val intent = Intent(this, AddressBook::class.java)
            startActivity(intent)
        }
    }

    inner class AddressAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return addressList.size
        }

        override fun getItem(position: Int): Any {
            return addressList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.address_item, null)

            val addressInfo: TextView = view.findViewById(R.id.addressInfo)
            val editLink: TextView = view.findViewById(R.id.editLink)
            val removeLink: TextView = view.findViewById(R.id.removeLink)

            val address = addressList[position]

            // Set your address info here
            addressInfo.text = "${address.name}, ${address.address}, ${address.city}, ${address.state}, ${address.postalCode}"

            editLink.setOnClickListener {
                // Handle your Edit action here
            }

            removeLink.setOnClickListener {
                // Handle your Remove action here
            }

            return view
        }
    }
}