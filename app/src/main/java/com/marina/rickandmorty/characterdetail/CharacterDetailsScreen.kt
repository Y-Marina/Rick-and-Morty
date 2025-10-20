package com.marina.rickandmorty.characterdetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.marina.rickandmorty.R
import com.marina.rickandmorty.data.models.Character
import com.marina.rickandmorty.util.Resource

@Composable
fun CharacterDetailsScreen(
    id: Int?,
    navController: NavController,
    viewModel: CharacterDetailsViewModel = hiltViewModel<CharacterDetailsViewModel>()
) {
    val characterInfo = produceState(initialValue = Resource.Loading()) {
        value = viewModel.getCharacterInfo(id)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        when (characterInfo) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            is Resource.Success -> {
                characterInfo.data?.let {
                    CharacterInfo(it)
                }

            }

            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = characterInfo.message!!,
                        color = Color.Red,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        TopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun TopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun CharacterInfo(
    entry: Character
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = entry.imageUrl,
                contentDescription = entry.name,
                placeholder = painterResource(R.drawable.placeholder),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Text(
            text = "name: ${entry.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            text = "status: ${entry.status}",
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "species: ${entry.species}",
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "type: ${entry.type}",
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "gender: ${entry.gender}",
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "origin: ${entry.origin.name}",
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "location: ${entry.location.name}",
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
    }
}
