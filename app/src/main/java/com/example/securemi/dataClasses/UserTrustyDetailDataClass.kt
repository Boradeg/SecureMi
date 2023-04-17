package com.example.securemi.dataClasses

class UserTrustyDetailDataClass {
    var userNumber: String? = null
    var userName: String? = null



    constructor()
    constructor(name: String?,number:String) {

        this.userNumber = number
        this.userName = name

    }
}
