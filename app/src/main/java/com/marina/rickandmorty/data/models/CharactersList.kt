package com.marina.rickandmorty.data.models

data class CharactersList(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: String,
    val gender: String,
    val species: String
)
