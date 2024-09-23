package com.example.recipebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.recipebox.data.RecipeDatabase
import com.example.recipebox.navigation.AppDestination
import com.example.recipebox.ui.screens.DetailsScreen
import com.example.recipebox.ui.screens.FormScreen
import com.example.recipebox.ui.screens.HomeScreen
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.recipebox.viewModel.RecipeViewModel
import com.example.recipebox.viewModel.Repository

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe.db"
        ).build()
    }

    private val viewModel by viewModels<RecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            RecipeBoxTheme(dynamicColor = false) {
                RecipeBox(
                    viewModel = viewModel,
                    mainActivity = this,
                    navController = navController
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AppDestination.Home.route
                    ) {
                        composable(AppDestination.Home.route) {
                            HomeScreen(
                                viewModel = viewModel,
                                onNavigateToForm = { navController.navigate(AppDestination.Form.route) },
                                onNavigateToDetail = { recipe -> navController.navigate("${AppDestination.Details.route}/${recipe.id}") }
                            )
                        }
                        composable(AppDestination.Form.route) {
                            FormScreen(
                                viewModel = viewModel,
                                onNavigateToHome = { navController.popBackStack() }
                            )
                        }
                        composable("${AppDestination.Details.route}/{recipeId}") { backStackEntry ->
                            val recipeId =
                                backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
                            recipeId?.let {

                                val recipe by viewModel.getRecipeById(it).observeAsState()
                                recipe?.let {
                                    DetailsScreen(
                                        viewModel = viewModel,
                                        recipe = it,
                                        onEditRecipe = { navController.navigate(AppDestination.Form.route) },
                                        onNavigateToHome = { navController.popBackStack() }
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeBox(
    navController: NavController,
    viewModel: RecipeViewModel,
    mainActivity: MainActivity,
    content: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold( modifier = Modifier.fillMaxSize() ) {
        Box( modifier = Modifier.padding(it) ) {
            Column(modifier = Modifier.padding(26.dp, 0.dp)) {
                Text(
                    modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp),
                    fontWeight = FontWeight.W600,
                    fontSize = 32.sp,
                    text = "Recipe Box"
                )
                content()
            }
        }
    }
}
