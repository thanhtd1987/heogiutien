package com.funworld.heogiutien.ui.pattern

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.funworld.heogiutien.R

class PatternFragment : Fragment() {

    private lateinit var patternViewModel: PatternViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        patternViewModel =
            ViewModelProvider(this).get(PatternViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pattern, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        patternViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }


}
