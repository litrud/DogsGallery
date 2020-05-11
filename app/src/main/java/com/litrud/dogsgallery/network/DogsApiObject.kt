package com.litrud.dogsgallery.network


data class DogsApiObject(var message : Map<String, Array<String>>,
                         var status : String)