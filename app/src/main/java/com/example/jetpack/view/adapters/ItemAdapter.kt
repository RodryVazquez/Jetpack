package com.example.jetpack.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack.R
import com.example.jetpack.model.ItemBreed
import com.example.jetpack.util.getProgressDrawable
import com.example.jetpack.util.loadImage
import com.example.jetpack.view.ListFragmentDirections
import kotlinx.android.synthetic.main.item_list.view.*

class ItemAdapter(val itemList: ArrayList<ItemBreed>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    fun updateItemList(newItemsList: List<ItemBreed>) {
        itemList.clear()
        itemList.addAll(newItemsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.txtTitleLegend.text = itemList[position].ItemName
        holder.view.txtSubtitleLegend.text = itemList[position].ItemLifeSpan
        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            action.detailId = if(itemList[position].itemId != null) itemList[position].itemId!!.toInt() else 0
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imgMainIcon.loadImage(itemList[position].ItemImageUrl, getProgressDrawable(holder.view.context))

    }

    override fun getItemCount() = itemList.size

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    }
}