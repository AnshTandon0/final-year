package com.tryouts.finalyearproject.data.models

import com.google.gson.annotations.SerializedName

data class OutputData(
    @SerializedName("prediction")
    var prediction : String = ""
)
