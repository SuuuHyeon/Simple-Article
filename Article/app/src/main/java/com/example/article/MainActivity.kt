package com.example.article

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.article.Screen.ArticleCreate
import com.example.article.Screen.Detail
import com.example.article.Screen.MainScreen
import com.example.article.ui.theme.ArticleTheme

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