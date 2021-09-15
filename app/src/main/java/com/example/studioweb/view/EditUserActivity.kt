package com.example.studioweb.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.studioweb.R
import com.example.studioweb.constants.UserConstants
import com.example.studioweb.services.repository.local.SecurityPreferences
import com.example.studioweb.view.masks.CpfMask
import com.example.studioweb.view.masks.DataMask
import com.example.studioweb.view.masks.TelefoneMask
import com.example.studioweb.viewmodel.EditUserViewModel
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.image_back
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditUserActivity : AppCompatActivity() {

    private lateinit var mEditViewModel: EditUserViewModel
    private lateinit var mSecurityPreferences: SecurityPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() //Removendo ActionBar
        setContentView(R.layout.activity_edit_user)

        mEditViewModel = ViewModelProvider(this).get(EditUserViewModel::class.java)
        mSecurityPreferences = SecurityPreferences(this)

        et_editCpf.addTextChangedListener(CpfMask.mask("###.###.###-##", et_editCpf))
        et_editTelefone.addTextChangedListener(
            TelefoneMask.insert(
                "(##)#####-####",
                et_editTelefone
            )
        );
        et_editNascimento.addTextChangedListener(DataMask.insert("##/##/####", et_editNascimento));

        loadingData()
        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadingData() {
        et_editNome.setText(mSecurityPreferences.get(UserConstants.SHARED.NOME))
        et_editCpf.setText(mSecurityPreferences.get(UserConstants.SHARED.CPF))
        et_editEmail.setText(mSecurityPreferences.get(UserConstants.SHARED.EMAIL))
        et_editTelefone.setText(mSecurityPreferences.get(UserConstants.SHARED.TELEFONE))

        // Armazena o nascimento vindo do Shared Preferences nessa variável
        val nascimentoShared = mSecurityPreferences.get(UserConstants.SHARED.NASCIMENTO)
        val nascimentoT = nascimentoShared.replaceAfter("T", "")
        val nascimentoInvalido = nascimentoT.replace("T", "")

        // Formato que tem que ser mostrado
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        // Formato do nascimento que vem do Shared Preferences
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Formatando
        val nascimento1 =
            LocalDate.parse(nascimentoInvalido, formatter2).format(formatter).toString()

        // Substituindo o hífen pela barra
        val nascimento = nascimento1.replace("-", "/")

        et_editNascimento.setText(nascimento)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners() {
        btn_editar.setOnClickListener {

            val nome = et_editNome.text.toString()
            val cpf = et_editCpf.text.toString()
            val email = et_editEmail.text.toString()
            val telefone = et_editTelefone.text.toString()
            val nascimentoInvalido = et_editNascimento.text.toString()
            val senha = et_editSenha.text.toString()
            val confirmaSenha = et_editConfirmaSenha.text.toString()

            val nascimento1 = nascimentoInvalido.replace("/", "-")
            val formatoEnviado = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatoDesejado = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val nascimento =
                LocalDate.parse(nascimento1, formatoEnviado).format(formatoDesejado).toString()

            if (nome == "" || cpf == "" || email == "" || telefone == "" || nascimento == "") {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else if (!validEmail()) {
                Toast.makeText(this, "Informe um e-mail válido!", Toast.LENGTH_SHORT).show()
            } else if (senha != confirmaSenha) {
                Toast.makeText(this, "As duas senhas devem ser iguais!", Toast.LENGTH_SHORT).show()
                if (senha.length < 6) {
                    Toast.makeText(this, "Sua senha deve ter no mínimo 6 caracteres!", Toast.LENGTH_SHORT).show()
                }
            } else {
                mEditViewModel.update(nome, cpf, email, telefone, nascimento, senha)
            }
        }

        //Listener do botão de voltar
        image_back.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun validEmail(): Boolean {
        val str = et_editEmail.text.toString()
        return Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

}