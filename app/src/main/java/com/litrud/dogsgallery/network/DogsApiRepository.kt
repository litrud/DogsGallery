package com.litrud.dogsgallery.network

import retrofit2.Call

object DogsApiRepository {
    private val webservice: DogsApiWebservice = DogsApiWebservice.create()

    fun getListAllBreeds() :
            Call<ApiObjectMapString> = webservice.getListAllBreeds()

    fun getPhotosURLsByBreed(breed: String) :
            Call<ApiObjectListString> = webservice.getPhotosURLsByBreed(breed)

    fun getRandomPhotoByBreed(breed: String) :
            Call<ApiObjectStringString> = webservice.getRandomPhotoByBreed(breed)

    fun getRandomPhoto() :
            Call<ApiObjectStringString> = webservice.getRandomPhoto()

}