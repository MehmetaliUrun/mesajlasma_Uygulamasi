package com.example.chat

class User {

    var name: String?=null
    var email: String?=null
    var uid: String?=null

//javadaki gibi recyclerview oluştururken sınıf oluşturup constructer oluştuturuyoruz

    constructor(){

    }

    constructor(name: String?,email: String?, uid:String?){
        this.name =name
        this.email =email
        this.uid =uid




    }




}