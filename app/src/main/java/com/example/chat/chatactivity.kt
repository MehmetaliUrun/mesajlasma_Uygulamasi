package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PlayGamesAuthCredential
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class chatactivity : AppCompatActivity() {


    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messagebox: EditText
    private lateinit var senButton: ImageView
    private lateinit var messageAdaptor: messageAdaptor
    private lateinit var messageList:ArrayList<message>
    private lateinit var mDbRef:DatabaseReference


    var receiverRoom:String?=null
    var senderRoom:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)



        val name =intent.getStringExtra("name")
        val receiverUid =intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()
        senderRoom=receiverUid + senderUid
        receiverRoom=senderUid + receiverUid

        supportActionBar?.title =name

        chatRecyclerView=findViewById(R.id.chatrecylerView)
        messagebox=findViewById(R.id.messagebox)
        senButton=findViewById(R.id.imageview2)
        messageList= ArrayList()
        messageAdaptor= messageAdaptor(this,messageList)


        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdaptor

        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children){

                    val message=postSnapshot.getValue(message::class.java)
                    messageList.add(message!!)

                }
                messageAdaptor.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


        senButton.setOnClickListener{

            val message =messagebox.text.toString()
            val messageObject=message(message,senderUid)
            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject)
                .addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)


                }
            messagebox.setText("")

        }



    }
}