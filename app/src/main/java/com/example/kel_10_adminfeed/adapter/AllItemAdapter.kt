package com.example.kel_10_adminfeed.adapter
import android.content.ClipData.Item
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kel_10_adminfeed.databinding.ItemItemBinding


class AllItemAdapter(private val MenuItemName:ArrayList<String>,private val MenuItemPrice:ArrayList<String>,private val MenuItemImage:ArrayList<String>): RecyclerView.Adapter <AllItemAdapter.AddItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding =ItemItemBinding.inflate
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :RecyclerView.ViewHolder(binding.root) {

    }
}