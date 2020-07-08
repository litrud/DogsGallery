package com.litrud.dogsgallery.listphoto

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.api.DogsApiRepository
import com.litrud.dogsgallery.network.api.ApiObjectListString
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel(
    private val dogRepository: DogsApiRepository
) : ViewModel() {

    val subBreeds = MutableLiveData<List<String>>()
    val urlList = MutableLiveData<List<String>>()
    val serverErrorMessage = MutableLiveData<Int>()

    fun getListAllSubBreeds(breed: String) =
        dogRepository.getListAllSubBreeds(breed).enqueue(
            object : Callback<ApiObjectListString> {
                override fun onFailure(call: Call<ApiObjectListString>, t: Throwable) {
//                    Log.e("LITRUD", "onFailure in getListAllSubBreeds()")
                    t.printStackTrace()
                }
                override fun onResponse(
                    call: Call<ApiObjectListString>,
                    response: Response<ApiObjectListString>
                ) {
                    // get response
                    val apiObject = response.body()
                    if (apiObject == null)
                        serverErrorMessage.value = R.string.msg_server_error
                    else {
                        // get list of dogs sub-breeds
                        val subBreedsList = apiObject.message
                        // notify observers
                        subBreeds.value = subBreedsList
                    }
                }
            }
        )

    fun getPhotosURLsByBreed(breedKeyword: String) =
        dogRepository.getPhotosURLsByBreed(breedKeyword).enqueue(
            object : Callback<ApiObjectListString> {
                override fun onFailure(call: Call<ApiObjectListString>, t: Throwable) {
//                    Log.e("LITRUD", "onFailure in getPhotosURLsByBreed()")
                    t.printStackTrace()
                }
                override fun onResponse(
                    call: Call<ApiObjectListString>,
                    response: Response<ApiObjectListString>
                ) {
                    // get response
                    val apiObject = response.body()
                    if (apiObject == null) {
                        serverErrorMessage.value = R.string.msg_server_error
                    } else {
                        // get list of URLs of photos
                        val urls = apiObject.message
                        // notify observers
                        urlList.value = urls
                    }
                }
            })

    fun getPhotosURLsBySubBreed(breed: String, subBreed: String) =
        dogRepository.getPhotosURLsBySubBreed(breed, subBreed).enqueue(
            object : Callback<ApiObjectListString> {
                override fun onFailure(call: Call<ApiObjectListString>, t: Throwable) {
                    Log.e("LITRUD", "onFailure in getPhotosURLsBySubBreed()")
                    t.printStackTrace()
                }
                override fun onResponse(
                    call: Call<ApiObjectListString>,
                    response: Response<ApiObjectListString>
                ) {
                    // get response
                    val apiObject = response.body()
                    if (apiObject == null)
                        serverErrorMessage.value = R.string.msg_server_error
                    else {
                        // get list of URLs of photos
                        val subUrls = apiObject.message
                        // notify observers
                        urlList.value = subUrls
                    }
                }
            })

    fun getUrl(position: Int)
            = urlList.value!![position]

    fun getItemCount()
            = urlList.value?.size ?: -1
}