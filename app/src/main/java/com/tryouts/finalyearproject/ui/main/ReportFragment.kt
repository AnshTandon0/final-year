package com.tryouts.finalyearproject.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tryouts.finalyearproject.common.SharedPreferencesClass
import com.tryouts.finalyearproject.data.models.AttackData
import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private lateinit var sharedPreferencesClass: SharedPreferencesClass
    private var attackList = arrayListOf<AttackData>()
    private lateinit var adapter: AttackReportRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSharedPreferences()
        getData()
        adapter  =  AttackReportRecyclerAdapter(attackList , context!!)
        binding.recycler.adapter = adapter
    }

    private fun initSharedPreferences() {
        sharedPreferencesClass = SharedPreferencesClass(activity!!)
    }

    private fun getData() {
        Log.d("hhhhh" , attackList.size.toString())
        if ( sharedPreferencesClass.checkFileData() )
            attackList = sharedPreferencesClass.getFileData()
        Log.d("hhhhh" , attackList.size.toString())
    }
}