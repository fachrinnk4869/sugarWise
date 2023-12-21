package com.example.sugarwiseapp.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sugarwiseapp.data.response.DataFoodDaily
import com.example.sugarwiseapp.data.response.DataItem
import com.example.sugarwiseapp.data.response.DataItemAllFood
import com.example.sugarwiseapp.databinding.ItemListFoodBinding
import com.example.sugarwiseapp.view.home.HomeViewModel
import java.util.ArrayList
import java.util.TimeZone

class ListFoodAdapter : RecyclerView.Adapter<ListFoodAdapter.FoodViewHolder>() {
    private val listFood = ArrayList<DataItem>()


    @SuppressLint("NotifyDataSetChanged")
    fun setList(stories: ArrayList<DataItem>) {
        Log.d("Adapter", stories.toString())
        listFood.clear()
        listFood.addAll(stories)
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(private val binding: ItemListFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: DataItem) {
            binding.apply {
                txtfoodlist.text = food.data.foodName
                txtfoodserv.text = food.data.amount.toString()
                txtsugarcount.text = food.data.totalSugar.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemListFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(listFood[position])
    }

    override fun getItemCount(): Int = listFood.size
}