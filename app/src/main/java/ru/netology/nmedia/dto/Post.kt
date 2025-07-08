package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 1999999,
    val reposts: Int = 1,
    val likeByMe: Boolean = false


)
