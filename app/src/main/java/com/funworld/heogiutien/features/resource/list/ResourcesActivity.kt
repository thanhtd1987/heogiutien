package com.funworld.heogiutien.features.resource.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.dao.Resource
import com.funworld.heogiutien.features.resource.create.CreateResourceActivity
import kotlinx.android.synthetic.main.activity_resources.*

class ResourcesActivity : AppCompatActivity() {

    private val mResources by lazy { Resource.getAll() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        initView()
        initViewAction()
    }

    private fun initView() {
        val adapter = ResourceListAdapter(mResources, listener = { resource ->
            //TODO open detail of resource
            CreateResourceActivity.startActivity(this, resource)
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayout.VERTICAL
        rcv_resources.layoutManager = layoutManager
        rcv_resources.adapter = adapter
    }

    private fun initViewAction() {
        iv_add_resouce.setOnClickListener {
            //TODO start activity for result Add resource
            CreateResourceActivity.startActivityForResult(this)
        }

        iv_back.setOnClickListener { onBackPressed() }
    }

    private fun addResource(resource: Resource) {
        mResources.add(resource)
        rcv_resources.adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //TODO add resource
        if (requestCode == CreateResourceActivity.ACTIVITY_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val resource = data.getParcelableExtra<Resource>(CreateResourceActivity.INTENT_PARAM_RETURN_RESOURCE)
                addResource(resource)
            }
        }
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, ResourcesActivity::class.java)
            context.startActivity(intent)
        }
    }
}
