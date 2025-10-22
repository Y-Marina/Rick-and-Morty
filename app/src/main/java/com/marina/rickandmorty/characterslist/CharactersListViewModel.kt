package com.marina.rickandmorty.characterslist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marina.rickandmorty.data.models.Character
import com.marina.rickandmorty.repository.CharacterRepository
import com.marina.rickandmorty.util.Constants.PAGE_SIZE
import com.marina.rickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private var curPage = 1

    var charactersList = mutableStateOf<List<Character>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedList = listOf<Character>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadCharacterPaginated()
    }

    fun searchCharacter(query: String) {
        val listToSearch = if (isSearchStarting) {
            charactersList.value
        } else {
            cachedList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                charactersList.value = cachedList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                cachedList = charactersList.value
                isSearchStarting = false
            }
            charactersList.value = results
            isSearching.value = true
        }
    }

    fun loadCharacterPaginated(
        name: String = "",
        status: String = "",
        species: String = ""
    ) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCharacterList(curPage, name, status, species)
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

                is Resource.Loading -> {
                    loadError.value = ""
                    isLoading.value = true
                }
            }
        }
    }

    fun searchByFilter(
        name: String = "",
        status: String = "",
        species: String = ""
    ) {
        curPage = 1
        charactersList.value = emptyList()
        loadCharacterPaginated(name, status, species)
    }
}