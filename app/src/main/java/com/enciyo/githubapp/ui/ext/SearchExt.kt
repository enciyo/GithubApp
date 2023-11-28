package com.enciyo.githubapp.ui.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


fun TextView.flowTextWatcher(): Flow<String> = callbackFlow {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
            Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            trySend(s.toString())
        }
    }
    this@flowTextWatcher.addTextChangedListener(textWatcher)
    awaitClose { this@flowTextWatcher.removeTextChangedListener(textWatcher) }
}
    .debounce(500)
    .distinctUntilChanged()
