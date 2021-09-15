package com.example.studioweb.services.generics

import com.google.gson.annotations.SerializedName

open class BaseResponse<Data : Any?>(

    @SerializedName("Sucesso")
    var sucesso: Boolean = false,

    @SerializedName("Mensagem")
    var mensagem: String = "",

    @SerializedName("Data")
    var data: Data? = null

)