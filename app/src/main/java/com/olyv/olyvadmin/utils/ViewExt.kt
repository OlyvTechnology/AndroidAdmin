package  com.olyv.olyvadmin.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.olyv.olyvadmin.R


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.inivisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    alpha = 1.0f
    isEnabled = true
}


fun View.disable() {
    alpha = 0.5f
    isEnabled = false
}

fun Context.pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun Group.addOnClickListener(listener: (view: View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun String.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidMobile() = !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()
fun String.isValidPassword() = this.length > 5

fun TextInputLayout.isRequired(isRequired:Boolean){
    if (isRequired){
        val colored = "*"
        val builder = SpannableStringBuilder()
        builder.append(hint) //get string

        builder.append(" ") // add space between text and start

        val start: Int = builder.length
        builder.append(colored)
        val end: Int = builder.length
        builder.setSpan(
            ForegroundColorSpan(Color.RED), start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        hint = builder
    }
}

fun ArrayList<Any>.search(){

}

fun ImageView.loadImage(path:String?){
    if (path==null){
        Glide
            .with(context)
            .load(R.drawable.ic_launcher_background)
            .into(this)
        return
    }
    Glide
        .with(context)
        .load(path)
        .error(R.drawable.ic_launcher_background)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}