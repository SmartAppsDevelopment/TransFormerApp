package com.example.sofittasktranformer.model

import com.google.gson.annotations.SerializedName


data class Transformers(
    @SerializedName("transformers") var transformers: ArrayList<TranFormerDataModel> = arrayListOf()
)
