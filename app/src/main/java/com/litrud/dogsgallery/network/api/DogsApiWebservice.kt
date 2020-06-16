package com.litrud.dogsgallery.network.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/*  api docs

    List All Breed
        https://dog.ceo/dog-api/documentation/
    List All Sub_Breed
        https://dog.ceo/dog-api/documentation/sub-breed
    Photos By Breed
        https://dog.ceo/dog-api/documentation/breed
    Random Photo By Breed
        https://dog.ceo/dog-api/breeds-list
    Random Photo
        https://dog.ceo/dog-api/documentation/random
 */

interface DogsApiWebservice {

    @GET("breeds/list/all")
    fun getListAllBreed() : Call<ApiObjectMapString>

    @GET("breed/{breed}/list")
    fun getListAllSubBreeds(
        @Path("breed") breed: String
    ) : Call<ApiObjectListString>

    @GET("breed/{breed}/images")
    fun getPhotosURLsByBreed(
        @Path("breed") breed: String
    ) : Call<ApiObjectListString>

    @GET("breed/{breed}/{subbreed}/images")
    fun getPhotosURLsBySubBreed(
        @Path("breed") breed: String,
        @Path("subbreed") subbreed: String
    ): Call<ApiObjectListString>

    @GET("breed/{breed}/images/random")
    fun getRandomPhotoByBreed(
        @Path("breed") breed: String
    ) : Call<ApiObjectStringString>

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