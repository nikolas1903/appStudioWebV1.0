package com.example.studioweb.services.request

import com.google.gson.annotations.SerializedName

sealed class User {

    class Login() {
        @SerializedName("CPF")
        var cpf: String = ""

        @SerializedName("Senha")
        var senha: String = ""
    }

    class Register() {
        @SerializedName("CPF")
        var cpf: String = ""

        @SerializedName("Email")
        var email: String = ""

        @SerializedName("Nascimento")
        var nascimento: String = ""

        @SerializedName("Nome")
        var nome: String = ""

        @SerializedName("Senha")
        var senha: String = ""

        @SerializedName("Telefone")
        var telefone: String = ""
    }

    class Update() {
        @SerializedName("CPF")
        var cpf: String = ""

        @SerializedName("Email")
        var email: String = ""

        @SerializedName("Nascimento")
        var nascimento: String = ""

        @SerializedName("Nome")
        var nome: String = ""

        @SerializedName("Senha")
        var senha: String = ""

        @SerializedName("Telefone")
        var telefone: String = ""
    }
}