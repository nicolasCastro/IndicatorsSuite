package com.thinkup.sample.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thinkup.sample.R
import kotlinx.android.synthetic.main.test_item.view.*

class TestAdapter(private val items: List<String> = listOf("Item1", "Item2", "Item3")) :
    RecyclerView.Adapter<TestAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
        holder.itemView.layoutParams.width = (holder.itemView.resources.displayMetrics.widthPixels * 70) / 100
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String) {
            itemView.testItemView.text = item
        }
    }
}