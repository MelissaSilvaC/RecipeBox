package com.example.recipebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipebox.data.Recipe
import com.example.recipebox.navigation.AppDestination
import com.example.recipebox.navigation.bottomAppBarItems
import com.example.recipebox.ui.components.BottomAppBarItem
import com.example.recipebox.ui.components.RecipeBoxAppBar
import com.example.recipebox.ui.screens.DetailsScreen
import com.example.recipebox.ui.screens.FormEditScreen
import com.example.recipebox.ui.screens.FormScreen
import com.example.recipebox.ui.screens.UserDetailsScreen
import com.example.recipebox.ui.screens.appBarScreens.FeedScreen
import com.example.recipebox.ui.screens.appBarScreens.LibraryScreen
import com.example.recipebox.ui.screens.appBarScreens.ProfileScreen
import com.example.recipebox.ui.screens.loginScreens.LoginScreen
import com.example.recipebox.ui.screens.loginScreens.RegisterScreen
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.ui.theme.displayFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseApp = Firebase.initialize(this)
        auth = FirebaseAuth.getInstance(firebaseApp!!)
        db = FirebaseFirestore.getInstance(firebaseApp)

        val startDestination = if (auth.currentUser != null) {
            AppDestination.Feed.route
        } else {
            AppDestination.Login.route
        }

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            RecipeBoxTheme(dynamicColor = false) {
                //configurando bottomBar, topBar e FAB
                val selectedItem by remember(currentDestination) {
                    val item = currentDestination?.let { destination ->
                        bottomAppBarItems.find {
                            it.destination.route == destination.route
                        }
                    } ?: bottomAppBarItems.first()
                    mutableStateOf(item)
                }
                val containsBottomAppBarItems = currentDestination?.let { destination ->
                    bottomAppBarItems.find {
                        it.destination.route == destination.route
                    }
                } != null
                val isShowFab = currentDestination?.route != AppDestination.Login.route && currentDestination?.route != AppDestination.Register.route
                val isShowTopBar = currentDestination?.route != AppDestination.Login.route && currentDestination?.route != AppDestination.Register.route

                RecipeBox(
                    bottomAppBarItemSelected = selectedItem,
                    onBottomAppBarItemSelectedChange = {
                        val route = it.destination.route
                        navController.navigate(route) {
                            launchSingleTop = true
                            popUpTo(route)
                        }
                    },
                    navController = navController,
                    isShowTopBar = isShowTopBar,
                    isShowBottomBar = containsBottomAppBarItems,
                    isShowFab = isShowFab,
                    auth = auth
                ) {
                    //Cofigurando as rotas do app
                    NavHost(navController = navController, startDestination = startDestination
                    ) {
                        composable(AppDestination.Login.route) {
                            LoginScreen(
                                navigateToRegister = { navController.navigate(AppDestination.Register.route) },
                                navigateToFeed = {
                                    navController.navigate(AppDestination.Feed.route) {
                                        popUpTo(AppDestination.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(AppDestination.Register.route) {
                            RegisterScreen(
                                navigateToLogin = { navController.navigate(AppDestination.Login.route) },
                                navigateToFeed = {
                                    navController.navigate(AppDestination.Feed.route) {
                                        popUpTo(AppDestination.Register.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        //Telas da Bottom Bar
                        composable(AppDestination.Feed.route) {
                            FeedScreen(
                                navigateToUserRecipeDetail = { feed ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedRecipe", feed)
                                    navController.navigate(AppDestination.UserRecipeDetails.route)
                                 },
                                db = db
                            )
                        }

                        composable(AppDestination.Library.route) {
                            LibraryScreen(
                                navigateToDetail = { recipe ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedRecipe", recipe)
                                    navController.navigate(AppDestination.Details.route)
                                },
                                db = db
                            )
                        }

                        composable(AppDestination.Profile.route) {
                            ProfileScreen(navigateToLogin = { navController.navigate(AppDestination.Login.route) })
                        }

                        composable(AppDestination.Form.route) {
                            FormScreen( NavigateBack = { navController.popBackStack() } )
                        }

                        composable(AppDestination.Details.route) {
                            val recipe = navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<Recipe>("selectedRecipe")

                            if (recipe != null) {
                                DetailsScreen(
                                    recipe = recipe,
                                    NavigateBack = { navController.popBackStack() },
                                    navigateToEdit = { selectedRecipe ->
                                        navController.currentBackStackEntry?.savedStateHandle?.set("selectedRecipe", selectedRecipe)
                                        navController.navigate(AppDestination.Edit.route)
                                    }
                                )
                            } else {
                                Text("Erro: Receita não encontrada")
                            }
                        }

                        composable(AppDestination.Edit.route) {
                            val recipe = navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<Recipe>("selectedRecipe")

                            recipe?.let {
                                FormEditScreen(
                                    recipe = it,
                                    NavigateBack = { navController.popBackStack() }
                                )
                            }
                        }

                        composable(AppDestination.UserRecipeDetails.route) {
                            val feed = navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<Recipe>("selectedRecipe")

                            feed?.let {
                                UserDetailsScreen(
                                    recipe = it,
                                    NavigateBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }//_NavHost
                }// _Recipebox
            }// _RecipeBoxTheme
        }// _setContent
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeBox(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    navController: NavController,
    isShowTopBar: Boolean = false,
    isShowBottomBar: Boolean = false,
    isShowFab: Boolean = false,
    onFabClick: () -> Unit = { navController.navigate(AppDestination.Form.route) },
    auth: FirebaseAuth,
    content: @Composable () -> Unit
) {
    Scaffold (
        topBar = {
            if (isShowTopBar) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.box_fill),
                                contentDescription = "RecipeBox Logo",
                                colorFilter = if(isSystemInDarkTheme()){
                                    ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                }else{
                                    ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
                                },
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = "Recipe Box",
                                fontSize = 30.sp,
                                fontFamily = displayFontFamily,
                                color = if(isSystemInDarkTheme()){
                                   MaterialTheme.colorScheme.primary
                                }else{
                                    MaterialTheme.colorScheme.secondaryContainer
                                },
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (isShowBottomBar) {
                RecipeBoxAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                )
            }
        },
        floatingActionButton = {
            if (isShowFab) {
                FloatingActionButton(
                    onClick = onFabClick,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(18.dp, 0.dp, 18.dp, 0.dp)
                ) {
                    Icon(
                        contentDescription = "Adicionar",
                        painter = painterResource(id = R.drawable.add),
                        tint = if(isSystemInDarkTheme()){
                            MaterialTheme.colorScheme.onTertiaryContainer
                        }else{
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()

    ){ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier.padding(26.dp, 14.dp)) {
                content()
            }
        }
    }
}