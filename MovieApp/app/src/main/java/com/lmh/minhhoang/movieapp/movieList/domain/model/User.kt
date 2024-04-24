package com.lmh.minhhoang.movieapp.movieList.domain.model

class User {
    var id:String?=null
    var email:String?=null
    var password:String?= null
    var power:String?=null
 constructor()
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    constructor(id: String?, email: String?, password: String?,power:String?) {
        this.id = id
        this.email = email
        this.password = password
        this.power = power
    }


}