package com.thinkup.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.thinkup.dotsindicator.DotsCallback
import kotlinx.android.synthetic.main.activity_dots.*

class DotsActivity : AppCompatActivity(), DotsCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dots)
        dots.loadItems(3, 0)

        testNextView.setOnClickListener { dots.next() }
        testPreviousView.setOnClickListener { dots.previous() }

        testList.adapter = TestAdapter()
        testList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(testList)
        dotsRec.attach(testList)
        dotsRec.setCallback(this)
    }

    override fun onIndexChange(previous: Int, current: Int) {
        Toast.makeText(this, "Previous:$previous, Current:$current", Toast.LENGTH_SHORT).show()
    }
}
