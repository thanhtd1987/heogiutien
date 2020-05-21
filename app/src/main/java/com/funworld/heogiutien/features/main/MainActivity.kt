package com.funworld.heogiutien.features.main

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.toast
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.model.Resource
import com.funworld.heogiutien.ui.home.HomeViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setupAppbarConfig()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    private fun setupAppbarConfig() {
        val drawer: DrawerLayout = findViewById(R.id.main_drawer_layout)
        val navView: NavigationView = findViewById(R.id.main_navigation)
        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_expenses, R.id.nav_resources, R.id.nav_expense_pattern), drawer
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->  //3
            if (destination.id in arrayOf(
                    R.id.addExpenseFragment,
                    R.id.addResourceFragment
                )
            ) {
                fab.hide()
            } else {
                fab.show()
            }

//            if (destination.id == R.id.presentationFragment) {
//                toolbar.visibility = View.GONE
//            } else {
//                toolbar.visibility = View.VISIBLE
//            }
        }
    }

    lateinit var homeViewModel: HomeViewModel
    private fun initView() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            if (mainViewModel.resources.value.isNullOrEmpty()) {
                showAddResourceFragment()
            } else {
                showAddExpenseFragment()
            }
        }

//        toolbar.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.action_search -> {
//                    //click search
//                    toast("search")
//                    true
//                }
//                R.id.action_more -> {
//                    //click more
//                    toast("more")
//                    true
//                }
//                else -> false
//            }
//        }

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.resources.observe(this, Observer { resources ->
            if (!resources.isNullOrEmpty())
                resources?.let {
                    homeViewModel.resources.postValue(it)
                    with(it.first { it.id == mTopResourceId }) {
                        tv_top_balance_name.text = this.name
                        tv_top_current_balance.text = moneyInVnd(this.currentBalance)
                    }
                }
        })
        mainViewModel.expenses.observe(this, Observer { expenses ->
            expenses?.let { homeViewModel.expenses.postValue(it) }
        })

        homeViewModel.addedExpense.observe(this, Observer { expense ->
            expense?.let { onAddedExpense(it) }
        })
        homeViewModel.addedResource.observe(this, Observer { resource ->
            resource?.let { onAddedResource(it) }
        })
    }

    private fun showAddResourceFragment() {
        findNavController(R.id.nav_host_fragment).navigate(R.id.addResourceFragment)
    }

    private fun showAddExpenseFragment() {
        val bundle = bundleOf(
            "resource_ids" to mainViewModel.resources.value!!.map { it.id }.toIntArray(),
            "resource_names" to mainViewModel.resources.value!!.map { it.name }.toTypedArray()
        )
        findNavController(R.id.nav_host_fragment).navigate(R.id.addExpenseFragment, bundle)
    }

    private fun onAddedResource(resource: Resource) {
        toast("Added ${resource.name} Resource")
        mainViewModel.insert(resource)
    }

    private fun onAddedExpense(expense: Expense) {
        mainViewModel.insert(expense)
    }

    interface NavigationResult {
        fun onNavigationResult(result: Bundle)
    }

    fun navigateBackWithResult(result: Bundle) {
        val childFragmentManager =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as NavigationResult).onNavigationResult(result)
            childFragmentManager.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        navController.popBackStack()
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
}
