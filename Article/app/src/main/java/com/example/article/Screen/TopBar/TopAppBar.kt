package com.example.article.Screen.TopBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(screen: String, navController: NavHostController) {
    TopAppBar(
        title = {
            when (screen) {
                "main" -> Text(
                    text = "글 목록",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                "detail" -> Text(
                    text = "글 수정",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                "create" -> Text(
                    text = "글 생성",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                if (screen != "main")
                    navController.popBackStack()
            }) {
                if (screen == "main")
                    Icon(Icons.Default.Menu, contentDescription = "menu")
                else
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "back")
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.DarkGray)
    )
}