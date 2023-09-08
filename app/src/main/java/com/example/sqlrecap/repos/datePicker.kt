package com.example.sqlrecap.repos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.view.View
import android.widget.EditText

class ReposDateTimePicker {
    companion object {

        private lateinit var datePickerDialog: DatePickerDialog
        public fun setupDatePicker(pDate : EditText, pContext : Context){
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR] // current year
            val mMonth = c[Calendar.MONTH] // current month
            val mDay = c[Calendar.DAY_OF_MONTH] // current day

            var date = pDate

            date.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    datePickerDialog = DatePickerDialog(pContext,
                        { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                            date.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year )
                        }, mYear, mMonth, mDay
                    )
                    datePickerDialog.show()
                }
            }

            date.setOnClickListener(View.OnClickListener { // calender class's instance and get current date , month and year from calender
                datePickerDialog = DatePickerDialog(pContext,
                    { view, year, monthOfYear, dayOfMonth -> // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year )
                    }, mYear, mMonth, mDay
                )
                datePickerDialog.show()
            })
        }
    }
}