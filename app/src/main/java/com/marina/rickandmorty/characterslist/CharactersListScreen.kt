package com.marina.rickandmorty.characterslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.marina.rickandmorty.R
import com.marina.rickandmorty.data.models.Character
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CharactersListScreen(
    navController: NavController,
    viewModel: CharactersListViewModel = hiltViewModel<CharactersListViewModel>()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var name by remember {
        mutableStateOf("")
    }

    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }

    val status = listOf("alive", "dead", "unknown")

    var species by remember {
        mutableStateOf("")
    }

    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(60.dp),
                containerColor = Color.Gray,
                onClick = { showBottomSheet = true }
            ) {
                Image(
                    painter = painterResource(R.drawable.filters),
                    contentDescription = "filters",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.rick_and_morty_logo),
                    contentDescription = "Rick_and_Morty",
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                SearchBar(
                    hint = "Search...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    viewModel.searchCharacter(it)
                }
                Spacer(modifier = Modifier.height(16.dp))
                CharactersList(
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                    navController = navController
                )

            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "name",
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier) {
                            BasicTextField(
                                value = name,
                                onValueChange = {
                                    name = it
                                },
                                maxLines = 1,
                                singleLine = true,
                                textStyle = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(5.dp, CircleShape)
                                    .background(Color.White, CircleShape)
                                    .padding(horizontal = 20.dp, vertical = 12.dp)
                            )
                        }
                    }

                    ButtonGroup(
                        overflowIndicator = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        for (i in 0 until 3) {
                            val checked = i == selectedIndex

                            this.toggleableItem(
                                checked = checked,
                                label = status[i],
                                weight = 1f,
                                onCheckedChange = {
                                    if (i == selectedIndex) {
                                        selectedIndex = -1
                                    } else {
                                        selectedIndex = i
                                    }
                                }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "species",
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier) {
                            BasicTextField(
                                value = species,
                                onValueChange = {
                                    species = it
                                },
                                maxLines = 1,
                                singleLine = true,
                                textStyle = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(5.dp, CircleShape)
                                    .background(Color.White, CircleShape)
                                    .padding(horizontal = 20.dp, vertical = 12.dp)
                            )
                        }
                    }

                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                                viewModel.searchByFilter(
                                    name = name,
                                    status = if (selectedIndex in 0 until status.size) {
                                        status[selectedIndex]
                                    } else {
                                        ""
                                    },
                                    species = species
                                )
                            }
                        }
                    }) {
                        Text("применить")
                    }
                }

            }
        }
    }

}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed != it.isFocused
                }
        )
        if (isHintDisplayed && text.isEmpty()) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CharactersListViewModel = hiltViewModel<CharactersListViewModel>()
) {
    val charactersList by remember { viewModel.charactersList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(charactersList.size) {
                if (it >= charactersList.size - 1 && !endReached && !isLoading && !isSearching) {
                    viewModel.loadCharacterPaginated()
                }
                CharactersEntry(
                    entry = charactersList[it],
                    navController = navController
                )
            }
            if (isLoading) {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .weight(2f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Red
                        )
                    }
                }
            }
            if (loadError.isNotEmpty() && !isLoading) {
                item(
                    span = { GridItemSpan(2) }
                ) {
                    RetrySection(error = loadError) {
                        viewModel.loadCharacterPaginated()
                    }
                }
            }
        }

    }
}

@Composable
fun CharactersEntry(
    entry: Character,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CharactersListViewModel = hiltViewModel<CharactersListViewModel>()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(0.7f)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate(
                    "character_detail_screen/${entry.id}"
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            var isLoading by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
            ) {
                AsyncImage(
                    model = entry.imageUrl,
                    contentDescription = entry.name,
                    contentScale = ContentScale.Crop,
                    onSuccess = {
                        isLoading = false
                    },
                    onLoading = {
                        isLoading = true
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                        .clip(shape = RoundedCornerShape(topStart = 15.dp))
                        .background(Color.Black)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = entry.status,
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = entry.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${entry.gender} | ${entry.species}",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
