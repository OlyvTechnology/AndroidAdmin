package com.olyv.olyvadmin.utils

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TextView
import java.util.*

enum class DateEnum {
    NOTHING,
    FUTURE,
    PAST
}

//fun showDatePicker(
//    context: Context,
//    dateEnum: DateEnum,
//    mDate: DatePicker?,
//    mylmda: (String, String, String, DatePicker) -> Unit
//) {
//
//    val builder = AlertDialog.Builder(context)
//    val dialogView: View = LayoutInflater.from(context)
//        .inflate(R.layout.user_birth_date_picker_alert_dialog, null)
//
//    val datePicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
//    val calendar = Calendar.getInstance()
//
//    when (dateEnum) {
//        DateEnum.NOTHING -> {
//        }
//        DateEnum.FUTURE -> datePicker.minDate = calendar.timeInMillis
//        DateEnum.PAST -> datePicker.maxDate = calendar.timeInMillis
//    }
//
//
//
//    if (mDate != null)
//        datePicker.updateDate(mDate.year, mDate.month, mDate.dayOfMonth)
//
//    val txt_pick = dialogView.findViewById<TextView>(R.id.txt_pick)
//    builder.setView(dialogView)
//    val alertDialog = builder.create()
//
//    txt_pick.setOnClickListener {
//        var monthOfYear = datePicker.month
//        monthOfYear += 1
//        mylmda(
//            monthOfYear.toString(),
//            datePicker.dayOfMonth.toString(),
//            datePicker.year.toString(),
//            datePicker
//        )
//        alertDialog.dismiss()
//    }
//
//    alertDialog.show()
//}
//
//fun showNumberPicker(
//    context: Context,
//    list: ArrayList<String>,
//    mylmda: (String) -> Unit
//) {
//    val builder = AlertDialog.Builder(context)
//    val view = LayoutInflater.from(context).inflate(R.layout.choose_gender_dialog, null, false)
//    builder.setView(view)
//    val alertDialog = builder.create()
//
//    val numberPicker: NumberPicker = view.findViewById(R.id.picker_category)
//    val tv_select: TextView = view.findViewById(R.id.tv_select)
//
//    var selected = if (list.isNotEmpty()) list.first() else ""
//
//    numberPicker.setOnValueChangedListener { picker, _, _ ->
//        selected = list[picker.value - 1]
//    }
//
//    numberPicker.minValue = 1
//    numberPicker.maxValue = list.size
//    numberPicker.displayedValues = list.toTypedArray()
//    tv_select.setOnClickListener {
//        mylmda(selected)
//        alertDialog.dismiss()
//    }
//    alertDialog.show()
//}
//
//fun showTimePicker(activity: Context, mylamda: (String,Int,Int) -> Unit) {
//
//    val mcurrentTime = Calendar.getInstance()
//    val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
//    val minute = mcurrentTime[Calendar.MINUTE]
//    val mTimePicker: TimePickerDialog
//    mTimePicker = TimePickerDialog(
//        activity,
//        OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
//            mylamda(getTimeFromHourMin(selectedHour.toString(), selectedMinute.toString()),selectedHour, selectedMinute)
//        },
//        hour,
//        minute,
//        false
//    )
//    mTimePicker.setTitle("Select Time")
//    mTimePicker.show()
//
//}