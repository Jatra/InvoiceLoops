package uk.co.jatra.invoice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by tim on 23/06/2017.
 */

class InvoiceView : LinearLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.VERTICAL
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun setInvoice(invoice: Invoice) {
        for (group in invoice.groups) {
            addItemGroup(group)
        }
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
