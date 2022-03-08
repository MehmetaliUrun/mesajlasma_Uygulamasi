package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.chat.databinding.ActivityLoginBinding
import com.example.chat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mAuth= FirebaseAuth.getInstance()
        //supportActionBar?.hide()


        binding.kayit1.setOnClickListener {
            val name =binding.name2.text.toString()
            val email =binding.email2.text.toString()
            val password=binding.password2.text.toString()

            signUp(name,email,password)


        }



    }

    private fun signUp(name:String, email:String, password:String) {

         mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Lütfen Şifrenizi ve emailinizi giriniz", Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun addUserToDatabase(name:String,email: String,uid:String){
        mDbRef =FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))


    }



}