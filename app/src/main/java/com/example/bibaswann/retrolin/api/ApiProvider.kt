package com.example.bibaswann.retrolin.api

import android.util.Log
import com.example.bibaswann.retrolin.Constants
import com.example.bibaswann.retrolin.model.SampleGetModel
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ApiProvider {
    private val TAG = "ApiProvider"

    private val mApiServiceNetwork = ApiServiceNetwork.getInstance()

    fun callApi(apiResult: ApiResult) {
        try {
            mApiServiceNetwork.getNetworkService(Constants.API_ENDPOINT)
                    .getSampleData()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<SampleGetModel>() {
                        override fun onCompleted() {
                            //Do nothing for now
                        }

                        override fun onError(e: Throwable) {
                            Log.e(TAG, "onError", e)
                            apiResult.onAPIFail()
                        }

                        override fun onNext(sampleGetModel: SampleGetModel) {
                            Log.i(TAG, "Operation performed successfully")
                            apiResult.onModel(sampleGetModel)
                        }
                    })
        } catch (e: Exception) {
            Log.e(TAG, "Exception", e)
            apiResult.onError(e)
        }

    }


}