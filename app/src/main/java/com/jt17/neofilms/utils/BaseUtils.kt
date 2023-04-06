package com.jt17.neofilms.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.jt17.neofilms.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object BaseUtils {

    fun getStringResource(context: Context, resourceID: Int) = context.getString(resourceID)

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateFormat(date: String): String {
        val inputDataFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
        val inputDate = LocalDate.parse(date, inputDataFormat)
        val outputDateFormat = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.US)
        return outputDateFormat.format(inputDate)
    }

}