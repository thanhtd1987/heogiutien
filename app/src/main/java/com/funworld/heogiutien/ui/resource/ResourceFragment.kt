package com.funworld.heogiutien.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.base.adapter.ViewTypeDelegateAdapter
import com.funworld.heogiutien.utils.extention.toast
import kotlinx.android.synthetic.main.fragment_resource.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResourceFragment : Fragment(), ViewTypeDelegateAdapter.ViewSelectedListener {

    private val resourceViewModel by viewModel<ResourceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_resource, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        val adapter = ResourceAdapter(this)
        rcv_resources.layoutManager = LinearLayoutManager(context)
        rcv_resources.adapter = adapter
        resourceViewModel.resource.observe(viewLifecycleOwner, Observer {
            it.let { adapter.setResources(it) }
        })
    }

    override fun onItemSelected(expenseId: Int) {
        toast("Select resource id ${expenseId}")
    }
}
