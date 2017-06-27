package uk.co.jatra.invoice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView



/**
 * Created by tim on 23/06/2017.
 */

class InvoiceView : LinearLayout {

    private lateinit var headerArrow: ImageView
    private lateinit var footerArrow: ImageView
    private lateinit var header : View
    private lateinit var footer : View
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
        addFooterView(invoice)
    }

    private fun addHeaderView(invoice: Invoice) {
        header = LayoutInflater.from(context).inflate(R.layout.invoice_header, null)
        (header.findViewById(R.id.header_text) as TextView).text = invoice.title
        headerArrow = header.findViewById(R.id.header_arrow) as ImageView
        setHeaderArrowExpanded(true)
        header.setOnClickListener { toggleExpand() }
        addView(header)
    }

    private fun addFooterView(invoice: Invoice) {
        footer = LayoutInflater.from(context).inflate(R.layout.invoice_header, null)
        (footer.findViewById(R.id.header_text) as TextView).text = invoice.title
        footerArrow = footer.findViewById(R.id.header_arrow) as ImageView
        setFooterArrowExpanded(true)
        footer.setOnClickListener { toggleExpand() }
        addView(footer)
    }


    private fun toggleExpand() {
        if (open) {
            AnimationUtils.collapse(this, header.height)
        }
        else {
            AnimationUtils.expand(this)
        }

        open = !open
        setHeaderArrowExpanded(open)
    }

    private fun setHeaderArrowExpanded(expanded: Boolean) {
        val stateSet = if (expanded) intArrayOf(android.R.attr.state_expanded) else intArrayOf()
        headerArrow.setImageState(stateSet, true)
    }

    private fun setFooterArrowExpanded(expanded: Boolean) {
        val stateSet = if (expanded) intArrayOf(android.R.attr.state_expanded) else intArrayOf()
        footerArrow.setImageState(stateSet, true)
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
}
