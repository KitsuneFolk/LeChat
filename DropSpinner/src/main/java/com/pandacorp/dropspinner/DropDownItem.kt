package com.pandacorp.dropspinner

data class DropDownItem(var id: Int, var text: String, var checked: Boolean = false) {
    fun toggleState() {
        checked = !checked
    }
}