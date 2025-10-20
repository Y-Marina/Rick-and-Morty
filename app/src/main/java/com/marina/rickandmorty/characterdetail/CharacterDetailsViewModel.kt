package com.marina.rickandmorty.characterdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.marina.rickandmorty.data.models.Character
import com.marina.rickandmorty.repository.CharacterRepository
import com.marina.rickandmorty.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    var isLoading = mutableStateOf(false)

    suspend fun getCharacterInfo(id: Int?): Resource<Character> {
        isLoading.value = true
        return repository.getCharacterInfo(id)
    }
}
