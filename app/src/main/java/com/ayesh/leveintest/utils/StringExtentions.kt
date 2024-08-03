package com.ayesh.leveintest.utils

fun String?.orEmptyIfNull(): String {
    return this ?: ""
}

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}
