package com.litrud.dogsgallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.litrud.dogsgallery.network.DogsApiObject
import com.litrud.dogsgallery.network.DogsApiWebservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DogsApiRepository {
    private val webservice: DogsApiWebservice = DogsApiWebservice.create()

    fun getListAllBreeds() :
            Call<DogsApiObject> = webservice.getListAllBreeds()

    fun getPhotosByBreed(breed: String) :
            Call<DogsApiObject> = webservice.getPhotosByBreed(breed)

    fun getRandomPhotoByBreed(breed: String) :
            Call<DogsApiObject> = webservice.getRandomPhotoByBreed(breed)

    fun getRandomPhoto() :
            Call<DogsApiObject> = webservice.getRandomPhoto()
}