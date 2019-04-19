package com.theblackthorn.snapclone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var  emailEditText: EditText? = null
    var  passwordEditText: EditText? = null
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        if (auth.currentUser != null) {
            logIn()
        }
    }

    fun goBtnClicked(view: View) {
        // check if we can log in the user
        auth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    auth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString()).addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            // add to database
                            logIn()
                        } else {
                            Toast.makeText(this, "Login Failed. Try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }

    fun logIn() {
        // move to next activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }
}
