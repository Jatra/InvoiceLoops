package uk.co.jatra.invoice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val invoiceView = findViewById(R.id.invoice_view) as InvoiceView
        invoiceView.setInvoice(invoice())
    }

    fun invoice(): Invoice {

        return Invoice("Invoice",
                (1..4)
                .map { g ->
                    val lines = (1..3).map { LineItem("item$g$it", it * g) }.toCollection(mutableListOf())
                    ItemGroup("header$g", lines)
                })

    }
}
