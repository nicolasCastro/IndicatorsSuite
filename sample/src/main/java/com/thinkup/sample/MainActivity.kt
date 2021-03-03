package com.thinkup.sample

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import com.thinkup.easycore.ViewRenderer
import com.thinkup.easylist.RendererAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_header_home.view.*

typealias ItemCallback = (Int) -> Unit
typealias LinkCallback = (String) -> Unit

class MainActivity : AppCompatActivity() {

    private val adapter = RendererAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
    }

    private fun initList() {
        featuresList.layoutManager = LinearLayoutManager(this)
        featuresList.adapter = adapter
        adapter.addRenderer(HeaderRenderer(this::onLinkClick))
        adapter.addRenderer(ItemRenderer(this::onItemClick))
        adapter.setItems(
            listOf(
                HeaderRenderer.Item(getString(R.string.app_title), getString(R.string.app_message), getString(R.string.app_repo_link)),
                "Dots indicator samples",
                "Steps indicator samples"
            )
        )
    }

    private fun onLinkClick(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    private fun onItemClick(position: Int) {
        val intent = when (position) {
            1 -> Intent(this, DotsActivity::class.java)
            else -> Intent(this, StepsActivity::class.java)
        }
        startActivity(intent)
    }

    class ItemRenderer(private val callback: ItemCallback) : ViewRenderer<String, Button>(String::class) {

        override fun create(parent: ViewGroup): Button {
            val style = R.style.Widget_AppCompat_Button_Borderless_Colored
            val button = Button(ContextThemeWrapper(parent.context, style), null, style)
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_right_arrow, 0)
            button.compoundDrawablePadding = parent.context.resources.getDimensionPixelSize(R.dimen.margin)
            button.gravity = Gravity.CENTER
            return button
        }

        override fun bind(view: Button, model: String, position: Int) {
            view.text = model
            view.setOnClickListener { callback(position) }
        }
    }

    class HeaderRenderer(private val callback: LinkCallback) : ViewRenderer<HeaderRenderer.Item, View>(Item::class) {

        override fun create(parent: ViewGroup) = inflate(R.layout.item_header_home, parent, false)

        override fun bind(view: View, model: Item, position: Int) {
            view.gitlabTitle.text = model.title
            view.gitlabMessage.text = model.message
            view.gitlabLink.text = model.link
            view.gitlabLink.paintFlags = Paint.UNDERLINE_TEXT_FLAG or view.gitlabLink.paintFlags
            view.gitlabLink.setOnClickListener { callback(model.link) }
        }

        data class Item(val title: String, val message: String, val link: String)
    }
}
