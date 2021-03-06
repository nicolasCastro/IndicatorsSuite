package com.thinkup.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thinkup.dotsindicator.DotsView
import com.thinkup.easylist.RendererAdapter
import com.thinkup.sample.utils.DotSpinnerRenderer
import com.thinkup.sample.utils.StepSpinnerRenderer
import com.thinkup.stepsindicator.StepsView
import kotlinx.android.synthetic.main.dialog_maker.*

class MakerBottomSheet : BottomSheetDialogFragment() {

    private val adapter = RendererAdapter()
    private var stepViewCallback: StepViewCallback? = null
    private var dotViewCallback: DotViewCallback? = null

    private val dotBuilder: DotsView.Builder by lazy {
        DotsView.Builder(requireContext())
            .setWidth(R.dimen.dimen_24)
            .setSelectedWidth(R.dimen.dimen_24)
            .setHeight(R.dimen.dimen_24)
            .setMargin(R.dimen.dimen_8)
            .setSelectedResource(R.color.green)
            .setUnselectedResource(R.color.red)
            .setGradient(false)
            .setGradientSelectedPercentage(100)
            .setGradientNearNextPercentage(100)
            .setGradientNearPrePercentage(100)
            .setGradientFarPercentage(100)
            .setBorder(false)
            .setRounded(true)
            .setSteps(false)
            .setLoader(false)
            .setRepeat(3)
            .setLoaderDelay(200)
            .setDuration(200)
            .setStepsCount(4)
            .setCurrentIndex(0)
    }
    private val stepBuilder: StepsView.Builder by lazy {
        StepsView.Builder(requireContext())
            .setCompletedColor(R.color.green)
            .setUncompletedColor(R.color.blue)
            .setTextCompletedColor(R.color.yellow)
            .setTextUncompletedColor(R.color.yellow)
            .setIconCompleted(R.drawable.tkup_completed_step)
            .setLineCompletedColor(R.color.green)
            .setLineUncompletedColor(R.color.blue)
            .setSelectedSize(R.dimen.dimen_24)
            .setSize(R.dimen.dimen_20)
            .setLineThickness(R.dimen.dimen_2)
            .setIsTouchable(true)
            .setShownCompetedIcon(true)
            .setDuration(200)
            .setStepsCount(3)
            .setCurrentIndex(0)
    }

    interface StepViewCallback {
        fun onCreateView(stepBuilder: StepsView.Builder)
    }

    interface DotViewCallback {
        fun onCreateView(dotBuilder: DotsView.Builder)
    }

    companion object {
        const val TYPE = "extra_type"
        const val MAKER_BOTTOM_SHEET = "tag_maker_bottom_sheet"
        fun build(selectedOption: String): MakerBottomSheet {
            val instance = MakerBottomSheet()
            instance.arguments = Bundle().apply {
                putString(TYPE, selectedOption)
            }
            return instance
        }
    }

    fun show(fragmentManager: FragmentManager, stepViewCallback: StepViewCallback) {
        this.stepViewCallback = stepViewCallback
        show(fragmentManager, MAKER_BOTTOM_SHEET)
    }

    fun show(fragmentManager: FragmentManager, dotViewCallback: DotViewCallback) {
        this.dotViewCallback = dotViewCallback
        show(fragmentManager, MAKER_BOTTOM_SHEET)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.dialog_maker, container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        builderList.layoutManager = LinearLayoutManager(context)
        builderList.adapter = adapter
        when (arguments?.getString(TYPE).orEmpty()) {
            getString(R.string.app_steps_name) -> createStepDialog()
            else -> createDotDialog()
        }
    }

    private fun createDotDialog() {
        adapter.addRenderer(DotSpinnerRenderer(this::onTypeSelectedValue))
        adapter.setItems(DotSpinnerRenderer.Types.values().map { DotSpinnerRenderer.Item(it, it.default) })
        doItButton.setOnClickListener { dotViewCallback?.onCreateView(dotBuilder) }
    }

    private fun createStepDialog() {
        adapter.addRenderer(StepSpinnerRenderer(this::onTypeSelectedValue))
        adapter.setItems(StepSpinnerRenderer.Types.values().map { StepSpinnerRenderer.Item(it, it.default) })
        doItButton.setOnClickListener { stepViewCallback?.onCreateView(stepBuilder) }
    }

    private fun onTypeSelectedValue(item: StepSpinnerRenderer.Types, value: String, position: Int) {
        when (item) {
            StepSpinnerRenderer.Types.COMPLETEDCOLOR -> stepBuilder.setCompletedColor(getColor(position))
            StepSpinnerRenderer.Types.UNCOMPLETEDCOLOR -> stepBuilder.setUncompletedColor(getColor(position))
            StepSpinnerRenderer.Types.TEXTCOMPLETEDCOLOR -> stepBuilder.setTextCompletedColor(getColor(position))
            StepSpinnerRenderer.Types.TEXTUNCOMPLETEDCOLOR -> stepBuilder.setTextUncompletedColor(getColor(position))
            StepSpinnerRenderer.Types.ICONCOMPLETED -> stepBuilder.setIconCompleted(getDrawable(position))
            StepSpinnerRenderer.Types.LINECOMPLETEDCOLOR -> stepBuilder.setLineCompletedColor(getColor(position))
            StepSpinnerRenderer.Types.LINEUNCOMPLETEDCOLOR -> stepBuilder.setLineUncompletedColor(getColor(position))
            StepSpinnerRenderer.Types.SELECTEDSIZE -> stepBuilder.setSelectedSize(getDimen(position))
            StepSpinnerRenderer.Types.SIZE -> stepBuilder.setSize(getDimen(position))
            StepSpinnerRenderer.Types.LINETHICKNESS -> stepBuilder.setLineThickness(getSmallDimen(position))
            StepSpinnerRenderer.Types.ISTOUCHABLE -> stepBuilder.setIsTouchable(getBoolean(position))
            StepSpinnerRenderer.Types.SHOWNCOMPETEDICON -> stepBuilder.setShownCompetedIcon(getBoolean(position))
            StepSpinnerRenderer.Types.DURATION -> stepBuilder.setDuration(value.toInt())
            StepSpinnerRenderer.Types.STEPSCOUNT -> stepBuilder.setStepsCount(value.toInt())
            StepSpinnerRenderer.Types.CURRENTINDEX -> stepBuilder.setCurrentIndex(value.toInt() - 1)
        }
    }

    private fun onTypeSelectedValue(item: DotSpinnerRenderer.Types, value: String, position: Int) {
        when (item) {
            DotSpinnerRenderer.Types.WIDTH -> dotBuilder.setWidth(getDimen(position))
            DotSpinnerRenderer.Types.SELECTEDWIDTH -> dotBuilder.setSelectedWidth(getDimen(position))
            DotSpinnerRenderer.Types.HEIGHT -> dotBuilder.setHeight(getDimen(position))
            DotSpinnerRenderer.Types.MARGIN -> dotBuilder.setMargin(getDimen(position))
            DotSpinnerRenderer.Types.SELECTEDRESOURCE -> dotBuilder.setSelectedResource(getColor(position))
            DotSpinnerRenderer.Types.UNSELECTEDRESOURCE -> dotBuilder.setUnselectedResource(getColor(position))
            DotSpinnerRenderer.Types.GRADIENT -> dotBuilder.setGradient(getBoolean(position))
            DotSpinnerRenderer.Types.GRADIENTSELECTEDPERCENTAGE -> dotBuilder.setGradientSelectedPercentage(value.toInt())
            DotSpinnerRenderer.Types.GRADIENTNEARNEXTPERCENTAGE -> dotBuilder.setGradientNearNextPercentage(value.toInt())
            DotSpinnerRenderer.Types.GRADIENTNEARPREPERCENTAGE -> dotBuilder.setGradientNearPrePercentage(value.toInt())
            DotSpinnerRenderer.Types.GRADIENTFARPERCENTAGE -> dotBuilder.setGradientFarPercentage(value.toInt())
            DotSpinnerRenderer.Types.BORDER -> dotBuilder.setBorder(getBoolean(position))
            DotSpinnerRenderer.Types.ROUNDED -> dotBuilder.setRounded(getBoolean(position))
            DotSpinnerRenderer.Types.STEPS -> dotBuilder.setSteps(getBoolean(position))
            DotSpinnerRenderer.Types.LOADER -> dotBuilder.setLoader(getBoolean(position))
            DotSpinnerRenderer.Types.REPEAT -> dotBuilder.setRepeat(value.toInt())
            DotSpinnerRenderer.Types.LOADERDELAY -> dotBuilder.setLoaderDelay(value.toInt())
            DotSpinnerRenderer.Types.DURATION -> dotBuilder.setDuration(value.toInt())
            DotSpinnerRenderer.Types.STEPSCOUNT -> dotBuilder.setStepsCount(value.toInt())
            DotSpinnerRenderer.Types.CURRENTINDEX -> dotBuilder.setCurrentIndex(value.toInt() - 1)
        }
    }

    private fun getColor(position: Int): Int {
        return when (position) {
            1 -> R.color.blue
            2 -> R.color.green
            3 -> R.color.yellow
            4 -> R.color.orange
            5 -> R.color.violet
            else -> R.color.red
        }
    }

    private fun getDimen(position: Int): Int {
        return when (position) {
            1 -> R.dimen.dimen_20
            2 -> R.dimen.dimen_24
            3 -> R.dimen.dimen_28
            4 -> R.dimen.dimen_32
            else -> R.dimen.dimen_16
        }
    }

    private fun getSmallDimen(position: Int): Int {
        return when (position) {
            1 -> R.dimen.dimen_4
            2 -> R.dimen.dimen_8
            3 -> R.dimen.dimen_10
            4 -> R.dimen.dimen_12
            else -> R.dimen.dimen_2
        }
    }

    private fun getBoolean(position: Int): Boolean {
        return when (position) {
            1 -> true
            else -> false
        }
    }

    private fun getDrawable(position: Int): Int {
        return when (position) {
            1 -> R.drawable.tkup_completed_step
            2 -> R.drawable.ic_checked
            else -> 0
        }
    }
}