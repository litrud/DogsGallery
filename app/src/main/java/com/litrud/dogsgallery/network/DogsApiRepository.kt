package com.litrud.dogsgallery.network

import com.litrud.dogsgallery.network.apiobject.ApiObjectListString
import com.litrud.dogsgallery.network.apiobject.ApiObjectMapString
import com.litrud.dogsgallery.network.apiobject.ApiObjectStringString
import retrofit2.Call

object DogsApiRepository {
    private val webservice: DogsApiWebservice = DogsApiWebservice.create()

    fun getListAllBreed() :
            Call<ApiObjectMapString> = webservice.getListAllBreed()

    fun getPhotosURLsByBreed(breed: String) :
            Call<ApiObjectListString> = webservice.getPhotosURLsByBreed(breed)

    fun getRandomPhotoByBreed(breed: String) :
            Call<ApiObjectStringString> = webservice.getRandomPhotoByBreed(breed)

    fun getRandomPhoto() :
            Call<ApiObjectStringString> = webservice.getRandomPhoto()

}