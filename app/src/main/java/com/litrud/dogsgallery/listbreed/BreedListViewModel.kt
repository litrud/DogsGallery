package com.litrud.dogsgallery.listbreed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.network.DogsApiRepository
import com.litrud.dogsgallery.network.apiobject.ApiObjectMapString
import com.litrud.dogsgallery.network.apiobject.ApiObjectStringString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreedListViewModel : ViewModel() {
    val breedsApiObjectMapString: MutableLiveData<ApiObjectMapString> = MutableLiveData()
    val randomPhotoApiObjectStringString: MutableLiveData<ApiObjectStringString> = MutableLiveData()

    fun getListAllBreed() {
        DogsApiRepository.getListAllBreed().enqueue(object : Callback<ApiObjectMapString> {
            override fun onFailure(call: Call<ApiObjectMapString>, t: Throwable) {
                Log.e("LITRUD", "onFailure in getListAllBreeds()")
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ApiObjectMapString>, response: Response<ApiObjectMapString>) {
                breedsApiObjectMapString.value = response.body()
            }
        })
    }

    fun getRandomPhotoByBreed(breed : String) {
        DogsApiRepository.getRandomPhotoByBreed(breed).enqueue(object : Callback<ApiObjectStringString> {
            override fun onFailure(call: Call<ApiObjectStringString>, t: Throwable) {
                Log.e("LITRUD", "onFailure in getRandomPhotoByBreed()")
                t.printStackTrace()
            }
            override fun onResponse(call: Call<ApiObjectStringString>, response: Response<ApiObjectStringString>) {
                randomPhotoApiObjectStringString.value = response.body()
            }
        })
    }
}