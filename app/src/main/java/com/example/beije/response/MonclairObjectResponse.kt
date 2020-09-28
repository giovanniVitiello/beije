package com.example.beije.response

data class MonclairObjectResponse(
    val content: List<Content>,
    val lang: String,
    val status: Boolean
)

data class Content(
    val mediaDate: MediaDate,
    val mediaId: Int,
    val mediaTitleCustom: String,
    val mediaType: String,
    val mediaUrl: String,
    val mediaUrlBig: String
)

data class MediaDate(
    val dateString: String,
    val year: String
)


