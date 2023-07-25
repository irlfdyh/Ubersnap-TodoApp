package com.ubersnap.challange.todo.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DEFAULT_FORMAT = "EEEE, dd MMMM yyyy"

fun Long.asDateFormat(): String =
    Date().run {
        this.time = this@asDateFormat
        SimpleDateFormat(
            DEFAULT_FORMAT, Locale.getDefault()
        ).format(this)
    }