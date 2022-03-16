package com.movie.android.ui.feature.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.movie.android.ui.feature.home.HomeScreen
import com.movie.android.R
import com.movie.android.ui.feature.home.viewmodel.HomeViewModel
import com.movie.android.ui.feature.main.viewmodel.MainViewModel
import com.movie.android.ui.theme.black

@Composable
fun HomeTabScreen(
    viewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    navController: NavController,
    tabStateHolder: HomeTabStateHolder,
    selectItem: (MainScreenHomeTab, Int) -> Unit,
    currentScreen: String?
) {

    val selectedTab by viewModel.selectedTab
    val tabs = MainScreenHomeTab.values()

    Scaffold(
        backgroundColor = black,
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                colorResource(id = R.color.black_50),
                                Color.Black
                            )
                        )
                    )
                    .fillMaxWidth()
                    .height(56.dp),
                elevation = 0.dp
            ) {

                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = tab.icon), contentDescription = null) },
                        label = { Text(text = stringResource(tab.title), color = Color.White) },
                        selected = tab == selectedTab,
                        onClick = { viewModel.selectTab(tab) },
                        selectedContentColor = colorResource(id = R.color.white),
                        unselectedContentColor = colorResource(id = R.color.white_50),
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) {

        Crossfade(selectedTab) { destination ->
            when (destination) {
                MainScreenHomeTab.TV -> HomeScreen(
                    homeViewModel,
                    tabStateHolder.homeLazyListState,
                    selectItem
                )
                /*MainScreenHomeTab.POPULAR -> PopularScreen(
                    viewModel,
                    selectItem,
                    tabStateHolder.popularLazyListState
                )
                MainScreenHomeTab.TOP_RATED -> TopRatedScreen(
                    viewModel,
                    selectItem,
                    tabStateHolder.topRatedLazyListState
                )*/
            }
        }

        MainAppBar(navController = navController, currentScreen = currentScreen)
    }
}