package com.ivos.presentation.activity.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ivos.presentation.R
import com.ivos.presentation.navigation.navScreens.BottomNavigation

@Composable
fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    items: List<BottomNavigation>,
) {
    BottomNavigation(
        modifier = modifier.navigationBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        elevation = 16.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = modifier.size(28.dp),
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = R.string.empty),
                        tint = changeSelectedColor(currentDestination?.route == screen.route)
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.label),
                        fontSize = 10.sp,
                        color = changeSelectedColor(currentDestination?.route == screen.route),
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.run {
                        if (screen.route != currentDestination?.route) {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun changeSelectedColor(
    isSelected: Boolean,
) = if (isSelected)
    MaterialTheme.colorScheme.primary
else
    MaterialTheme.colorScheme.onPrimary
