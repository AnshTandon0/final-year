package com.tryouts.finalyearproject.data.repo

import com.tryouts.finalyearproject.data.ApiCalls
import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.data.models.OutputData
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api : ApiCalls
) : MainRepository {
    override suspend fun getPrediction(inputData: InputData): Response<OutputData> {
        return api.getPrediction(inputData)
    }
}