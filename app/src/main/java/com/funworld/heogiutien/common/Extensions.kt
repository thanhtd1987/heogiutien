package com.funworld.heogiutien.common

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Inflate the layout
 * @param layoutRes layout resource id
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

/**
 * Toast function for Activity
 */
fun AppCompatActivity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}

/**
 * hide keyboard
 */
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * return result from fragment
 */
//fun Fragment.getNavigationResult(key: String = "result") =
//   findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

//fun Fragment.setNavigationResult(result: String, key: String = "result") {
//    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)

/**
 * https://medium.com/@AjahnCharles/wrap-callbacks-as-coroutines-kotlin-android-bf4aa79024c5
 * Callbacks as Coroutines
 */
fun View.setOnClickCoroutine(owner: LifecycleOwner, listener: suspend (view: View) -> Unit) =
    this.setOnClickListener { owner.lifecycleScope.launch { listener(it) } }