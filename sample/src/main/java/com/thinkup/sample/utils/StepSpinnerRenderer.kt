package com.thinkup.sample.utils

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import com.thinkup.easycore.ViewRenderer
import com.thinkup.sample.R
import com.thinkup.sample.StepSpinnerCallback
import kotlinx.android.synthetic.main.item_spinner.view.*

class StepSpinnerRenderer(val callback: StepSpinnerCallback) : ViewRenderer<StepSpinnerRenderer.Item, View>(Item::class) {

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
        val stepTypes: Types,
        var position: Int = 0
    )

    enum class Types(val title: String, @ArrayRes val array: Int, val default: Int) {
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
        CURRENTINDEX("Current selected index", R.array.steps_index_array, 0)
    }
}