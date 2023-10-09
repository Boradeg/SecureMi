package com.GokulBorade.securemi.dataClasses

class UserDataSignUp {
        var email: String? = null
    var numb: String? = null



    constructor()
    constructor(email: String?,numb:String) {

        this.email = email
        this.numb = numb

    }
}