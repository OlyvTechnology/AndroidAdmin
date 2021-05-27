package com.olyv.olyvadmin.model

import java.util.*

class UserDataByAdmin(
    var number:String,
    var isAllowed:Boolean,
    var uuid: String
){
    constructor():this("",false,"")
}