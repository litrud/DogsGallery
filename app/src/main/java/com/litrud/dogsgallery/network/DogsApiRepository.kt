package com.litrud.dogsgallery.network

import com.litrud.dogsgallery.network.apiobject.ApiObjectListString
import com.litrud.dogsgallery.network.apiobject.ApiObjectMapString
import com.litrud.dogsgallery.network.apiobject.ApiObjectStringString
import retrofit2.Call

class DogsApiRepository(private val dogService: DogsApiWebservice) {

    fun getListAllBreed():
            Call<ApiObjectMapString> = dogService.getListAllBreed()

    fun getPhotosURLsByBreed(breed: String):
            Call<ApiObjectListString> = dogService.getPhotosURLsByBreed(breed)

    fun getRandomPhotoByBreed(breed: String):
            Call<ApiObjectStringString> = dogService.getRandomPhotoByBreed(breed)

    fun getRandomPhoto():
            Call<ApiObjectStringString> = dogService.getRandomPhoto()

}