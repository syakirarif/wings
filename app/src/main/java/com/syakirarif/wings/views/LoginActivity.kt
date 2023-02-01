package com.syakirarif.wings.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syakirarif.wings.core.model.User
import com.syakirarif.wings.database.AppDatabase
import com.syakirarif.wings.databinding.ActivityLoginBinding
import com.syakirarif.wings.utils.encodeToBase64
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (appDatabase.containsUserData()) {
            goToMainAct()
        }

        binding.apply {
            btnSignIn.setOnClickListener {
                processLogin()
            }
        }

    }

    private fun processLogin() {
        binding.apply {
            if (edtUsername.text?.isEmpty() == false && edtPassword.text?.isEmpty() == false) {
                val uname = edtUsername.text.toString()
                val passw = edtPassword.text.toString()
                val user = User(
                    username = uname,
                    password = passw.encodeToBase64()
                )
                appDatabase.setUserData(user)
                goToMainAct()
            } else {
                if (edtUsername.text?.isEmpty() == true) {
                    edtUsername.error = "Username tidak boleh kosong"
                    edtUsername.requestFocus()
                } else if (edtPassword.text?.isEmpty() == true) {
                    edtPassword.error = "Password tidak boleh kosong"
                    edtPassword.requestFocus()
                }
            }
        }
    }

    private fun goToMainAct() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}