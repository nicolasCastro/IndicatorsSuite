package com.thinkup.sample.utils

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import com.thinkup.easycore.ViewRenderer
import com.thinkup.sample.DotSpinnerCallback
import com.thinkup.sample.R
import kotlinx.android.synthetic.main.item_spinner.view.*

class DotSpinnerRenderer(val callback: DotSpinnerCallback) : ViewRenderer<DotSpinnerRenderer.Item, View>(Item::class) {

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
        WIDTH("Dot width", R.array.dimens_array, 2),
        SELECTEDWIDTH("Dot selected width", R.array.dimens_array, 2),
        HEIGHT("Dot height", R.array.dimens_array, 2),
        MARGIN("Dot margins", R.array.thick_array, 2),
        SELECTEDRESOURCE("Dot selected resource", R.array.colors_array, 2),
        UNSELECTEDRESOURCE("Dot unselected resource", R.array.colors_array, 0),
        GRADIENT("Dot gradient progress", R.array.boolean_array, 0),
        GRADIENTSELECTEDPERCENTAGE("Selected dot gradient %", R.array.percentage_array, 5),
        GRADIENTNEARNEXTPERCENTAGE("Next dot gradient %", R.array.percentage_array, 5),
        GRADIENTNEARPREPERCENTAGE("Previous dot gradient %", R.array.percentage_array, 5),
        GRADIENTFARPERCENTAGE("Others dots gradient %", R.array.percentage_array, 5),
        BORDER("Dot with borders", R.array.boolean_array, 0),
        ROUNDED("Use rounded dots", R.array.boolean_array, 1),
        STEPS("Use like steps indicators", R.array.boolean_array, 0),
        LOADER("Use like a loader indicator", R.array.boolean_array, 0),
        REPEAT("If is loader, haw many time repeat that", R.array.steps_index_array, 2),
        LOADERDELAY("Delay between dots", R.array.anim_array, 1),
        DURATION("Animation duration", R.array.anim_array, 1),
        STEPSCOUNT("Count of dots in view", R.array.steps_array, 2),
        CURRENTINDEX("Current selected selected width", R.array.steps_index_array, 0)
    }
}