package ru.mirea.istornikov.firebaseauth

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var emailEditText: EditText
    private lateinit var passwordTextEdit: EditText
    private lateinit var statusTextView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var signInBtn: Button
    private lateinit var signOutBtn: Button
    private lateinit var createAcc: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailEditText = findViewById(R.id.email_input)
        passwordTextEdit = findViewById(R.id.password_input)
        statusTextView = findViewById(R.id.status_text_view)
        findViewById<View>(R.id.sign_in_button).setOnClickListener(this)
        findViewById<View>(R.id.create_account_button).setOnClickListener(this)
        findViewById<View>(R.id.sign_out_button).setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View) {
        val i = view.id
        if (i == R.id.create_account_button) {
            createAccount(
                emailEditText!!.text.toString(),
                passwordTextEdit!!.text.toString()
            )
        } else if (i == R.id.sign_in_button) {
            signIn(
                emailEditText!!.text.toString(),
                passwordTextEdit!!.text.toString()
            )
        } else if (i == R.id.sign_out_button) {
            signOut()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth!!.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            statusTextView!!.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            findViewById<View>(R.id.email_input).visibility = View.GONE
            findViewById<View>(R.id.password_input).visibility = View.GONE
            findViewById<View>(R.id.sign_in_button).visibility = View.GONE
            findViewById<View>(R.id.create_account_button).visibility = View.GONE
            findViewById<View>(R.id.sign_out_button).visibility = View.VISIBLE
        } else {
            statusTextView.setText(R.string.signed_out)
            findViewById<View>(R.id.password_input).visibility = View.VISIBLE
            findViewById<View>(R.id.email_input).visibility = View.VISIBLE
            findViewById<View>(R.id.sign_in_button).visibility = View.VISIBLE
            findViewById<View>(R.id.create_account_button).visibility = View.VISIBLE
            findViewById<View>(R.id.sign_out_button).visibility = View.VISIBLE
        }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = emailEditText!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailEditText!!.error = "Required."
            valid = false
        } else {
            emailEditText!!.error = null
        }
        val password = passwordTextEdit!!.text.toString()
        if (TextUtils.isEmpty(password)) {
            passwordTextEdit!!.error = "Required."
            valid = false
        } else {
            passwordTextEdit!!.error = null
        }
        return valid
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signOut() {
        auth!!.signOut()
        updateUI(null)
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
                if (!task.isSuccessful) {
                    statusTextView.setText(R.string.auth_failed)
                }
            }
    }
}