package com.funworld.heogiutien.features.expense.create

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.widget.TextView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.dao.Resource
import kotlinx.android.synthetic.main.activity_expense_create.*
import kotlinx.android.synthetic.main.dialog_list_of_resource.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class CreateExpenseActivity : AppCompatActivity(), View.OnClickListener{

    private val accounts by lazy { Resource.getAll() }

    private lateinit var mSelectedResource: Resource
    private lateinit var mDestinationResource: Resource
    private val dialog = Dialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_create)

        val param = intent.getStringExtra(INTENT_PARAM) ?: "" //throw Exception()

        initView()
        initViewAction()
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.iv_close -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle(getString(R.string.title_alert))
                        .setMessage(getString(R.string.discard_warning))
                        .setPositiveButton(getString(R.string.ok), { dialogInterface, i ->
                            dialogInterface.dismiss()
                            onBackPressed()
                        })
                        .setNegativeButton(getString(R.string.cancel), { dialogInterface, i -> dialogInterface.dismiss() })
                        .show()
            }

            R.id.tv_expense_time -> {
                //TODO: do action change date time
            }

            R.id.tv_expense_account -> {
                //TODO: open dialog list of account for selecting
            }

            R.id.tv_expense_to_account -> {
                //TODO: open dialog list of account for selecting
            }

            R.id.tv_reset_info -> {
                //TODO: reset all inputted info to begin
            }

            R.id.tv_expense_save -> {
                //TODO: save Expense
            }

        }
    }

    private fun initView(){
        val formatter = DateTimeFormat.forPattern("EEE, yyyy-MM-dd HH:mm:ss")
        val dt = DateTime.now()
        tv_expense_time.text = formatter.print(dt)


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

    private fun initResourceListDialog(textView: TextView, content: String){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_list_of_resource)

        val adapter = ResourceListAdapter(accounts, listener = { resource ->
            when(textView.id){
                R.id.tv_expense_account -> {
                    mSelectedResource = resource
                }

                R.id.tv_expense_to_account -> {
                    mDestinationResource = resource
                }
            }
            textView.text = resource.name
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_dialog_list_resource.layoutManager = layoutManager
        rcv_dialog_list_resource.adapter = adapter
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