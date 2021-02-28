package com.thinkup.sample

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.thinkup.easycore.ViewRenderer
import com.thinkup.easylist.RendererAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = RendererAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
    }

    private fun initList() {
        featuresList.layoutManager = LinearLayoutManager(this)
        featuresList.adapter = adapter
        adapter.addRenderer(ItemRenderer(object : ItemRenderer.Callback {
            override fun onItemClick(position: Int) {
                this@MainActivity.onItemClick(position)
            }
        }))
        adapter.setItems(
            listOf(
                "Dots indicator", "Steps indicator",
            )
        )
    }

    private fun onItemClick(position: Int) {
        val intent = when (position) {
            0 -> Intent(this, DotsActivity::class.java)
            else -> Intent(this, StepsActivity::class.java)
        }
        startActivity(intent)
    }

    class ItemRenderer(private val callback: Callback) : ViewRenderer<String, Button>(String::class) {

        override fun create(parent: ViewGroup) = Button(parent.context)

        override fun bind(view: Button, model: String, position: Int) {
            view.text = model
            view.setOnClickListener { callback.onItemClick(position) }
        }

        interface Callback {
            fun onItemClick(position: Int)
        }
    }
}
