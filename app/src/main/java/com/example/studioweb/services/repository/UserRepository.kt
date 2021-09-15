package com.example.studioweb.services.repository

import com.example.studioweb.listener.APIListener
import com.example.studioweb.services.UserModel
import com.example.studioweb.services.generics.BaseResponse
import com.example.studioweb.services.repository.remote.UserService
import com.example.studioweb.services.repository.remote.RetrofitClient
import com.example.studioweb.services.request.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val mRemote = RetrofitClient.createService(UserService::class.java)

    fun login(cpf: String, senha: String, listener: APIListener) {

        val login = User.Login()
        login.cpf = cpf
        login.senha = senha

        val call: Call<BaseResponse<UserModel.LoginResponse>> = mRemote.login(login)
        call.enqueue(object : Callback<BaseResponse<UserModel.LoginResponse>> {
            override fun onFailure(call: Call<BaseResponse<UserModel.LoginResponse>>, t: Throwable) {
                listener.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<BaseResponse<UserModel.LoginResponse>>, response: Response<BaseResponse<UserModel.LoginResponse>>) {
                response.body()?.let { listener.onSuccess(it) }
            }
        })

    }

    fun create(nome: String, cpf: String, email: String, telefone: String, nascimento: String, senha: String, listener: APIListener) {

        val register = User.Register()
        register.nome = nome
        register.email = email
        register.telefone = telefone
        register.nascimento = nascimento
        register.cpf = cpf
        register.senha = senha

        val call: Call<BaseResponse<UserModel.LoginResponse>> = mRemote.register(register)
        call.enqueue(object : Callback<BaseResponse<UserModel.LoginResponse>> {
            override fun onFailure(call: Call<BaseResponse<UserModel.LoginResponse>>, t: Throwable) {
                listener.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<BaseResponse<UserModel.LoginResponse>>, response: Response<BaseResponse<UserModel.LoginResponse>>) {
                response.body()?.let { listener.onSuccess(it) }
            }
        })

    }

    fun update(nome: String, cpf: String, email: String, telefone: String, nascimento: String, senha: String, listener: APIListener){

        val update = User.Update()
        update.nome = nome
        update.email = email
        update.telefone = telefone
        update.nascimento = nascimento
        update.cpf = cpf
        update.senha = senha

        val call: Call<BaseResponse<UserModel.LoginResponse>> = mRemote.update(update)
        call.enqueue(object : Callback<BaseResponse<UserModel.LoginResponse>> {
            override fun onFailure(call: Call<BaseResponse<UserModel.LoginResponse>>, t: Throwable) {
                listener.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<BaseResponse<UserModel.LoginResponse>>, response: Response<BaseResponse<UserModel.LoginResponse>>) {
                response.body()?.let { listener.onSuccess(it) }
            }
        })

    }
}