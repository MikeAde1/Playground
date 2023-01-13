package com.example.workmanagerapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.EditText
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import com.example.workmanagerapp.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.*
import kotlin.math.min

class CurrencyInputWatcher(
    private val editText: EditText,
    private val currencySymbol: SpannableStringBuilder,
    locale: Locale
) : TextWatcher {

    companion object {
        const val MAX_NO_OF_DECIMAL_PLACES = 2
        const val FRACTION_FORMAT_PATTERN_PREFIX = "#,##0."
    }

    private var hasDecimalPoint = false
    private val wholeNumberDecimalFormat =
        (NumberFormat.getNumberInstance(locale) as DecimalFormat).apply {
            applyPattern("#,##0")
        }

    private val fractionDecimalFormat = (NumberFormat.getNumberInstance(locale) as DecimalFormat)

    val decimalFormatSymbols: DecimalFormatSymbols
        get() = wholeNumberDecimalFormat.decimalFormatSymbols

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        fractionDecimalFormat.isDecimalSeparatorAlwaysShown = true
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasDecimalPoint = s.toString().contains(decimalFormatSymbols.decimalSeparator.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun afterTextChanged(s: Editable) {
        val newInputString = s.toString()

        if (newInputString.length < currencySymbol.length) {
            editText.setText(currencySymbol)
            editText.setSelection(currencySymbol.length)
            return
        }

        if (newInputString == currencySymbol.toString()) {
            editText.setSelection(currencySymbol.length)
            return
        }

        editText.removeTextChangedListener(this)
        if (newInputString.length > 1) {
            val ssb = SpannableStringBuilder(newInputString)
            ssb.setSpan(ForegroundColorSpan(editText.context.resources.getColor(R.color.teal_200)), 0,1,Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

            val colorSpan = ForegroundColorSpan(editText.context.resources.getColor(R.color.purple_200))
            ssb.setSpan(colorSpan, 1, newInputString.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            //StringBuilder(currencySymbol.toString())
            editText.setText(ssb)
            editText.setSelection(newInputString.length)
        }
        editText.addTextChangedListener(this)
    }

    /**
     * @param number the original number to format
     * @return the appropriate zero sequence for the input number. e.g 156.1 returns "0",
     *  14.98 returns "00"
     */
    private fun getFormatSequenceAfterDecimalSeparator(number: String): String {
        val noOfCharactersAfterDecimalPoint = number.length - number.indexOf(decimalFormatSymbols.decimalSeparator) - 1
        return "0".repeat(min(noOfCharactersAfterDecimalPoint, MAX_NO_OF_DECIMAL_PLACES))
    }
}
internal fun parseMoneyValue(value: String, groupingSeparator: String, currencySymbol: String): String =
    value.replace(groupingSeparator, "").replace(currencySymbol, "")

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal fun getLocaleFromTag(localeTag: String): Locale {
    return try {
        Locale.Builder().setLanguageTag(localeTag).build()
    } catch (e: Exception) {
        Locale.getDefault()
    }
}

internal fun isLollipopAndAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

inline fun Context.withStyledAttributes(
    set: AttributeSet? = null,
    attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    block: TypedArray.() -> Unit
) {
    val typedArray = obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
    try {
        typedArray.block()
    } finally {
        typedArray.recycle()
    }
}
