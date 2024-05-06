package com.tryouts.finalyearproject.data

import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.data.models.OutputData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCalls {

    @POST("/predict")
    suspend fun getPrediction(@Body input:InputData) : Response<OutputData>

}