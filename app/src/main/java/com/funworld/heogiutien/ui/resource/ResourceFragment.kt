package com.funworld.heogiutien.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.funworld.heogiutien.R

class ResourceFragment : Fragment() {

    private lateinit var resourceViewModel: ResourceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resourceViewModel = ViewModelProvider(this).get(ResourceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_resource, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        resourceViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
