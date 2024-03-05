package com.ivos.presentation.activity.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivos.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    showNavigationIcon: Boolean,
    onClickNavIcon: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                modifier = modifier
                    .padding(start = 16.dp),
                text = stringResource(id = title),
                fontSize = 24.sp,
            )
        },
        navigationIcon = {
            if (showNavigationIcon) {
                Icon(
                    modifier = modifier
                        .fillMaxHeight()
                        .clickable { onClickNavIcon.invoke() }
                        .padding(start = 16.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.empty),
                )
            }
        }
    )
}
