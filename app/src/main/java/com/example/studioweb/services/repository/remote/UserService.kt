package com.example.studioweb.services.repository.remote

import com.example.studioweb.services.UserModel
import com.example.studioweb.services.generics.BaseResponse
import com.example.studioweb.services.request.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {

    @POST("api/Login")
    fun login(
        @Body login: User.Login
    ): Call<BaseResponse<UserModel.LoginResponse>>


    @POST("/api/Cadastro")
    fun register(
        @Body register: User.Register

    ): Call<BaseResponse<UserModel.LoginResponse>>


    @PUT("/api/Alterar")
    fun update(
        @Body register: User.Update

    ): Call<BaseResponse<UserModel.LoginResponse>>

}

