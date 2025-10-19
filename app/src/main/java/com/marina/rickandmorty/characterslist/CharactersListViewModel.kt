package com.marina.rickandmorty.characterslist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.rickandmorty.data.models.Character
import com.marina.rickandmorty.data.models.CharactersList
import com.marina.rickandmorty.repository.CharacterRepository
import com.marina.rickandmorty.util.Constants.PAGE_SIZE
import com.marina.rickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.collections.plus

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private var curPage = 1

    var charactersList = mutableStateOf<List<Character>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<CharactersList>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadCharacterPaginated()
    }

    fun loadCharacterPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCharacterList(curPage)
            println("myTag result = $result")
            when (result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.info.count
                    val characterEntries = result.data.results
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    charactersList.value += characterEntries
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}