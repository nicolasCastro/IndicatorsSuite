package com.thinkup.sample

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.balloon.*
import com.thinkup.sample.utils.DotSpinnerRenderer
import com.thinkup.sample.utils.StepSpinnerRenderer
import com.thinkup.sample.utils.TestAdapter
import com.thinkup.stepsindicator.StepsView
import com.thinkup.stepsindicator.ext.toLinear
import kotlinx.android.synthetic.main.activity_steps.*
import kotlinx.android.synthetic.main.activity_steps.testList

typealias DotSpinnerCallback = (DotSpinnerRenderer.Types, String, Int) -> Unit
typealias StepSpinnerCallback = (StepSpinnerRenderer.Types, String, Int) -> Unit

class StepsActivity : AppCompatActivity(), StepsView.Callback, MakerBottomSheet.StepViewCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_steps_name)
        setContentView(R.layout.activity_steps)

        setNavListener()
        setAddListener()

        movesMessage.text = getString(R.string.app_move_message, getString(R.string.app_steps_name))
        testList.adapter = TestAdapter()
        testList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(testList)
        stepsView6.attach(testList)

        createBalloon(this) {
            setArrowSize(10)
            setMarginLeft(50)
            setWidth(BalloonSizeSpec.WRAP)
            setHeight(65)
            setCornerRadius(4f)
            setPadding(10)
            setArrowOrientation(ArrowOrientation.RIGHT)
            setAlpha(0.9f)
            setAutoDismissDuration(2500)
            setText(getString(R.string.app_balloon_message, getString(R.string.app_steps_name)))
            setTextColorResource(R.color.colorPrimary)
            setTextIsHtml(true)
            setBackgroundColorResource(R.color.colorAccent)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }.showAlignTop(containerSteps)
    }

    private fun setAddListener() {
        stepAdd.setOnClickListener {
            subView.addView(
                StepsView.Builder(this)
                    .setCompletedColor(R.color.dark)
                    .setUncompletedColor(R.color.light_black)
                    .setTextCompletedColor(R.color.colorAccent)
                    .setTextUncompletedColor(R.color.colorPrimary)
                    .setIconCompleted(R.drawable.tkup_completed_step)
                    .setLineCompletedColor(R.color.dark)
                    .setLineUncompletedColor(R.color.light_black)
                    .setSelectedSize(R.dimen.step_size)
                    .setSize(R.dimen.step_size)
                    .setLineThickness(R.dimen.step_line_thickness)
                    .setIsTouchable(true)
                    .setBorder(false)
                    .setShownCompetedIcon(true)
                    .setShownUncompletedResource(false)
                    .setDuration(350)
                    .setStepsCount(4)
                    .setCurrentIndex(0)
                    .setCallback(this)
                    .setLineMargins(R.dimen.dimen_16)
                    .build()
            )
        }
    }

    private fun setNavListener() {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.make_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.makeAction -> {
                MakerBottomSheet.build(getString(R.string.app_steps_name))
                    .show(supportFragmentManager, this)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onStepChanged(oldIndex: Int, newIndex: Int) {
        Toast.makeText(this, "Change from index $oldIndex to index $newIndex", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(stepBuilder: StepsView.Builder) {
        val container = LinearLayout(this)
        val view = stepBuilder.build()
        container.setPadding(resources.getDimensionPixelSize(R.dimen.dimen_16))
        container.addView(view)
        view.layoutParams.toLinear().width = LinearLayout.LayoutParams.MATCH_PARENT
        AlertDialog.Builder(this)
            .setView(container)
            .setPositiveButton(R.string.app_ok) { dialog, _ -> dialog?.dismiss() }
            .show()
    }
}