package com.litrud.dogsgallery.network.api

data class ApiObjectMapString(var message: Map<String, MutableList<String>>,
                              var status: String)