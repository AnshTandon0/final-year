package com.tryouts.finalyearproject.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.data.models.OutputData
import com.tryouts.finalyearproject.ui.usecases.GetPredictionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class Sensor2ViewModel @Inject constructor(
    private val getPredictionUseCase: GetPredictionUseCase
) : ViewModel() {

    private var _getPredictionResponse = MutableLiveData<OutputData>()
    var loadingStatus = MutableLiveData<Int>()

    val getPredictionResponse : MutableLiveData<OutputData> by lazy {
        _getPredictionResponse
    }

    suspend fun getPrediction ( inputData: InputData )
    {
        getPredictionUseCase.invoke(inputData).let {
            loadingStatus.postValue( View.VISIBLE )
            _getPredictionResponse.postValue(it.body())
            loadingStatus.postValue(View.GONE)
        }
    }

}