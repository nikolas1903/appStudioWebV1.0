package com.example.studioweb.services

import com.google.gson.annotations.SerializedName

class UserModel {

    class LoginResponse {
        @SerializedName("CPF")
        var cpf: String = ""

        @SerializedName("Nascimento")
        var nascimento: String = ""

        @SerializedName("Nome")
        var nome: String = ""

        @SerializedName("Email")
        var email: String = ""

        @SerializedName("Telefone")
        var telefone: String = ""

        @SerializedName("Senha")
        var senha: String = ""

        @SerializedName("Sucesso")
        var sucesso: String = ""

        @SerializedName("Mensagem")
        var mensagem: String = ""
    }
}

