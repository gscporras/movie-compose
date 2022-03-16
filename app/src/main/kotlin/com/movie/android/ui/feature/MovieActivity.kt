package com.movie.android.ui.feature

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.movie.android.extensions.fromRoute
import com.movie.android.ui.feature.detail.MovieDetailViewModel
import com.movie.android.ui.feature.home.viewmodel.HomeViewModel
import com.movie.android.ui.feature.main.MainScreen
import com.movie.android.ui.feature.main.viewmodel.MainViewModel
import com.movie.android.ui.feature.search.viewmodel.SearchViewModel
import com.movie.android.ui.theme.RappiTestTheme
import com.skydoves.landscapist.coil.LocalCoilImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            CompositionLocalProvider(
                LocalCoilImageLoader provides viewModel.imageLoader,
                LocalCoilImageLoader provides homeViewModel.imageLoader,
                LocalCoilImageLoader provides searchViewModel.imageLoader
            ) {
                RappiTestTheme {
                    MainScreen(currentRoute?.fromRoute())
                }
            }
        }
    }
}