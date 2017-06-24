package uk.co.jatra.invoice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by tim on 23/06/2017.
 */

class InvoiceView : LinearLayout {

    private lateinit var arrow : ImageView
    private lateinit var header : View
    private var open: Boolean = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
    }

    fun setInvoice(invoice: Invoice) {
        addHeaderView(invoice)
        for (group in invoice.groups) {
            addItemGroup(group)
        }
    }

    private fun addHeaderView(invoice: Invoice) {
        header = LayoutInflater.from(context).inflate(R.layout.invoice_header, null)
        (header.findViewById(R.id.header_text) as TextView).text = invoice.title
        arrow = header.findViewById(R.id.header_arrow) as ImageView
        setArrowExpanded(true)
        header.setOnClickListener { toggleExpand() }
        addView(header)
    }


    private fun toggleExpand() {
        if (open) {
            collapse(this, header.height)
        }
        else {
            expand(this)
        }

        open = !open
        setArrowExpanded(open)
    }

    private fun setArrowExpanded(selected: Boolean) {
        val stateSet = if (selected) intArrayOf(android.R.attr.state_expanded) else intArrayOf()
        arrow.setImageState(stateSet, true)
    }

    private fun addItemGroup(group: ItemGroup) {
        val header = LayoutInflater.from(context).inflate(R.layout.group_header, null) as TextView
        header.text = group.headerText
        addView(header)

        for (item in group.lineItems) {
            addLineItem(item)
        }
    }

    private fun addLineItem(item: LineItem) {
        val itemLine = LayoutInflater.from(context).inflate(R.layout.line_item, null)
        (itemLine.findViewById(R.id.description) as TextView).text = item.description
        (itemLine.findViewById(R.id.cost) as TextView).text = "£" + item.value
        addView(itemLine)
    }

    private fun expand(view: View) {
        val initialHeight = view.height
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = view.measuredHeight
        val heightChange = targetHeight - initialHeight

        view.layoutParams.height = view.height
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
                view.layoutParams.height = if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    initialHeight + (heightChange * interpolatedTime).toInt()
                }
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        runAnimation(view, animation, heightChange)
    }

    private fun collapse(view: View, targetHeight: Int) {
        val initialHeight = view.measuredHeight
        val heightChange = initialHeight - targetHeight

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
                view.layoutParams.height = initialHeight - (heightChange * interpolatedTime).toInt()
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        runAnimation(view, animation, heightChange)
    }

    private fun runAnimation(view: View, animation: Animation, heightChange: Int) {
        animation.duration = (heightChange / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }
}
