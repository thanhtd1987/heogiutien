package com.funworld.heogiutien.features.expense.create

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.dao.Resource
import com.funworld.heogiutien.data.helper.ExpenseHelper
import kotlinx.android.synthetic.main.activity_expense_create.*
import kotlinx.android.synthetic.main.dialog_list_of_resource.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class CreateExpenseActivity : AppCompatActivity(), View.OnClickListener{

    private val accounts by lazy { Resource.getAll() }

    private lateinit var mSelectedResource: Resource
    private lateinit var mDestinationResource: Resource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_create)

        val param = intent.getStringExtra(INTENT_PARAM) ?: "" //throw Exception()

        initView()
        initViewAction()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_close -> {
                showWarning(getString(R.string.title_alert),
                        getString(R.string.discard_warning),
                        okListener = { _ -> onBackPressed() })
            }

            R.id.tv_expense_time -> {
                //TODO: do action change date time
            }

            R.id.tv_expense_account -> {
                //TODO: open dialog list of account for selecting
                selectResource(mSelectedResource, listener = { resource -> onSelectedAccountChanged(resource) })
            }

            R.id.tv_expense_to_account -> {
                //TODO: open dialog list of account for selecting
                selectResource(mDestinationResource, listener = { resource -> onDestinationAccountChanged(resource) })
            }

            R.id.tv_reset_info -> {
                //TODO: reset all inputted info to begin
                showWarning(getString(R.string.title_alert),
                        "Are you sure to reset all info?",
                        okListener = { _ -> resetAllView() })
            }

            R.id.tv_expense_save -> {
                //TODO: save Expense
                if (verifyExpenseInfo()) {
//                    ExpenseHelper.addExpense(et_expense_purpose.text.toString(),
//                            et_expense_amount.text.toString().toInt(),
//                            mSelectedResource,
//                            )
                } else {
                    showWarning(getString(R.string.title_alert),
                            "There is something wrong, please recheck!!!",
                            okListener = { _ -> Unit })
                }
            }

        }
    }

    private fun initView(){
        val formatter = DateTimeFormat.forPattern("EEE, yyyy-MM-dd HH:mm:ss")
        val dt = DateTime.now()
        tv_expense_time.text = formatter.print(dt)

        onSelectedAccountChanged(accounts[0])
        onDestinationAccountChanged(accounts[0])

    }

    private fun initViewAction(){
        iv_close.setOnClickListener(this)
        tv_expense_time.setOnClickListener(this)
        tv_expense_account.setOnClickListener(this)
        tv_expense_to_account.setOnClickListener(this)
        tv_reset_info.setOnClickListener(this)
        tv_expense_save.setOnClickListener(this)

        cb_expense_transfer.setOnCheckedChangeListener{_, isChecked ->
            tv_expense_to_account.isEnabled = isChecked
        }

        cb_expense_related.setOnCheckedChangeListener{_, isChecked ->
            toggle_related.isEnabled = isChecked
            et_expense_related_name.isEnabled = isChecked
        }
    }

    private fun onSelectedAccountChanged(resource: Resource){
        mSelectedResource = resource
        tv_expense_account.text = mSelectedResource.name
    }

    private fun onDestinationAccountChanged(resource: Resource){
        mDestinationResource = resource
        tv_expense_to_account.text = mDestinationResource.name
    }

    private fun resetAllView(){

    }

    private fun verifyExpenseInfo(): Boolean{
        return true
    }

    private fun selectResource(currentResource: Resource, listener: (Resource) -> Unit) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_list_of_resource)

        val adapter = ResourceListAdapter(accounts, listener = { resource ->
            listener(resource)
            dialog.dismiss()
        })
        adapter.mSelectedResource = currentResource

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_dialog_list_resource.layoutManager = layoutManager
        rcv_dialog_list_resource.adapter = adapter

        dialog.show()
    }

    fun showWarning(title: String, message: String, okListener: (dialogInterface: DialogInterface) -> Unit){
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
        private val INTENT_PARAM = "param"

        fun startActivity(context: Context){
            val intent = Intent(context, CreateExpenseActivity::class.java)
            intent.putExtra(INTENT_PARAM, "hello")
            context.startActivity(intent)
        }
    }
}