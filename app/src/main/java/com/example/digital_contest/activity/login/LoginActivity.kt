package com.example.digital_contest.activity.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digital_contest.activity.main.MainActivity
import com.example.digital_contest.R
import com.example.digital_contest.activity.signUp.SignUpActivity
import com.example.digital_contest.activity.sphash.authDB
import com.example.digital_contest.activity.sphash.temp_userData
import com.example.digital_contest.model.User
import com.example.digital_contest.databinding.ActivityLoginBinding
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var viewModel : LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.txtLoginSingUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoginLogin.setOnClickListener {
            login()
        }
    }

    fun login(){
        val loadingDialog = Dialog(this@LoginActivity)
        loadingDialog.setContentView(R.layout.dialog_loading)
        loadingDialog.show()

        CoroutineScope(Dispatchers.Main).launch {
            val loginResult : User? = viewModel.login()
            loadingDialog.dismiss()

            if(loginResult == null){
                Toast.makeText(this@LoginActivity, "로그인에 실패했습니다.", Toast.LENGTH_LONG).show()
                return@launch
            }

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra("userData", loginResult)
            startActivity(intent)
            finish()
        }
    }
}