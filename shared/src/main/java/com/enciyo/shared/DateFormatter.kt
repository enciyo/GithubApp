package com.enciyo.shared

import java.text.SimpleDateFormat
import java.util.Locale


const val uiDateFormatter = "dd MMMM yy"
const val remoteDateFormatter = "yyyy-MM-dd'T'HH:mm:ss'Z'"



fun String.toUiDate(): String {
    val remoteDate = SimpleDateFormat(remoteDateFormatter, Locale.getDefault()).parse(this)
    return SimpleDateFormat(uiDateFormatter).format(remoteDate)
}