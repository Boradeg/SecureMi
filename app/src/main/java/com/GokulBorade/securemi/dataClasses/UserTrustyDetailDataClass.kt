package com.GokulBorade.securemi.dataClasses

class UserTrustyDetailDataClass {
    var userName: String? = null
    var userNumber: String? = null
    var docId: String? = null
   // var notification: String? = null
    constructor()
    constructor(userName: String?, userNumber: String?) {
        this.userName = userName
        this.userNumber = userNumber
        this.docId = docId


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
