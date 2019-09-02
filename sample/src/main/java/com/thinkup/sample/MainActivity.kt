package com.thinkup.sample

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dots.loadItems(3, 0)

        testNextView.setOnClickListener { dots.next() }
        testPreviousView.setOnClickListener { dots.previous() }
    }
}
