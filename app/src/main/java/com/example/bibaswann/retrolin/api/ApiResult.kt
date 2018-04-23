package com.example.bibaswann.retrolin.api

import com.example.bibaswann.retrolin.model.BaseModel
import com.google.gson.JsonObject

interface ApiResult {

    fun onError(e: Exception)

    fun onModel(baseModel: BaseModel)

    fun onJson(jsonObject: JsonObject)

    fun onAPIFail()
}
