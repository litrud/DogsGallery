package com.litrud.dogsgallery.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/*  api docs

    List All Breeds
        https://dog.ceo/dog-api/documentation/
    Photos By Breed
        https://dog.ceo/dog-api/documentation/breed
    Random Photo By Breed
        https://dog.ceo/dog-api/breeds-list
    Random Photo
        https://dog.ceo/dog-api/documentation/random
 */

interface DogsApiWebservice {

    @GET("breeds/list/all")
    fun getListAllBreeds() : Call<ApiObjectMapString>

    @GET("breed/{breed}/images")
    fun getPhotosURLsByBreed(@Path("breed") breed: String) : Call<ApiObjectListString>

    @GET("breed/{breed}/images/random")
    fun getRandomPhotoByBreed(@Path("breed") breed: String) : Call<ApiObjectStringString>

    @GET("breeds/image/random")
    fun getRandomPhoto() : Call<ApiObjectStringString>

    companion object Factory {
        fun create() : DogsApiWebservice {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DogsApiWebservice::class.java)
        }
    }
}