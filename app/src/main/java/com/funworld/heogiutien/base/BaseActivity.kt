package com.funworld.heogiutien.base

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.funworld.heogiutien.R

/**
 * Created by ThanhTD on 2/20/2018.
 */

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        initView()
//        initViewAction()
    }

    open fun initView() {

    }

    open fun initViewAction() {

    }

    fun showWarning(title: String, message: String, okListener: (dialogInterface: DialogInterface) -> Unit) {
        showWarning(title, message, getString(R.string.ok), getString(R.string.cancel), okListener)
    }

    fun showWarning(title: String, message: String, strOk: String, strCancel: String, okListener: (dialogInterface: DialogInterface) -> Unit) {
        val alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(strOk, { dialogInterface, i ->
                    dialogInterface.dismiss()
                    okListener(dialogInterface)
                })
                .setNegativeButton(strCancel, { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .create()
        alertDialog.show()
    }

    fun showSnackbar(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration).show()
    }

    fun showDismissSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setAction("DISMISS", {
            snackbar.dismiss()
        })

        // styling for action text
//        snackbar.setActionTextColor(Color.WHITE)
//
//        // styling for rest of text
//        val snackbarView = snackbar.view
//        val textView = snackbarView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
//        textView.setTextColor(Color.RED)
//        textView.setAllCaps(true)
//        textView.textSize = 20f
//
//        // styling for background of snackbar
//        snackbarView.setBackgroundColor(Color.BLUE)
    }
}