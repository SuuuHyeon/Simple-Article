package com.example.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.article.Screen.ArticleCreate
import com.example.article.Screen.Detail
import com.example.article.Screen.MainScreen
import com.example.article.Screen.TopBar.TopBar
import com.example.article.ui.theme.ArticleTheme
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArticleTheme {
                MyNav()
            }
        }
    }
    // 네비게이션
    @Composable
    fun MyNav() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "mainScreen") {
            composable("mainScreen") { MainScreen(navController = navController) }
            composable("ArticleCreate") { ArticleCreate(navController = navController) }
            composable(
                route = "ArticleDetail/{articleID}",
                arguments = listOf(navArgument("articleID") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val articleID = backStackEntry.arguments?.getString("articleID")
                Log.d("articleID : ", articleID.toString())
                articleID?.let {
                    Detail(articleID = it, navController)
                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ArticleTheme {
//        MyNav()
//    }
//}