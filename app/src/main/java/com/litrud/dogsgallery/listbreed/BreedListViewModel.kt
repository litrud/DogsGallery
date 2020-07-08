package com.litrud.dogsgallery.listbreed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.api.DogsApiRepository
import com.litrud.dogsgallery.network.api.ApiObjectMapString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedListViewModel(
    private val dogRepository: DogsApiRepository
) : ViewModel() {

    val serverErrorMessage = MutableLiveData<Int>()
    val breedMap = MutableLiveData<Map<String, MutableList<String>>>()

    fun getListAllBreed() {
        dogRepository.getListAllBreed().enqueue(object : Callback<ApiObjectMapString> {
            override fun onFailure(call: Call<ApiObjectMapString>, t: Throwable) {
//                Log.e("LITRUD", "onFailure in getListAllBreeds()")
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ApiObjectMapString>,
                response: Response<ApiObjectMapString>
            ) {
                // get response
                val apiObject = response.body()
                if (apiObject == null)
                    serverErrorMessage.value = R.string.msg_server_error
                else {
                    // get list of dogs breeds
                    val breeds = apiObject.message
                    // notify observers
                    breedMap.value = breeds
                }
            }
        })
    }
}