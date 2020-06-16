package com.litrud.dogsgallery.network.api

import retrofit2.Call

class DogsApiRepository(private val dogService: DogsApiWebservice) {

    fun getListAllBreed() :
            Call<ApiObjectMapString> = dogService.getListAllBreed()

    fun getListAllSubBreeds(breed: String) :
            Call<ApiObjectListString> = dogService.getListAllSubBreeds(breed)

    fun getPhotosURLsByBreed(breed: String) :
            Call<ApiObjectListString> = dogService.getPhotosURLsByBreed(breed)

    fun getPhotosURLsBySubBreed(breed: String, subbreed: String) :
            Call<ApiObjectListString> = dogService.getPhotosURLsBySubBreed(breed, subbreed)

    fun getRandomPhotoByBreed(breed: String) :
            Call<ApiObjectStringString> = dogService.getRandomPhotoByBreed(breed)

    fun getRandomPhoto() :
            Call<ApiObjectStringString> = dogService.getRandomPhoto()

}