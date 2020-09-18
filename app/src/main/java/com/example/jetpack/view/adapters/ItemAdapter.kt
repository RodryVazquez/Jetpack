package com.example.jetpack.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack.R
import com.example.jetpack.databinding.ItemListBinding
import com.example.jetpack.model.ItemBreed
import com.example.jetpack.util.getProgressDrawable
import com.example.jetpack.util.loadImage
import com.example.jetpack.view.ItemClickListener
import com.example.jetpack.view.ListFragmentDirections
import kotlinx.android.synthetic.main.item_list.view.*

class ItemAdapter(val itemList: ArrayList<ItemBreed>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemClickListener {

    fun updateItemList(newItemsList: List<ItemBreed>) {
        itemList.clear()
        itemList.addAll(newItemsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =  DataBindingUtil.inflate<ItemListBinding>(inflater, R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.item = itemList[position]
        holder.view.listener = this
    }

    override fun getItemCount() = itemList.size

    class ItemViewHolder(val view: ItemListBinding) : RecyclerView.ViewHolder(view.root){
    }

    override fun onItemClickListener(view: View) {
        val itemId = view.itemId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.detailId = itemId
        Navigation.findNavController(view).navigate(action)
    }
}