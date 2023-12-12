package com.example.kel_10_adminfeed.adapter
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kel_10_adminfeed.databinding.ItemItemBinding
import com.example.kel_10_adminfeed.model.AllMenu
import com.google.firebase.database.DatabaseReference


class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference,


    ):RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>(){
    private val itemQuantities = IntArray(menuList.size){1}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = menuList.size


    inner class AddItemViewHolder(private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem= menuList[position]
                val uriString =menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodNameTextView.text=menuItem.foodName
                priceTextView.text=menuItem.foodPrice
                Glide.with(context).load(uri).into(foodImageView)

                quantityTextView.text=quantity.toString()

                minusButton.setOnClickListener {
                    decreseQuantity(position)
                }
                plusButton.setOnClickListener {
                    IncreaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    deleteQuantity(position)
                }


            }

        }
        private fun IncreaseQuantity(position: Int) {
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                binding.quantityTextView.text=itemQuantities[position].toString()
            }
        }
        private fun decreseQuantity(position: Int) {
            if (itemQuantities[position]>1){
                itemQuantities[position]--
                binding.quantityTextView.text=itemQuantities[position].toString()
            }
        }
        private fun deleteQuantity(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }

    }




}

