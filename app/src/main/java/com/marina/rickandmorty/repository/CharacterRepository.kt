package com.marina.rickandmorty.repository

import com.marina.rickandmorty.data.models.Character
import com.marina.rickandmorty.data.models.CharactersList
import com.marina.rickandmorty.data.models.Info
import com.marina.rickandmorty.data.models.Location
import com.marina.rickandmorty.data.models.Origin
import com.marina.rickandmorty.data.remote.RickAndMortyApi
import com.marina.rickandmorty.data.remote.responses.CharacterDto
import com.marina.rickandmorty.data.remote.responses.CharactersListDto
import com.marina.rickandmorty.data.remote.responses.InfoDto
import com.marina.rickandmorty.data.remote.responses.LocationDto
import com.marina.rickandmorty.data.remote.responses.OriginDto
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
    species = species,
    type = type,
    origin = origin.toOrigin(),
    location = location.toLocation(),
    episode = episode,
    created = created
)

private fun OriginDto.toOrigin() = Origin(
    name = name,
    url = url
)

private fun LocationDto.toLocation() = Location(
    name = name,
    url = url
)

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi
) {
    suspend fun getCharacterList(
        page: Int,
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ): Resource<CharactersList> {
        val response = try {
            api.getCharacterList(page, name, status, species, type, gender).toCharacterList()
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getCharacterInfo(id: Int?): Resource<Character> {
        val response = try {
            api.getCharacterInfo(id).toCharacter()
        } catch (e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }
}
