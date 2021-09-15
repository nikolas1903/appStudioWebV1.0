package com.example.studioweb.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.studioweb.R
import com.example.studioweb.constants.UserConstants
import com.example.studioweb.services.repository.local.SecurityPreferences
import com.example.studioweb.view.masks.CpfMaskTv
import com.example.studioweb.view.masks.DataMaskTv
import com.example.studioweb.view.masks.TelefoneMaskTv
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var mSecurityPreferences: SecurityPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() //Removendo ActionBar
        setContentView(R.layout.activity_main)

        tv_cpfCompleto.addTextChangedListener(CpfMaskTv.mask("###.###.###-##", tv_cpfCompleto))
        tv_telefoneCompleto.addTextChangedListener(TelefoneMaskTv.insert("(##) # ####-####", tv_telefoneCompleto));
        tv_nascimentoCompleto.addTextChangedListener(DataMaskTv.insert("##/##/####", tv_nascimentoCompleto));

        mSecurityPreferences = SecurityPreferences(this)

        setData()
        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData() {
        // Setando dados do usuário na tela principal.
        tv_emailCompleto.text = mSecurityPreferences.get(UserConstants.SHARED.EMAIL)
        tv_cpfCompleto.text = mSecurityPreferences.get(UserConstants.SHARED.CPF)
        tv_telefoneCompleto.text = mSecurityPreferences.get(UserConstants.SHARED.TELEFONE)

        // Armazena o nascimento vindo do Shared Preferences nessa variável
        val nascimentoShared = mSecurityPreferences.get(UserConstants.SHARED.NASCIMENTO)
        val nascimentoT = nascimentoShared.replaceAfter("T", "")
        val nascimentoInvalido = nascimentoT.replace("T", "")

        // Formato que tem que ser mostrado
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        // Formato do nascimento que vem do Shared Preferences
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Formatando
        val nascimento = LocalDate.parse(nascimentoInvalido, formatter2).format(formatter).toString()
        // Substituindo o hífen pela barra
        nascimento.replace("-", "/")
        tv_nascimentoCompleto.text = nascimento


        tv_nomeCompleto.text = mSecurityPreferences.get(UserConstants.SHARED.NOME)
    }

    private fun setListeners() {
        //Listener do botão de Logout
        tv_logout.setOnClickListener {
            mSecurityPreferences.remove(UserConstants.SHARED.CPF)
            Toast.makeText(this, "Deslogado com sucesso!", Toast.LENGTH_SHORT).show()
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Listener do botão de Alterar Dados
        tv_alterarDados.setOnClickListener {
            intent = Intent(this, EditUserActivity::class.java)
            startActivity(intent)
        }

    }
}