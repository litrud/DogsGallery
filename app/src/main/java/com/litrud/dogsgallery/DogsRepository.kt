package com.litrud.dogsgallery

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import com.litrud.dogsgallery.model.DogsApiWebservice

object DogsRepository {
    val webservice : DogsApiWebservice = TODO("получить с помощью Dagger 2")

    fun getImages() : LiveData<List<Drawable>> {
        TODO()
    }
}
