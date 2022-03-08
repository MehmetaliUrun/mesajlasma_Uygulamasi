package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth



    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        //action barı saklıyor

        mAuth= FirebaseAuth.getInstance()

        binding.kayit1.setOnClickListener {

            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        binding.giris1.setOnClickListener {

            val email =binding.email.text.toString()
            val password =binding.pasword1.text.toString()


            login(email,password);


        }


    }
//burası zaten firabase dokumanlarında yazıyor
    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent =Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"böyle bir kullanıcı yok",Toast.LENGTH_SHORT).show()

                }
            }



    }


    }
