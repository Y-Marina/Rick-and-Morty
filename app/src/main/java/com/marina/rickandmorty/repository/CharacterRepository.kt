package com.marina.rickandmorty.repository

import com.marina.rickandmorty.R
import com.marina.rickandmorty.data.models.Character
import com.marina.rickandmorty.data.models.CharactersList
import com.marina.rickandmorty.data.models.Info
import com.marina.rickandmorty.data.remote.RickAndMortyApi
import com.marina.rickandmorty.data.remote.responses.CharacterDto
import com.marina.rickandmorty.data.remote.responses.CharactersListDto
import com.marina.rickandmorty.data.remote.responses.InfoDto
import com.marina.rickandmorty.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

private fun CharactersListDto.toCharacterList() = CharactersList(
    info = info.toInfo(),
    results = results.map { it.toCharacter() }
)

private fun InfoDto.toInfo() = Info(
    count = count,
    pages = pages,
    next = next,
    prev = prev
)

private fun CharacterDto.toCharacter() = Character(
    id = id,
    name = name,
    imageUrl = image,
    status = status,
    gender = gender,
    species = species
)

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi
) {
    suspend fun getCharacterList(page: Int): Resource<CharactersList> {
        val response = try {
            api.getCharacterList(page).toCharacterList()
        } catch (e: Exception) {
            println("myTag e = ${e.message}")
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}