package com.tryouts.finalyearproject.ui.usecases

import com.tryouts.finalyearproject.data.models.InputData
import com.tryouts.finalyearproject.data.models.OutputData
import com.tryouts.finalyearproject.data.repo.MainRepository
import retrofit2.Response
import javax.inject.Inject

class GetPredictionUseCase@Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke(inputData: InputData) : Response<OutputData> {
        return repository.getPrediction(inputData)
    }
}