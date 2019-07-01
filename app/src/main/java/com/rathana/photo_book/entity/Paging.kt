package com.rathana.photo_book.entity

data class Paging(var page:Int=0,
                  var limit: Int=25){

    var offset:Int =0
    get() = (page-1) * limit

    override fun toString(): String {
        return "Paging(page=$page, limit=$limit , getOffset=$offset)"
    }


}