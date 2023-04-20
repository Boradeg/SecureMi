package com.example.securemi.dataClasses

class UserTrustyDetailDataClass {
    var userName: String? = null
    var userNumber: String? = null
   // var notification: String? = null
    constructor()
    constructor(userName: String?, userNumber: String?) {
        this.userName = userName
        this.userNumber = userNumber
      //  this.notification = notification
    }

//    constructor(
//
//        userName: String?,
//        userNumber: String?
//    ) {
//
//        this.userName = userName
//        this.userNumber = userNumber
//
//    }
}
