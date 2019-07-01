package com.rathana.photo_book.utils.eventbus

import com.rathana.photo_book.entity.Photo


 data class ItemClickedEvent(
     var photo : Photo?,
     var position: Int?
 )

data class AddBookmarkEvent(var photo: Photo)
