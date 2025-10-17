package com.marina.rickandmorty.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersListDto(
    @SerialName("info")
    val info: InfoDto,

    @SerialName("results")
    val results: ResultsDto
)

@Serializable
data class InfoDto(
    @SerialName("count")
    val count: Int,

    @SerialName("pages")
    val pages: Int,

    @SerialName("next")
    val next: String,

    @SerialName("prev")
    val prev: String
)

@Serializable
data class ResultsDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("status")
    val status: String,

    @SerialName("species")
    val species: String,

    @SerialName("type")
    val type: String,

    @SerialName("gender")
    val gender: String,

    @SerialName("origin")
    val origin: OriginDto,

    @SerialName("location")
    val location: LocationDto,

    @SerialName("image")
    val image: String,

    @SerialName("episode")
    val episode: String,

    @SerialName("url")
    val url: String,

    @SerialName("created")
    val created: String
)

@Serializable
data class OriginDto(
    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String
)

@Serializable
data class LocationDto(
    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String
)
