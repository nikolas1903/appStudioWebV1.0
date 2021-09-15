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

class EditUserViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository = UserRepository()
    private val mSharedPreferences = SecurityPreferences(application)

    val mUpdate = MutableLiveData<Boolean>()

    fun update(nome: String, cpf: String, email: String, telefone: String, nascimento: String, senha: String) {

        mUserRepository.update(nome, cpf, email, telefone, nascimento, senha, object : APIListener{

            override fun onSuccess(model: BaseResponse<UserModel.LoginResponse>) {
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.EMAIL, it.email) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.NOME, it.nome) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.TELEFONE, it.telefone) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.CPF, it.cpf) }
                model.data?.let { mSharedPreferences.store(UserConstants.SHARED.NASCIMENTO, it.nascimento) }

                if (model.sucesso){
                    setEditTrue(model)
                }else{
                    setEditFalse(model)
                }

            }

            override fun onFailure(str: String) {
                mUpdate.value = false
            }

        })
    }

    fun setEditFalse(model: BaseResponse<UserModel.LoginResponse>){
        mUpdate.value = false
        Toast.makeText(getApplication(), model.mensagem, Toast.LENGTH_SHORT).show()
    }

    fun setEditTrue(model: BaseResponse<UserModel.LoginResponse>) {
        mUpdate.value = true
        Toast.makeText(getApplication(), model.mensagem, Toast.LENGTH_SHORT).show()
    }

}