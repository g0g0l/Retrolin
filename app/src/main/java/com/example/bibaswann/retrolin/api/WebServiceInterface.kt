package com.example.bibaswann.retrolin.api

import com.example.bibaswann.retrolin.model.SampleGetModel
import retrofit2.http.GET
import rx.Observable

interface WebServiceInterface {
    @GET("posts/1")
    fun getSampleData(): Observable<SampleGetModel>
}
