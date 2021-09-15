package com.example.studioweb.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.studioweb.constants.UserConstants
import com.example.studioweb.listener.APIListener
import com.example.studioweb.services.UserModel
import com.example.studioweb.services.generics.BaseResponse
import com.example.studioweb.services.repository.UserRepository
import com.example.studioweb.services.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mLoggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = mLoggedUser

    private val mUserRepository = UserRepository()
    private val mSharedPreferences = SecurityPreferences(application)

    val mLogin = MutableLiveData<Boolean>()
    var login: LiveData<Boolean> = mLogin

    fun doLogin(cpf: String, senha: String) {
        mUserRepository.login(cpf, senha, object : APIListener {
            override fun onSuccess(model: BaseResponse<UserModel.LoginResponse>) {

                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.EMAIL, it.email) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.NOME, it.nome) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.TELEFONE, it.telefone) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.CPF, it.cpf) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.NASCIMENTO, it.nascimento) }

                if (model.sucesso) {
                    setLoginTrue(model)
                } else {
                    setLoginFalse(model)
                }
            }

            override fun onFailure(message: String) {
                mLogin.value = false
            }

        })
    }

    fun verifyLoggedUser() {
        val cpf = mSharedPreferences.get(UserConstants.SHARED.CPF)
        val logged = (cpf != "")
        mLoggedUser.value = logged
    }

    fun setLoginFalse(model: BaseResponse<UserModel.LoginResponse>) {
        Toast.makeText(getApplication(), model.mensagem, Toast.LENGTH_SHORT).show()
    }

    fun setLoginTrue(model: BaseResponse<UserModel.LoginResponse>) {
        mLogin.value = true
        Toast.makeText(getApplication(), model.mensagem, Toast.LENGTH_SHORT).show()
    }
}