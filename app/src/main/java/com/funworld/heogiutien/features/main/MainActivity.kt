package com.funworld.heogiutien.features.main

import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.funworld.heogiutien.R
import com.funworld.heogiutien.features.add.AddExpenseDialogFragment
import com.funworld.heogiutien.features.add.AddResourceDialogFragment
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.model.Resource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), AddResourceDialogFragment.Callback,
    AddExpenseDialogFragment.Callback {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {

        fab.setOnClickListener {
            if (mainViewModel.resources.value.isNullOrEmpty()) {
//                MaterialAlertDialogBuilder(baseContext)
//                    .setMessage(R.string.mess_dialog_confirm_add_resource)
//                    .setPositiveButton(R.string.ok) { _, _ -> showAddResourceDialog() }
//                    .setNegativeButton(R.string.cancel) { _, _ -> }
//                    .show()
                showAddResourceDialog()
            } else {
                showAddExpenseDialog()
            }
        }

        toolbar.setNavigationOnClickListener {

        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_search -> {
                    //click search
                    true
                }
                R.id.action_more -> {
                    //click more
                    true
                }
                else -> false
            }
        }

        val adapter = ExpenseAdapter(this)
        rcv_latest_expense.adapter = adapter
        rcv_latest_expense.layoutManager = LinearLayoutManager(this)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.resources.observe(this, Observer { resources ->
            if (!resources.isNullOrEmpty())
                resources.let { Log.d("DEBUG", "resource: " + it.last().name) }
        })
        mainViewModel.expenses.observe(this, Observer { expenses ->
            expenses?.let { adapter.setExpenses(it) }
        })
    }

    companion object {
        private val TAG = "MainActivity"
    }

    private fun showAddDialog(dialogFragment: DialogFragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction
            .add(R.id.frg_container, dialogFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showAddResourceDialog() {
        val addResource = AddResourceDialogFragment(this)
        showAddDialog(addResource)
    }

    private fun showAddExpenseDialog() {
        val addExpenseDialogFragment = AddExpenseDialogFragment(this)
        addExpenseDialogFragment.setResources(mainViewModel.resources.value!!)
        showAddDialog(addExpenseDialogFragment)
    }

    val addActionModeCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_search -> {
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.top_app_bar, menu)
            mode.title = "hello"
            mode.subtitle = "how are you?"

            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu): Boolean {
            val menuItemSetting = menu.findItem(R.id.action_settings)
            menuItemSetting.isVisible = false
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {

        }

    }

    override fun onAddedResource(resource: Resource) {
        Log.d("DEBUG", "Insert resource")
        mainViewModel.insert(resource)
    }

    override fun onAddedExpense(expense: Expense) {
        mainViewModel.insert(expense)
    }

}
