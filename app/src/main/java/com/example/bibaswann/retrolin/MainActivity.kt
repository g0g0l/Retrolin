package com.example.bibaswann.retrolin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bibaswann.retrolin.api.ApiProvider
import com.example.bibaswann.retrolin.api.ApiResult
import com.example.bibaswann.retrolin.model.BaseModel
import com.example.bibaswann.retrolin.model.SampleGetModel
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "ApiProvider"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCall.setOnClickListener {
            resultText.text="Making call..."
            ApiProvider().callApi(object : ApiResult {
                override fun onError(e: Exception) {
                    Log.e(TAG, e.message)
                }

                override fun onModel(baseModel: BaseModel) {
                    if (baseModel is SampleGetModel) {
                        val userId = baseModel.userId;
                        val id = baseModel.id;
                        val title = baseModel.title;
                        val body = baseModel.body;
                        resultText.text = "UserId: "+userId + "\n\nId: " + id + "\n\nTitle: " + title + "\n\nBody: " + body
                    }
                }

                override fun onJson(jsonObject: JsonObject) {
                    Log.e(TAG, "Received a different model")
                }

                override fun onAPIFail() {
                    Log.e(TAG, "Failed horribly")
                }

            })
        }
    }
}
