package com.funworld.heogiutien.features.expense.create

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import com.funworld.heogiutien.R
import com.funworld.heogiutien.base.BaseActivity
import com.funworld.heogiutien.common.utils.Utils
import com.funworld.heogiutien.data.model.Resource
import com.funworld.heogiutien.data.helper.ExpenseHelper
import kotlinx.android.synthetic.main.activity_expense_create.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class CreateExpenseActivity : BaseActivity(), View.OnClickListener{

    private val accounts by lazy { Resource.getAll() }

    private lateinit var mSelectedResource: Resource
    private lateinit var mDestinationResource: Resource
    private var mCreatedTime = DateTime.now().millis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_create)

        val param = intent.getStringExtra(INTENT_PARAM) ?: "" //throw Exception()

        initView()
        initViewAction()
    }

    override fun initView(){
        super.initView()

        onDateTimeChanged()

        onSelectedAccountChanged(accounts[0])
        onDestinationAccountChanged(accounts[0])

    }

    override fun initViewAction(){
        super.initViewAction()

        iv_close.setOnClickListener(this)
        rl_create_expense.setOnClickListener(this)
        tv_expense_time.setOnClickListener(this)
        tv_expense_account.setOnClickListener(this)
        tv_expense_to_account.setOnClickListener(this)
        tv_reset_info.setOnClickListener(this)
        tv_expense_save.setOnClickListener(this)

        cb_expense_transfer.setOnCheckedChangeListener{_, isChecked ->
            ll_receive_account.visibility = View.VISIBLE
            tv_expense_to_account.isEnabled = isChecked
        }

        cb_expense_related.setOnCheckedChangeListener{_, isChecked ->
            rl_expense_related.visibility = View.VISIBLE
            rd_borrow.isEnabled = isChecked
            rd_lend.isEnabled = isChecked
            et_expense_related_name.isEnabled = isChecked
            et_expense_related_amount.isEnabled = isChecked
        }

        et_expense_amount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(view: Editable) {
                //TODO display number with comma
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_close -> {
                if( !et_expense_purpose.text.isEmpty() || !et_expense_amount.text.isEmpty())
                    showWarning(getString(R.string.title_alert),
                            getString(R.string.discard_warning),
                            okListener = { _ -> onBackPressed() })
                else
                    onBackPressed()
            }

            R.id.rl_create_expense -> { Utils.hideKeyboard(this, et_expense_purpose) }

            R.id.tv_expense_time -> {
                Utils.showDateTimePicker( this, mCreatedTime, listener = { time ->
                    mCreatedTime = time
                    onDateTimeChanged()
                })
            }

            R.id.tv_expense_account -> {
                selectResource(mSelectedResource, listener = { resource -> onSelectedAccountChanged(resource) })
            }

            R.id.tv_expense_to_account -> {
                selectResource(mDestinationResource, listener = { resource -> onDestinationAccountChanged(resource) })
            }

            R.id.tv_reset_info -> {
                showWarning(getString(R.string.title_alert),
                        "Are you sure to reset all info?",
                        okListener = { _ -> resetAllView() })
            }

            R.id.tv_expense_save -> {
                if (verifyExpenseInfo()) {
                    if (cb_expense_transfer.isChecked) {
                        val result = ExpenseHelper.transferMoney(
                                getString(R.string.transfer_tag) + et_expense_purpose.text.toString(),
                                et_expense_amount.text.toString().toInt(),
                                mSelectedResource,
                                mDestinationResource,
                                et_expense_note.text.toString(),
                                mCreatedTime
                        )
                        Snackbar.make(view, result, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                    } else {
                        var expenseId = ""
                        if (cb_expense_related.isChecked) {
                            expenseId = ExpenseHelper.addExpense(
                                    et_expense_purpose.text.toString(),
                                    et_expense_amount.text.toString().toInt(),
                                    mSelectedResource,
                                    cb_expense_deposit.isChecked,
                                    et_expense_note.text.toString(),
                                    mCreatedTime,
                                    rd_lend.isChecked,
                                    et_expense_related_name.text.toString(),
                                    et_expense_related_amount.text.toString().toInt()
                            )
                        } else {
                            expenseId = ExpenseHelper.addExpense(
                                    et_expense_purpose.text.toString(),
                                    et_expense_amount.text.toString().toInt(),
                                    mSelectedResource,
                                    cb_expense_deposit.isChecked,
                                    et_expense_note.text.toString(),
                                    mCreatedTime)
                        }
                        Snackbar.make(view, "Expense $expenseId is saved!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                    }
                    finish()
                } else {
                    showWarning(getString(R.string.title_alert),
                            "There is something wrong, please recheck!!!",
                            okListener = { _ -> Unit })
                }
            }

        }
    }

    private fun onSelectedAccountChanged(resource: Resource){
        mSelectedResource = resource
        tv_expense_account.text = mSelectedResource.shortName
    }

    private fun onDestinationAccountChanged(resource: Resource){
        mDestinationResource = resource
        tv_expense_to_account.text = mDestinationResource.shortName
    }

    private fun onDateTimeChanged(){
        val formatter = DateTimeFormat.forPattern("EEE, dd-MM-yyyy HH:mm")
        tv_expense_time.text = formatter.print(mCreatedTime)
    }

    private fun resetAllView(){
        et_expense_purpose.setText("")
        et_expense_amount.setText("")
        et_expense_note.setText("")
        et_expense_related_name.setText("")
        et_expense_related_amount.setText("")
        onSelectedAccountChanged(accounts[0])
        onDestinationAccountChanged(accounts[0])
        cb_expense_deposit.isChecked = false
        cb_expense_transfer.isChecked = false
        cb_expense_related.isChecked = false
    }

    private fun verifyExpenseInfo(): Boolean{
        if(et_expense_amount.text.isEmpty()){
            return false
        }

        if (cb_expense_related.isChecked && (et_expense_related_amount.text.isEmpty() ||
                        et_expense_related_amount.text.toString().toInt() > et_expense_amount.text.toString().toInt())){
            et_expense_related_amount.text = et_expense_amount.text
        }
        return true
    }

    private fun selectResource(currentResource: Resource, listener: (Resource) -> Unit) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_list_of_resource)
        val rcvResources = dialog.findViewById<RecyclerView>(R.id.rcv_dialog_list_resource)

        val adapter = ResourceListAdapter(accounts, listener = { resource ->
            listener(resource)
            dialog.dismiss()
        })
        adapter.mSelectedResource = currentResource

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcvResources.layoutManager = layoutManager
        rcvResources.adapter = adapter
        val decoration = DividerItemDecoration(this, layoutManager.orientation)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decoration.setDrawable(baseContext.getDrawable(R.drawable.rcv_line_devider))
        }
        rcvResources.addItemDecoration(decoration)

        dialog.show()
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