package com.marina.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marina.rickandmorty.characterdetail.CharacterDetailsScreen
import com.marina.rickandmorty.characterslist.CharactersListScreen
import com.marina.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBarDefaults
                    }
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
//                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "characters_list_screen"
                    ) {
                        composable("characters_list_screen") {
                            CharactersListScreen(navController = navController)
                        }
                        composable(
                            "character_detail_screen/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val id = remember {
                                it.arguments?.getInt("id")
                            }
                            CharacterDetailsScreen(
                                id = id,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
