package com.tryouts.finalyearproject.data.repo

import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.data.models.OutputData
import retrofit2.Response

interface MainRepository {
    suspend fun getPrediction(inputData: InputData) : Response<OutputData>
}