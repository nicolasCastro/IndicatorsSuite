package com.thinkup.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_steps.*

class StepsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_steps)

        stepBack.setOnClickListener {
            stepsView.previous()
            stepsView2.previous()
            stepsView3.previous()
            stepsView4.previous()
            stepsView5.previous()
        }
        stepNext.setOnClickListener {
            stepsView.next()
            stepsView2.next()
            stepsView3.next()
            stepsView4.next()
            stepsView5.next()
        }
    }
}