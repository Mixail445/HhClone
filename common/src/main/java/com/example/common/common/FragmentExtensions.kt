package com.example.common.common

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common.R
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

const val DATE_PICKER_TAG = "date_picker_tag"

inline fun Fragment.showDatePickers(
    date: Long?,
    crossinline onDateSelectClick: (date: Long) -> Unit,
) {
    val picker =
        MaterialDatePicker.Builder
            .datePicker()
            .setTheme(R.style.BlackScreenWhiteText)
            .setTitleText("SelectDataPicker")
            .setSelection(date)
            .build()
    picker.addOnPositiveButtonClickListener {
        onDateSelectClick(it)
    }
    picker.show(childFragmentManager, DATE_PICKER_TAG)
}

fun EditText.onTextChanged(action: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            action(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun Fragment.showDialogError(
    title: String,
    message: String,
) {
    context?.let {
        AlertDialog
            .Builder(it)
            .setTitle(title)
            .setMessage(message)
            .show()
    }
}