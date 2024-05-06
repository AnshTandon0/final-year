package com.tryouts.finalyearproject.ui.main

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tryouts.finalyearproject.R
import com.tryouts.finalyearproject.common.Constants
import com.tryouts.finalyearproject.common.SharedPreferencesClass
import com.tryouts.finalyearproject.data.models.AttackData
import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.data.models.OutputData
import com.tryouts.finalyearproject.databinding.FragmentSensor1Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class Sensor1Fragment : Fragment() {

    private lateinit var binding: FragmentSensor1Binding
    private lateinit var sharedPreferencesClass: SharedPreferencesClass
    private lateinit var viewModel: Sensor1ViewModel
    private var inputData = arrayListOf<InputData>()
    var point = 0
    var makeCall = false
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
        getPrediction()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSensor1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeCall = true
        initSharedPreferences()
        readJsonFromFile()
        initViewModel()
    }

    private fun initSharedPreferences() {
        sharedPreferencesClass = SharedPreferencesClass(activity!!)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(Sensor1ViewModel::class.java)

        getPrediction()

        viewModel.getPredictionResponse.observe(this){
            it?.let {
                saveAttackData(it)
            }
        }

        viewModel.loadingStatus.observe(this) {
            binding.progressBar.visibility = it
        }
    }

    private fun saveAttackData ( it : OutputData) {
        if ( it.prediction == "attack" )
        {
            val sdf = SimpleDateFormat("dd/MM/yyyy  HH : mm : ss", Locale.getDefault())
            val currentDateAndTime: String = sdf.format(Date())
            val attackData = AttackData( currentDateAndTime , "Sensor 1" )
            var attackList = arrayListOf<AttackData>()
            if ( sharedPreferencesClass.checkFileData() )
                attackList = sharedPreferencesClass.getFileData()
            attackList.add(attackData)
            sharedPreferencesClass.saveFileData(attackList)
            binding.continueBtn.setText("Attack")
            binding.continueBtn.setBackgroundColor(resources.getColor(R.color.red))
        }
        else
        {
            binding.continueBtn.setText("Normal")
            binding.continueBtn.setBackgroundColor(resources.getColor(R.color.green))
        }
        object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long){}
            override fun onFinish() {
                if ( makeCall )
                    getPrediction()
            }
        }.start()
    }

    private fun readJsonFromFile(){
        val jsonString = resources.openRawResource(Constants.FILENAME).bufferedReader().use { it.readText() }
        val gson = Gson()
        inputData = gson.fromJson(jsonString, object : TypeToken<List<InputData>>() {}.type)
    }

    private fun getPrediction() {
        point ++
        initViews()
        object : CountDownTimer(5000, 1000){
            override fun onTick(p0: Long){}
            override fun onFinish() {
                    lifecycleScope.launch (Dispatchers.IO + coroutineExceptionHandler) {
                        delay(1000)
                        viewModel.getPrediction(inputData[point])
                    }
            }
        }.start()
    }

    private fun initViews () {
        binding.continueBtn.setBackgroundColor(resources.getColor(R.color.purple_500))
        binding.continueBtn.setText("Determining ...")
        binding.temperature.text = inputData[point].K.toString()
        binding.avgTempertaure.text = inputData[point].D.toString()
        binding.latency.text = inputData[point].B.toString()
        binding.humidity.text = inputData[point].C.toString()
    }

    override fun onPause() {
        super.onPause()
        makeCall = false
    }

    override fun onResume() {
        super.onResume()
        makeCall = true
        getPrediction()
    }
}