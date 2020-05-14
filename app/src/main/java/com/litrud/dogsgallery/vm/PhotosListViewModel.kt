package com.litrud.dogsgallery.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.network.DogsApiRepository
import com.litrud.dogsgallery.network.ApiObjectListString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosListViewModel : ViewModel() {
    val urlList: MutableLiveData<MutableList<String>> = MutableLiveData()

    fun getPhotosURLsByBreed(breed: String) {
        DogsApiRepository.getPhotosURLsByBreed(breed).enqueue(object : Callback<ApiObjectListString> {
            override fun onFailure(call: Call<ApiObjectListString>, t: Throwable) {
                Log.e(TAG, "onFailure in getPhotosByBreed()")
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ApiObjectListString>, response: Response<ApiObjectListString>) {
                // get response
                val apiObject = response.body()
                // get list of URLs of photos
                val list = apiObject!!.message

                urlList.value = list
            }
        })
    }

//    fun getPhotosURLsByBreed(breed: String) {
//        DogsApiRepository.getPhotosByBreed(breed).enqueue(object : Callback<DogsApiObject> {
//            override fun onFailure(call: Call<DogsApiObject>, t: Throwable) {
//                Log.e("LITRUD", "onFailure in getPhotosByBreed()")
//                t.printStackTrace()
//            }
//            override fun onResponse(call: Call<DogsApiObject>, response: Response<DogsApiObject>) {
//                // get response
//                val apiObject = response.body()
//                // get map of URLs of photos
//                val map = apiObject!!.message
//                // get the list of URLs of photos
//                val list = map.getValue(KEY)
//
//                urlList.value = list
//            }
//        })
//    }

    companion object ApiObjectContract {
        const val KEY = "message"
        const val TAG = "LITRUD"
    }
}