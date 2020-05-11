package com.litrud.dogsgallery

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.litrud.dogsgallery.network.DogsApiObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogBreedsListViewModel : ViewModel() {
    val breedsMapObject: MutableLiveData<DogsApiObject> = MutableLiveData()
    val randomPhotoObject: MutableLiveData<DogsApiObject> = MutableLiveData()

    fun getListAllBreeds() {
        DogsApiRepository.getListAllBreeds().enqueue(object : Callback<DogsApiObject> {
            override fun onFailure(call: Call<DogsApiObject>, t: Throwable) {
                Log.e("LITRUD", "onFailure")
                t.printStackTrace()
            }
            override fun onResponse(call: Call<DogsApiObject>, response: Response<DogsApiObject>) {
                breedsMapObject.value = response.body()
            }
        })
    }

    fun getRandomPhotoByBreed(breed : String) {
        DogsApiRepository.getPhotosByBreed(breed).enqueue(object : Callback<DogsApiObject> {
            override fun onFailure(call: Call<DogsApiObject>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<DogsApiObject>, response: Response<DogsApiObject>) {
                randomPhotoObject.value = response.body()
            }
        })
    }
}