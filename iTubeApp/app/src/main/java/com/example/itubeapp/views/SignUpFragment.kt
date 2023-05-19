package com.example.itubeapp.views

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.itubeapp.MainActivity
import com.example.itubeapp.R

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val a = this.requireActivity() as MainActivity

        val eFullName = rootView.findViewById<EditText>(R.id.fullName)
        val eUserName = rootView.findViewById<EditText>(R.id.signUpUserName)
        val ePassword = rootView.findViewById<EditText>(R.id.password)
        val eConfirmPassword = rootView.findViewById<EditText>(R.id.confirmPassword)

        val createAccount = rootView.findViewById<AppCompatButton>(R.id.createAccount)
        createAccount.setOnClickListener {
            when {
                TextUtils.isEmpty(eFullName.text.toString().trim()) -> {
                    eFullName.error = "Name required!"
                }
                TextUtils.isEmpty(eUserName.text.toString().trim()) -> {
                    eUserName.error = "UserName required!"
                }
                TextUtils.isEmpty(ePassword.text.toString().trim()) -> {
                    ePassword.error = "Password required!"
                }
                TextUtils.isEmpty(eConfirmPassword.text.toString().trim()) -> {
                    eConfirmPassword.error = "Confirm password required!"
                }
                else -> {
                    val p = ePassword.text.toString().trim()
                    val cP = eConfirmPassword.text.toString().trim()
                    if(p != cP) {
                        eConfirmPassword.error = "Password Mismatch!"
                    } else {
                        val fullName = eFullName.text.toString().trim()
                        val userName = eUserName.text.toString().trim()
                        val password = ePassword.text.toString().trim()

                        Log.d("res", "$fullName : $userName : $password")
                        a.createNewUser(fullName, userName, password)
                    }
                }
            }
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Let's handle onClick back btn
                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LoginFragment.newInstance()).commitNow()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}