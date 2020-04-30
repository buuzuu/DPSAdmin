package com.example.dpsadmin.model

import java.io.Serializable

data class BillFee(
    var fee: Fee,
    var isChecked:Boolean
): Serializable
{
    constructor() :  this(Fee(),false)
}