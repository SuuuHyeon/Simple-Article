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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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

    // 메인화면
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun MainScreen(navController: NavHostController) {
        // 코투린 스코프 생성
        val coroutineScope = rememberCoroutineScope()
        // 레트로핏 인스턴스 생성
        val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
        // 리스트 저장변수 생성
        val articles = remember {
            mutableStateOf(listOf<Article>())
        }

        // 코투린 시작
        coroutineScope.launch {
            // api 호출해서 값 가져오기
            val response = retrofitInstance.getArticleList()
            // 가져온 값 result에 저장
            val result = response.body()
            if (result != null) {
                // result를 articles의 value에 대입
                articles.value = result
                Log.d("API result", articles.value.toString())
            }
        }
        Scaffold(
            topBar = { TopBar() },
            floatingActionButton = {
                MyFloatingActionButton(navController)
            }
        ) { paddingValue ->
            Box(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize()
                    .background(color = Color.LightGray)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // articles.value 값들을 LazyColumn에 연결
                    items(articles.value) {
                        // ArticleForm에 대입
                        ArticleForm(it, navController)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar() {
        TopAppBar(
            title = {
                Text(text = "게시글", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            },
        )
    }

    // 게시글 Form
    @Composable
    fun ArticleForm(article: Article, navController: NavHostController) {
        // 카드 형태로 생성
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clickable {
                        // 경로 지정
                        navController.navigate("ArticleDetail/${article.id}")
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = article.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = article.content, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = article.maker, fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    @Composable
    fun MyFloatingActionButton(navController: NavHostController) {
        FloatingActionButton(
            onClick = {
                try {
                    navController.navigate("ArticleCreate")
                } catch (e: Exception) {
                    Log.d("생성페이지", e.printStackTrace().toString())
                }
            },
        ) {
            Icon(Icons.Default.Add, contentDescription = "create")
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