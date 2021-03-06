package com.thinkup.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.thinkup.dotsindicator.DotsView
import com.thinkup.sample.utils.TestAdapter
import com.thinkup.stepsindicator.ext.toLinear
import kotlinx.android.synthetic.main.activity_dots.*
import kotlinx.android.synthetic.main.activity_dots.movesMessage
import kotlinx.android.synthetic.main.activity_dots.subView
import kotlinx.android.synthetic.main.activity_dots.testList

class DotsActivity : AppCompatActivity(), DotsView.Callback, MakerBottomSheet.DotViewCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.app_dots_name)
        setContentView(R.layout.activity_dots)

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
            setText(getString(R.string.app_balloon_message, getString(R.string.app_dots_name)))
            setTextColorResource(R.color.colorPrimary)
            setTextIsHtml(true)
            setBackgroundColorResource(R.color.colorAccent)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(lifecycleOwner)
        }.showAlignTop(containerDots)

        initViews()
        initList()
        initAdd()
    }

    private fun initViews() {
        dots.loadItems(3, 0)
        dots.setCallback(this)
        movesMessage.text = getString(R.string.app_move_message, getString(R.string.app_dots_name))
        dotsNext.setOnClickListener { dots.next() }
        dotsBack.setOnClickListener { dots.previous() }
    }

    private fun initList() {
        testList.adapter = TestAdapter()
        testList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        PagerSnapHelper().attachToRecyclerView(testList)
        dotsRec.attach(testList)
        dotsRec2.attach(testList)
        dotsRec.setCallback(this)
    }

    private fun initAdd() {
        dotsAdd.setOnClickListener {
            subView.addView(
                DotsView.Builder(this)
                    .setWidth(R.dimen.dot_width_select)
                    .setSelectedWidth(R.dimen.dot_width_select)
                    .setHeight(R.dimen.dot_width_select)
                    .setMargin(R.dimen.dot_width_select)
                    .setSelectedResource(R.color.colorAccent)
                    .setUnselectedResource(R.color.colorPrimaryDark)
                    .setGradient(false)
                    .setGradientSelectedPercentage(100)
                    .setGradientNearNextPercentage(100)
                    .setGradientNearPrePercentage(100)
                    .setGradientFarPercentage(100)
                    .setBorder(false)
                    .setRounded(true)
                    .setSteps(false)
                    .setLoader(false)
                    .setRepeat(DotsView.LOADER_INFINITE)
                    .setLoaderDelay(DotsView.DEFAULT_DELAY)
                    .setDuration(350)
                    .setStepsCount(4)
                    .setCurrentIndex(1)
                    .setCallback(this)
                    .build()
            )
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
                MakerBottomSheet.build(getString(R.string.app_dots_name))
                    .show(supportFragmentManager, this)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onIndexChange(previous: Int, current: Int) {
        Toast.makeText(this, "Previous:$previous, Current:$current", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(dotBuilder: DotsView.Builder) {
        val container = LinearLayout(this)
        val view = dotBuilder.build()
        container.setPadding(resources.getDimensionPixelSize(R.dimen.dimen_16))
        container.addView(view)
        view.layoutParams.toLinear().width = LinearLayout.LayoutParams.MATCH_PARENT
        AlertDialog.Builder(this)
            .setView(container)
            .setPositiveButton(R.string.app_ok) { dialog, _ -> dialog?.dismiss() }
            .show()
    }
}
