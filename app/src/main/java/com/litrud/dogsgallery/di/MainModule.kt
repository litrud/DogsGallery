package com.litrud.dogsgallery.di

import com.litrud.dogsgallery.listbreed.BreedListViewModel
import com.litrud.dogsgallery.listphoto.PhotosViewModel
import com.litrud.dogsgallery.network.DogsApiRepository
import com.litrud.dogsgallery.network.DogsApiWebservice
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val mainModule = module {
    single { DogsApiWebservice.create() }
    single { DogsApiRepository(get()) }
    viewModel { BreedListViewModel(get()) }
    viewModel { PhotosViewModel(get()) }
}