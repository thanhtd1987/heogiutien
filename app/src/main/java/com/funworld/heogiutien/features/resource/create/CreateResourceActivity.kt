package com.funworld.heogiutien.features.resource.create

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.utils.Utils
import com.funworld.heogiutien.data.dao.Resource
import com.funworld.heogiutien.data.helper.ResourceHelper
import kotlinx.android.synthetic.main.activity_create_resource.*


class CreateResourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_resource)

        val resource = intent.getParcelableExtra<Resource>(INTENT_PARAM_RESOURCE)

        if (resource != null) {
            et_resource_name.append(resource.name)
            et_resource_description.append(resource.description)
            et_resource_open_balance.append(resource.openingBalance.toString())
            et_resource_short_name.append(resource.shortName)
            et_resource_currency.append(resource.currency)
            et_resource_currency_unit.append(resource.currencyUnit)
        }

        initView()
        initViewAction()
    }

    private fun initView() {
        til_resource_name.hint = getString(R.string.create_resource_name)
        til_resource_description.hint = getString(R.string.create_resource_description)
        til_resource_open_balance.hint = getString(R.string.create_resource_open_balance)
        til_resource_short_name.hint = getString(R.string.create_resource_short_name)
        til_resource_currency.hint = getString(R.string.create_resource_currency)
        til_resource_currency_unit.hint = getString(R.string.create_resource_currency_unit)
    }

    private fun initViewAction() {

        tv_create_resource_add.setOnClickListener {
            if (verifyResourceInfo(it)) {
                val resource = ResourceHelper.addResource(et_resource_name.text.toString().trim(),
                        et_resource_description.text.toString(),
                        et_resource_short_name.text.toString(),
                        et_resource_open_balance.text.toString().toInt(),
                        et_resource_currency.text.toString(),
                        et_resource_currency_unit.text.toString())

                if (resource != null) {
                    val returnIntent = Intent()
                    returnIntent.putExtra(INTENT_PARAM_RETURN_RESOURCE, resource)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                } else {
                    showDismissSnackbar(it, "This resource is exist!!!", Snackbar.LENGTH_LONG)
                }
            }
        }

        iv_close.setOnClickListener {
            showWarning(getString(R.string.title_alert),
                    getString(R.string.discard_warning),
                    okListener = { _ ->
                        val returnIntent = Intent()
                        setResult(Activity.RESULT_CANCELED, returnIntent)
                        finish()
                    })
        }

        rl_create_resource.setOnClickListener { Utils.hideKeyboard(this, et_resource_name) }
    }

    fun verifyResourceInfo(view: View): Boolean {
        if (et_resource_name.text.toString().isEmpty()) {
            showSnackbar(view, "Name must not be empty!", Snackbar.LENGTH_LONG)
            return false
        }

        if (et_resource_open_balance.text.toString().isEmpty()) {
            et_resource_open_balance.append("0")
        }

        return true
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

    companion object {

        const val INTENT_PARAM_RESOURCE = "edit_resource"
        const val INTENT_PARAM_RETURN_RESOURCE = "return_resource"
        const val ACTIVITY_RESULT_CODE = 101

        fun startActivity(context: Context, resource: Resource? = null) {
            val intent = Intent(context, CreateResourceActivity::class.java)
            intent.putExtra(INTENT_PARAM_RESOURCE, resource)
            context.startActivity(intent)
        }

        fun startActivityForResult(activity: Activity) {
            val intent = Intent(activity, CreateResourceActivity::class.java)
            activity.startActivityForResult(intent, ACTIVITY_RESULT_CODE)
        }
    }
}
