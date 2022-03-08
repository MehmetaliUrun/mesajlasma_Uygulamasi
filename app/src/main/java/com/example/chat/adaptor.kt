package com.example.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.RecylerRowBinding

//recyclerlisttin olacağı yerlede adaptoru bağlamamız gerekiyor
//ayrıca adaptor için sınıfa ve o sınıfı kapsayan bir arraylistte lazım
//maine hit orada var o bağlantılar

class adaptor(val userlist: ArrayList<User>) : RecyclerView.Adapter<adaptor.UserHolder>() {


    class UserHolder (val binding: RecylerRowBinding):RecyclerView.ViewHolder(binding.root){
        //binding yapıyoruz vievi böylelikle binding kullanabiliyoruz
        //BURASI BİNDİNG KULLANILMAYANDA FARKLI BURDA YAPILANLAR SAYESİNDE BİNDİNG KULLANA BİLİYORUZ
        //BİNDİNG VERİM AÇISINDAN DAHA İYİ


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        //recycler_row'u baglama işlemi burada view binding kullanarak
        //recycler_row layoutuna bizim adaptorumuzu burdan bağlıyoruz

        val binding= RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserHolder(binding)

    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        //bağlandıktan sonra hangi textte hangi veri olucak
        //textRow row layoutttaki textin ismi
        holder.binding.textRow.text=userlist.get(position).name
        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context,chatactivity::class.java)
            intent.putExtra("name",userlist.get(position).name)
            intent.putExtra("uid",userlist.get(position).uid)

            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount():Int {
        return userlist.size

    }
}