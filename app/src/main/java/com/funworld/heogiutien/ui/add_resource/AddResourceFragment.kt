package com.funworld.heogiutien.ui.add_resource

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.funworld.heogiutien.R
import com.funworld.heogiutien.model.entity.Resource
import com.funworld.heogiutien.ui.home.HomeViewModel
import com.funworld.heogiutien.utils.extention.hideKeyboard
import com.funworld.heogiutien.utils.extention.toast
import kotlinx.android.synthetic.main.add_resource_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddResourceFragment : Fragment(R.layout.add_resource_fragment) {

    private val homeViewModel by sharedViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.add_resource_fragment, container, false)

        rootView.findViewById<ConstraintLayout>(R.id.adding_layout)
            .setOnClickListener { hideKeyboard() }

        setHasOptionsMenu(true)
        return rootView
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
                    onSaved()
                    findNavController().popBackStack()
                    hideKeyboard()
                    true
                } else {
                    toast("Invalid input")
                }
            }
            android.R.id.home -> {
                hideKeyboard()
                true
            }
            else -> toast("other items " + item.title)
        }
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }

    private fun isInvalidateInput() =
        edt_resource_name.text!!.isEmpty()
                || edt_description.text!!.isEmpty()
                || edt_money_amount.text!!.isEmpty()

    private fun getAddedResource() =
        Resource(
            edt_resource_name.text.toString(),
            edt_description.text.toString(),
            edt_money_amount.text.toString().toInt()
        )

    private fun onSaved() {
        homeViewModel.addedResource.postValue(getAddedResource())
    }
}
