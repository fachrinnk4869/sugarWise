package com.example.sugarwiseapp.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sugarwiseapp.data.response.DataItem
import com.example.sugarwiseapp.data.response.DataItemAllFood
import com.example.sugarwiseapp.databinding.ItemListFoodAllBinding
import com.example.sugarwiseapp.databinding.ItemListFoodBinding
import java.util.ArrayList

class ListFoodAllAdapter : RecyclerView.Adapter<ListFoodAllAdapter.FoodViewHolder>() {
    private val listFood = ArrayList<DataItemAllFood>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(stories: ArrayList<DataItemAllFood>) {
        listFood.clear()
        listFood.addAll(stories)
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(private val binding: ItemListFoodAllBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: DataItemAllFood) {
            binding.apply {
                txtfoodlist.text = food.data.foodName
                txtsugarcount.text = food.data.sugars.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemListFoodAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(listFood[position])
    }

    override fun getItemCount(): Int = listFood.size
}