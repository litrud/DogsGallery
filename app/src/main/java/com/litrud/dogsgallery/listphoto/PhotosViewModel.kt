package com.litrud.dogsgallery.listphoto

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.network.DogsApiRepository
import com.litrud.dogsgallery.network.apiobject.ApiObjectListString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel : ViewModel() {
    val urlList = MutableLiveData<List<String>>()

    fun getPhotosURLsByBreed(breedKeyword: String, breedHyphenated: String) {
        DogsApiRepository.getPhotosURLsByBreed(breedKeyword).enqueue(
            object : Callback<ApiObjectListString> {
                override fun onFailure(call: Call<ApiObjectListString>, t: Throwable) {
                    Log.e("LITRUD", "onFailure in getPhotosByBreed()")
                    t.printStackTrace()
                }
                override fun onResponse(call: Call<ApiObjectListString>,
                                        response: Response<ApiObjectListString>) {
                    // get response
                    val apiObject = response.body()
                    // get list of URLs of photos
                    val urls = apiObject!!.message
                    // select specific urls
                    subBreedList(urls, breedHyphenated)
                }
            })
    }

    private fun subBreedList(urls: List<String>, breedHyphenated: String) {
        // select from links only those links that contain breedHyphenated
        val subUrls = mutableListOf<String>()
        urls.forEach {
            if (it.contains(breedHyphenated))
                subUrls.add(it)
        }
        urlList.value = subUrls
    }

    fun getUrl(position: Int)
            = urlList.value!![position]

    fun getItemCount()
            = urlList.value?.size ?: -1
}