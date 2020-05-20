package com.litrud.dogsgallery.network.apiobject

data class ApiObjectMapString(var message: Map<String, MutableList<String>>,
                              var status: String)