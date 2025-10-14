package com.roadrunner.roadmate.utilities

fun parseNewsContent(raw: String): Pair<String, Boolean> {
    if (raw.isEmpty()) return "" to false
    val marker = "… [+"
    val i = raw.indexOf(marker)
    return if (i > 0) raw.substring(0, i + 1) to true else raw to false
}