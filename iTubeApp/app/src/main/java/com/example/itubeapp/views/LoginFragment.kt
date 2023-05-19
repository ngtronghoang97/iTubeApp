package com.example.itubeapp.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.itubeapp.MainActivity
import com.example.itubeapp.R

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        val a = this.requireActivity() as MainActivity

        // signup
        val signUpBtn = rootView.findViewById<AppCompatButton>(R.id.signUpBtn)
        signUpBtn.setOnClickListener {
            (this.activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignUpFragment.newInstance())
                .commitNow()
        }

        val eUserName = rootView.findViewById<EditText>(R.id.loginUserName)
        val ePassword = rootView.findViewById<EditText>(R.id.loginPassword)

        val loginBtn = rootView.findViewById<View>(R.id.loginBtn)
        loginBtn.setOnClickListener {
            when {
                TextUtils.isEmpty(eUserName.text.toString().trim()) -> {
                    eUserName.error = "UserName Required!"
                }
                TextUtils.isEmpty(ePassword.text.toString().trim()) -> {
                    ePassword.error = "Password Required!"
                }
                else -> {
                    val usrName = eUserName.text.toString().trim()
                    val usrPassword = ePassword.text.toString().trim()
                    a.loginUser(usrName, usrPassword)
                }
            }
        }

        return rootView
    }
}