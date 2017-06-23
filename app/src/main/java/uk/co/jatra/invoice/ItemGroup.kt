package uk.co.jatra.invoice

/**
 * Created by tim on 23/06/2017.
 */
data class ItemGroup(val headerText : String, val lineItems : List<LineItem>)