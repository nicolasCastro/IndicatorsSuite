package com.thinkup.sample

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.thinkup.dotsindicator.DotsCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.test_item.view.*

class MainActivity : AppCompatActivity(), DotsCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dots.loadItems(3, 0)

        testNextView.setOnClickListener { dots.next() }
        testPreviousView.setOnClickListener { dots.previous() }

        testList.adapter = TestAdapter()
        testList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(testList)
        dotsRec.attach(testList)
        dotsRec.setCallback(this)
    }

    class TestAdapter(private val items: List<String> = listOf("Item1", "Item2", "Item3")) :
        RecyclerView.Adapter<TestAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false))

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(items[position])
        }

        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: String) {
                itemView.testItemView.text = item
            }
        }
    }

    override fun onIndexChange(previous: Int, current: Int) {
        Toast.makeText(this, "Previous:$previous, Current:$current", Toast.LENGTH_SHORT).show()
    }
}
