package com.litrud.dogsgallery.listbreed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.network.apiobject.DogsApiRepository
import com.litrud.dogsgallery.network.apiobject.ApiObjectMapString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val ERROR_ON_SERVER = "The server is temporarily unavailable.\nTry later"

class BreedListViewModel(private val dogRepository: DogsApiRepository) : ViewModel() {
    val serverErrorMessage = MutableLiveData<String>()
    val breedMap = MutableLiveData<Map<String, MutableList<String>>>()
//    val randomPhotoApiObjectStringString: MutableLiveData<ApiObjectStringString> = MutableLiveData()

    fun getListAllBreed() {
        dogRepository.getListAllBreed().enqueue(object : Callback<ApiObjectMapString> {
            override fun onFailure(call: Call<ApiObjectMapString>, t: Throwable) {
                Log.e("LITRUD", "onFailure in getListAllBreeds()")
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ApiObjectMapString>,
                response: Response<ApiObjectMapString>
            ) {
                // get response
                val apiObject = response.body()

                if (apiObject == null) {
                    serverErrorMessage.value = ERROR_ON_SERVER
                } else {
                    // get list of dogs breeds
                    val breeds = apiObject.message
                    // notify observers
                    breedMap.value = breeds
                }
            }
        })
    }

//    fun getRandomPhotoByBreed(breed : String) {
//        dogRepository.getRandomPhotoByBreed(
//            breed
//        ).enqueue(object : Callback<ApiObjectStringString> {
//            override fun onFailure(call: Call<ApiObjectStringString>, t: Throwable) {
//                Log.e("LITRUD", "onFailure in getRandomPhotoByBreed()")
//                t.printStackTrace()
//            }
//            override fun onResponse(
//                call: Call<ApiObjectStringString>,
//                response: Response<ApiObjectStringString>
//            ) {
//                randomPhotoApiObjectStringString.value = response.body()
//            }
//        })
//    }
}