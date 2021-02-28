package com.thinkup.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.thinkup.stepsindicator.StepsView
import kotlinx.android.synthetic.main.activity_steps.*
import kotlinx.android.synthetic.main.activity_steps.testList

class StepsActivity : AppCompatActivity(), StepsView.Callback {
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

        stepAdd.setOnClickListener {
            subView.addView(
                StepsView.Builder(this)
                    .setStepsCount(4)
                    .setCurrentIndex(0)
                    .setCompletedColor(R.color.colorPrimaryDark)
                    .setIsTouchable(true)
                    .setCallback(this)
                    .build()
            )
        }

        testList.adapter = TestAdapter()
        testList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(testList)
        stepsView6.attach(testList)
    }

    override fun onStepChanged(oldIndex: Int, newIndex: Int) {
        Toast.makeText(this, "Change from index $oldIndex to index $newIndex", Toast.LENGTH_SHORT).show()
    }
}