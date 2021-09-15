package com.example.studioweb.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studioweb.R
import com.example.studioweb.view.masks.CpfMask
import com.example.studioweb.view.masks.DataMask
import com.example.studioweb.view.masks.TelefoneMask
import com.example.studioweb.viewmodel.LoginViewModel
import com.example.studioweb.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.activity_login.et_cpf
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.image_back
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterActivity : AppCompatActivity() {

    private lateinit var mRegisterViewModel: RegisterViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() //Removendo ActionBar
        setContentView(R.layout.activity_register)

        mRegisterViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        et_registerCpf.addTextChangedListener(CpfMask.mask("###.###.###-##", et_registerCpf))
        et_registerTelefone.addTextChangedListener(
            TelefoneMask.insert(
                "(##)#####-####",
                et_registerTelefone
            )
        );
        et_registerNascimento.addTextChangedListener(
            DataMask.insert(
                "##/##/####",
                et_registerNascimento
            )
        );

        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners() {
        //Listener do botão de registrar!
        btn_registrar.setOnClickListener {
            val nome = et_registerNome.text.toString()
            val cpf = et_registerCpf.text.toString()
            val email = et_registerEmail.text.toString()
            val nascimentoInvalido = et_registerNascimento.text.toString()
            val telefone = et_registerTelefone.text.toString()
            val senha = et_registerSenha.text.toString()
            val confirmaSenha = et_confirmaRegisterSenha.text.toString()

            val nascimento1 = nascimentoInvalido.replace("/", "-")
            val formatoEnviado = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatoDesejado = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val nascimento =
                LocalDate.parse(nascimento1, formatoEnviado).format(formatoDesejado).toString()

            if (nome == "" || cpf == "" || email == "" || telefone == "" || nascimento == "" || senha == "" || confirmaSenha == "") {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else if (!validEmail()){
                Toast.makeText(this, "Informe um e-mail válido!", Toast.LENGTH_SHORT).show()
            } else if (senha != confirmaSenha) {
                Toast.makeText(this, "As duas senhas devem ser iguais!", Toast.LENGTH_SHORT).show()
            } else if (senha.length < 6) {
                Toast.makeText(
                    this,
                    "Sua senha deve ter no mínimo 6 caracteres!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mRegisterViewModel.create(nome, cpf, email, telefone, nascimento, senha)
            }
            if (mRegisterViewModel.mCreate.value == true) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        //Listener do botão de voltar
        image_back.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validEmail() : Boolean {
        val str = et_registerEmail.text.toString()
        return Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

}