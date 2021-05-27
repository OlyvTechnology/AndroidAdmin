package com.olyv.olyvadmin.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


fun changeTextColor(text: String, color: String): SpannableStringBuilder {
    val builder = SpannableStringBuilder()
    builder.append(text)
    val start: Int = 0
    val end: Int = builder.length
    builder.setSpan(
        ForegroundColorSpan(Color.parseColor(color)), start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return builder
}

fun changeTextViewColor(
    view: TextView,
    fulltext: String,
    color: String,
    start: Int,
    end: Int
) {
    try {
        val mColor = "#$color"
        view.setText(fulltext, TextView.BufferType.SPANNABLE)
        val str = view.text as Spannable
        str.setSpan(
            ForegroundColorSpan(Color.parseColor(mColor)),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


val openLink = fun(context: Context, url: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    context.startActivity(i)
}



fun hideKeyboard(activity: Activity) {
    val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}