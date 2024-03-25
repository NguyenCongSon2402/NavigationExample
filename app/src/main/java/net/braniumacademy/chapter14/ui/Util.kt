package net.braniumacademy.chapter14.ui

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun dateToString(date: Date?): String {
    val format = "dd/MM/yyyy"
    val dateFormat = SimpleDateFormat(format)
    date?.let { return dateFormat.format(it) }
    return "01/01/2000"
}

fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}