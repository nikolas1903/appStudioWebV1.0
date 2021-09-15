package com.example.studioweb.listener

import com.example.studioweb.services.UserModel
import com.example.studioweb.services.generics.BaseResponse

interface APIListener {

    fun onSuccess(model: BaseResponse<UserModel.LoginResponse>)

    fun onFailure(str: String)

}