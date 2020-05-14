package com.funworld.heogiutien.features.add

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.toast
import com.funworld.heogiutien.model.Resource
import kotlinx.android.synthetic.main.add_resource_dialog_fragment.*

class AddResourceDialogFragment(val callback: Callback) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.add_resource_dialog_fragment, container, false)
        val toolbar: Toolbar = rootView.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

//        val actionBar = (activity as AppCompatActivity).supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)

        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        viewModel = ViewModelProviders.of(this).get(AddExpenseViewModel::class.java)
        // TODO: Use the ViewModel
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_expense_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                toast("Save Resource")
                if (!isInvalidateInput()) {
                    callback.onAddedResource(getAddedResource())
                    dismiss()
                    true
                } else {
                    toast("Invalid input")
                }
            }
            android.R.id.home -> {
                dismiss()
                true
            }
            else -> toast("other items " + item.title)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isInvalidateInput() =
        edt_resource_name.text!!.isEmpty()
                || edt_description.text!!.isEmpty()
                || edt_money_amount.text!!.isEmpty()

    private fun getAddedResource() = Resource(
        edt_resource_name.text.toString(),
        edt_description.text.toString(),
        edt_money_amount.text.toString().toInt()
    )

    interface Callback {
        fun onAddedResource(resource: Resource)
    }
}
