package com.example.kel_10_adminfeed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kel_10_adminfeed.databinding.PendingOrderItemBinding

class PendingOrderAdapter (
    private val customerNames:ArrayList<String>,
    private val quantity: ArrayList<String>,
    private val foodImage:ArrayList<Int>,
    private  val context:Context
    ):RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding=PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int = customerNames.size
    inner class PendingOrderViewHolder (private val binding: PendingOrderItemBinding):RecyclerView.ViewHolder(binding.root){
        private var isAcceted =false
        fun bind(position: Int) {
            binding.apply {
                customerName.text=customerNames[position]
                priceTextView.text=quantity[position]
                OrderdImageView.setImageResource(foodImage[position])

                orderdAcceptedButton.apply {
                if (!isAcceted){
                    text="Accept"
                }else{
                    text="Dispact"
                }
                    setOnClickListener {
                        if (!isAcceted){
                            text ="Dispatch"
                            isAcceted=true
                            showToast("Order Is Accepted")
                        }else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemChanged(adapterPosition)
                            showToast("Order is Dispatch")


                        }
                    }
                }

            }


        }
        private fun showToast(mesege:String){
            Toast.makeText(context,mesege,Toast.LENGTH_SHORT).show()
        }

    }
}