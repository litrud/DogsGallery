package com.litrud.dogsgallery.listphoto

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.network.DogsApiRepository
import com.litrud.dogsgallery.network.apiobject.ApiObjectListString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoListViewModel(private val dogRepository: DogsApiRepository) : ViewModel() {
    val urlList: MutableLiveData<MutableList<String>> = MutableLiveData()

    fun getPhotosURLsByBreed(breed: String) {
        dogRepository.getPhotosURLsByBreed(breed).enqueue(object : Callback<ApiObjectListString> {
            override fun onFailure(call: Call<ApiObjectListString>, t: Throwable) {
                Log.e(TAG, "onFailure in getPhotosByBreed()")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ApiObjectListString>,
                response: Response<ApiObjectListString>
            ) {
                // get response
                val apiObject = response.body()
                // get list of URLs of photos
                val list = apiObject!!.message

                urlList.value = list
            }
        })
    }

    companion object ApiObjectContract {
        const val TAG = "LITRUD"
    }
}