package com.example.studioweb.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.studioweb.R
import com.example.studioweb.view.masks.CpfMask
import com.example.studioweb.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        //Máscara CPF
        et_cpf.addTextChangedListener(CpfMask.mask("###.###.###-##", et_cpf))

        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setListeners()
        observe()
        verifyLoggedUser()
    }

    private fun observe() {
        //Observa se o usuário fez o Loggin, e muda ele pra MainActivity
        mLoginViewModel.login.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        //Observa se o usuário continua logado, e leva ele direto pra Main sem precisar logar dnv
        mLoginViewModel.loggedUser.observe(this, Observer {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun setListeners() {

        //Listener Logo - levar para o Insta
        img_logo.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/studioweb.digital"))
            startActivity(browserIntent)
        }

        //Listener Login
        btn_login.setOnClickListener {
            val cpfInvalido = et_cpf.text.toString()
            val cpf = cpfInvalido.replace(".", "").replace("-", "")

            val senha = et_senha.text.toString()

            if (cpf.length == 11 && senha.length >= 6) {
                mLoginViewModel.doLogin(cpf, senha)
            } else {
                Toast.makeText(this, "Campos inválidos!", Toast.LENGTH_SHORT).show()
            }
        }

        //Listener Registrar
        tv_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun unmaskCpf() {


    }

    private fun verifyLoggedUser() {
        mLoginViewModel.verifyLoggedUser()
    }
}