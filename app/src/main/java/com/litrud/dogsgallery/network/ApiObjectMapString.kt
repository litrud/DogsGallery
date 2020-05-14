package com.litrud.dogsgallery.network

data class ApiObjectMapString(var message: Map<String, MutableList<String>>,
                              var status: String)