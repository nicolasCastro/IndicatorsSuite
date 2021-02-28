package com.thinkup.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_steps.*

class StepsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)

        stepBack.setOnClickListener { stepsView.previous() }
        stepNext.setOnClickListener { stepsView.next() }
    }
}