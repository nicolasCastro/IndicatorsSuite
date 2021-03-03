package com.thinkup.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thinkup.easycore.ViewRenderer
import com.thinkup.easylist.RendererAdapter
import com.thinkup.stepsindicator.StepsView
import kotlinx.android.synthetic.main.dialog_maker.*
import kotlinx.android.synthetic.main.item_spinner.view.*


class MakerBottomSheet : BottomSheetDialogFragment() {

    private val adapter = RendererAdapter()
    private var callback: Callback? = null

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
            .setDuration(350)
            .setStepsCount(3)
            .setCurrentIndex(0)
    }

    interface Callback {
        fun onCreateView(stepBuilder: StepsView.Builder)
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

    fun show(fragmentManager: FragmentManager, callback: Callback) {
        this.callback = callback
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
        adapter.addRenderer(SpinnerRenderer(this::onTypeSelectedValue))
        builderList.adapter = adapter
        val type = arguments?.getString(TYPE).orEmpty()
        adapter.setItems(StepTypes.values().map { SpinnerRenderer.Item(it, it.default) })
        doItButton.setOnClickListener { callback?.onCreateView(stepBuilder) }
    }

    private fun onTypeSelectedValue(item: StepTypes, value: String, position: Int) {
        when (item) {
            StepTypes.COMPLETEDCOLOR -> stepBuilder.setCompletedColor(getColor(position))
            StepTypes.UNCOMPLETEDCOLOR -> stepBuilder.setUncompletedColor(getColor(position))
            StepTypes.TEXTCOMPLETEDCOLOR -> stepBuilder.setTextCompletedColor(getColor(position))
            StepTypes.TEXTUNCOMPLETEDCOLOR -> stepBuilder.setTextUncompletedColor(getColor(position))
            StepTypes.ICONCOMPLETED -> stepBuilder.setIconCompleted(getDrawable(position))
            StepTypes.LINECOMPLETEDCOLOR -> stepBuilder.setLineCompletedColor(getColor(position))
            StepTypes.LINEUNCOMPLETEDCOLOR -> stepBuilder.setLineUncompletedColor(getColor(position))
            StepTypes.SELECTEDSIZE -> stepBuilder.setSelectedSize(getDimen(position))
            StepTypes.SIZE -> stepBuilder.setSize(getDimen(position))
            StepTypes.LINETHICKNESS -> stepBuilder.setLineThickness(getSmallDimen(position))
            StepTypes.ISTOUCHABLE -> stepBuilder.setIsTouchable(getBoolean(position))
            StepTypes.SHOWNCOMPETEDICON -> stepBuilder.setShownCompetedIcon(getBoolean(position))
            StepTypes.DURATION -> stepBuilder.setDuration(value.toInt())
            StepTypes.STEPSCOUNT -> stepBuilder.setStepsCount(value.toInt())
            StepTypes.CURRENTINDEX -> stepBuilder.setCurrentIndex(value.toInt() - 1)
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


    class SpinnerRenderer(val callback: SpinnerCallback) : ViewRenderer<SpinnerRenderer.Item, View>(Item::class) {

        override fun create(parent: ViewGroup): View = inflate(R.layout.item_spinner, parent, false)

        override fun bind(view: View, model: Item, position: Int) {
            val items = view.resources.getStringArray(model.stepTypes.array)
            view.spinnerTitle.text = model.stepTypes.title
            val spinnerArrayAdapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, items)
            view.spinnerView.adapter = spinnerArrayAdapter
            view.spinnerView.setSelection(model.position)
            view.spinnerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    model.position = position
                    callback(model.stepTypes, items[position], position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        class Item(
            val stepTypes: StepTypes,
            var position: Int = 0
        )
    }

    enum class StepTypes(val title: String, @ArrayRes val array: Int, val default: Int) {
        COMPLETEDCOLOR("Step completed color", R.array.colors_array, 2),
        UNCOMPLETEDCOLOR("Step uncompleted color", R.array.colors_array, 1),
        TEXTCOMPLETEDCOLOR("Text completed color", R.array.colors_array, 3),
        TEXTUNCOMPLETEDCOLOR("Text uncompleted color", R.array.colors_array, 3),
        ICONCOMPLETED("Icon completed", R.array.images_array, 1),
        LINECOMPLETEDCOLOR("Line completed color", R.array.colors_array, 2),
        LINEUNCOMPLETEDCOLOR("Line uncompleted color", R.array.colors_array, 1),
        SELECTEDSIZE("Step focused size", R.array.dimens_array, 2),
        SIZE("Step unfocused size", R.array.dimens_array, 1),
        LINETHICKNESS("Line thickness", R.array.thick_array, 0),
        ISTOUCHABLE("Steps touchable", R.array.boolean_array, 1),
        SHOWNCOMPETEDICON("Show icon on completed step", R.array.boolean_array, 1),
        DURATION("Animation duration", R.array.anim_array, 1),
        STEPSCOUNT("Steps count", R.array.steps_array, 1),
        CURRENTINDEX("Current selected step", R.array.steps_index_array, 0)
    }
}