package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userlist:ArrayList<User>
    //arraylisti burda tanımlıyoruz
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    private lateinit var adapter:adaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()



        binding.recyclerwiewtext.layoutManager=LinearLayoutManager(this)
        userlist= ArrayList()
        //arraylist initile ediyoruz
        val adapter=adaptor(userlist)
        binding.recyclerwiewtext.adapter = adapter


        val addValueEventListener =
            mDbRef.child("user").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userlist.clear()
                    for (postSnapshot in snapshot.children) {
                        val currentUser = postSnapshot.getValue(User::class.java)
                        if(mAuth.currentUser?.uid != currentUser?.uid){
                            userlist.add(currentUser!!)

                        }

                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuyu bağlıyoruz
        //BAĞLAMA İŞLEMLERİ HEP İNFİLATER İLE
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.cıkıs){
            mAuth.signOut()
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()
            return true




        }
        return true
    }



}


