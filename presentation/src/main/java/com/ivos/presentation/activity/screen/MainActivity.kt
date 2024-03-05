package com.ivos.presentation.activity.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ivos.common.utils.BOTTOM_NAV_ANIM_DELAY
import com.ivos.common.utils.BOTTOM_NAV_ANIM_DURATION
import com.ivos.presentation.activity.components.MainBottomNavigation
import com.ivos.presentation.activity.components.MainTopAppBar
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeBottomNavVisibility
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeNavIconVisibility
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeTitleName
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeTopAppbarVisibility
import com.ivos.presentation.activity.viewmodel.NoNetworkViewModel
import com.ivos.presentation.base.BaseActivity
import com.ivos.presentation.navigation.buildNavOptions
import com.ivos.presentation.navigation.findRoute
import com.ivos.presentation.navigation.navCore.NavigationIntent
import com.ivos.presentation.navigation.navCore.NavigationIntent.NavigateBack
import com.ivos.presentation.navigation.navGraph.BottomScreensNav.bottomNavScreens
import com.ivos.presentation.navigation.navGraph.BottomScreensNav.bottomNavigationScreens
import com.ivos.presentation.navigation.navGraph.LaunchAppScreensNav.launchAppNavScreens
import com.ivos.presentation.navigation.navGraph.LaunchAppScreensNav.launchAppScreens
import com.ivos.presentation.navigation.navGraph.blockingNavigationScreens
import com.ivos.presentation.navigation.navGraph.blockingScreens
import com.ivos.presentation.navigation.navScreens.BlockingNavigation.NoNetworkNavScreen
import com.ivos.presentation.navigation.navScreens.BlockingNavigation.ServerNotAvailableNavScreen
import com.ivos.presentation.navigation.navScreens.BottomNavigation.LoginNavScreen
import com.ivos.presentation.navigation.navScreens.LaunchAppNavigation.SplashNavScreen
import com.ivos.presentation.ui.VKNewsLineTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun initUi() {
        setContent {
            VKNewsLineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(
                        navController = rememberNavController(),
                    )
                }
            }
        }
    }

    @Composable
    private fun MainScreen(
        modifier: Modifier = Modifier,
        navController: NavHostController,
    ) {
        val uiData by viewModel.mainActivityStateFlow.collectAsState()
        var preventBackNavigation by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                AnimatedVisibility(
                    modifier = modifier.statusBarsPadding(),
                    visible = uiData.toolbarState.topAppbarVisibility,
                ) {
                    MainTopAppBar(
                        title = uiData.toolbarState.titleName,
                        showNavigationIcon = uiData.toolbarState.navIconVisibility,
                        onClickNavIcon = {
                            if (!preventBackNavigation)
                                navController.popBackStack()
                        },
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = uiData.bottomNavState.showBottomNavigation,
                    enter = slideInVertically(initialOffsetY = { 50 }) + fadeIn(initialAlpha = 0.3f),
                    exit = fadeOut(
                        tween(
                            durationMillis = BOTTOM_NAV_ANIM_DURATION,
                            delayMillis = BOTTOM_NAV_ANIM_DELAY,
                            easing = FastOutLinearInEasing,
                        )
                    ),
                ) {
                    MainBottomNavigation(
                        navController = navController,
                        items = bottomNavScreens,
                    )
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = SplashNavScreen.route,
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                launchAppScreens()
                bottomNavigationScreens()
                blockingNavigationScreens()
            }

            BackHandler(enabled = preventBackNavigation) {
                /*ifDebug {
                    Log.d("BackHandler", "back click()")
                }*/
            }

            Navigator(navController = navController) {
                preventBackNavigation = it
            }

            addNavDestinationListener(
                navController = navController,
                changeTitle = {
                    viewModel.onTriggerEvent(ChangeTitleName(it))
                },
                changeTopAppbarVisibility = {
                    viewModel.onTriggerEvent(ChangeTopAppbarVisibility(it))
                },
                changeNavigationIconVisibility = {
                    viewModel.onTriggerEvent(ChangeNavIconVisibility(it))
                },
                changeBottomNavigationVisibility = {
                    viewModel.onTriggerEvent(ChangeBottomNavVisibility(it))
                },
            )
        }
    }

    @Composable
    private fun Navigator(
        navController: NavHostController,
        preventBackNavigation: (Boolean) -> Unit,
    ) {
        LaunchedEffect(key1 = viewModel, navController) {
            viewModel.getAppNavigatorFlow().collectLatest {
                when (it) {
                    is NavigateBack -> {
                        preventBackNavigation.invoke(it.blockBackNavigation)

                        if (it.route.isNotEmpty())
                            navController.popBackStack(route = it.route, inclusive = it.inclusive)
                        else
                            navController.popBackStack()
                    }

                    is NavigationIntent.NavigateTo -> {
                        preventBackNavigation.invoke(it.blockBackNavigation)

                        if (it.popUpToRoute.isNotEmpty()) {
                            navController.navigate(
                                route = it.route,
                                navOptions = it.buildNavOptions(),
                            )
                        } else {
                            navController.navigate(it.route)
                        }
                    }
                }
            }
        }
    }

    private fun addNavDestinationListener(
        navController: NavController,
        changeTitle: (Int) -> Unit,
        changeTopAppbarVisibility: (Boolean) -> Unit,
        changeNavigationIconVisibility: (Boolean) -> Unit,
        changeBottomNavigationVisibility: (Boolean) -> Unit,
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavScreens.find { it.route == destination.route }?.label?.let {
                changeTitle.invoke(it)
            } ?: run {
                launchAppNavScreens.findRoute(destination, changeTitle)
                //other
            }

            handleDestination(
                destination = destination,
                changeTopAppbarVisibility = changeTopAppbarVisibility,
                changeNavigationIconVisibility = changeNavigationIconVisibility,
                changeBottomNavigationVisibility = changeBottomNavigationVisibility,
            )
        }
    }

    private fun handleDestination(
        destination: NavDestination,
        changeTopAppbarVisibility: (Boolean) -> Unit,
        changeNavigationIconVisibility: (Boolean) -> Unit,
        changeBottomNavigationVisibility: (Boolean) -> Unit,
    ) {
        when (destination.route) {
            SplashNavScreen.route,
            LoginNavScreen.route,
            NoNetworkNavScreen.route,
            ServerNotAvailableNavScreen.route,
            -> {
                changeTopAppbarVisibility.invoke(false)
                changeNavigationIconVisibility.invoke(false)
                changeBottomNavigationVisibility.invoke(true)
            }

            else -> {
                changeTopAppbarVisibility.invoke(true)
                changeNavigationIconVisibility.invoke(true)
                changeBottomNavigationVisibility.invoke(true)
            }
        }
    }
}
