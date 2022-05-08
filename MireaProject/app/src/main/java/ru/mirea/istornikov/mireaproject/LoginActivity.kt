package ru.mirea.istornikov.mireaproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ru.mirea.istornikov.mireaproject.ui.settings.SettingsFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var login : EditText
    private lateinit var password : EditText
    private lateinit var firebaseAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(checkRemeberMe()){
            Toast.makeText(this, "Successful login",
                Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            return
        }

        login = findViewById(R.id.login)
        password = findViewById(R.id.password)

        firebaseAuth = FirebaseAuth.getInstance()
    }


    fun btnClick(view: View){
        createAccount()
    }

    private fun checkRemeberMe() : Boolean{
        val preferences = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
        println(preferences.all)
        return preferences.getBoolean("remember", false)
    }


    private fun checkData() : Boolean {
        if(login.text.toString().isEmpty()) {
            Toast.makeText(this, "Empty login",
                Toast.LENGTH_SHORT).show()
            login.error = "Empty"
            return false
        }
        if(password.text.toString().isEmpty()) {
            Toast.makeText(this, "Empty password",
                Toast.LENGTH_SHORT).show()
            password.error = "Empty"
            return false
        }

        return true
    }


    private fun createAccount(){
        val loginString = login.text.toString()
        val passwordString = password.text.toString()

        if(!checkData()) return

        firebaseAuth.createUserWithEmailAndPassword(loginString, passwordString).addOnCompleteListener { cIt ->
            if(cIt.isSuccessful){
                Toast.makeText(this, "Successful registration",
                    Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(loginString, passwordString).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Successful login",
                            Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    else{
                        password.error = "Invalid password"
                        Toast.makeText(this, "Invalid password",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}