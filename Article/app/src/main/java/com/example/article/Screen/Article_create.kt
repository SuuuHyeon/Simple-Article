package com.example.article.Screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.article.Article
import com.example.article.MyApi
import com.example.article.RetrofitInstance
import com.example.article.Screen.dialog.CrudDialog
import com.example.article.Screen.TopBar.TopBar
import kotlinx.coroutines.launch
import java.lang.Exception

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCreate(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()

    suspend fun createArticle(article: Article): Article? {
        val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
        val response = retrofitInstance.createArticle(article)
        return if (response.isSuccessful) response.body() else null
    }

    var titleText by remember {
        mutableStateOf("")
    }
    var titleContent by remember {
        mutableStateOf("")
    }
    var titleMaker by remember {
        mutableStateOf("")
    }

    var dialogShow by remember {
        mutableStateOf(false)
    }

    if (dialogShow) {
        CrudDialog(
            crud = "생성",
            onConfirm = {
                coroutineScope.launch {
                    try {
                        val createdArticle = Article(
                            id = "",
                            title = titleText,
                            content = titleContent,
                            maker = titleMaker
                        )
                        // 글 생성
                        createArticle(createdArticle)
                    } catch (e: Exception) {
                        Log.d("생성버튼", "발생에러 : ${e.message}")
                    }
                }
                navController.navigate("mainScreen")
            },
            onDismiss = { dialogShow = false }
        )
        //        AlertDialog(
//            onDismissRequest = { },
//            title = { Text(text = "업로드") },
//            text = { Text(text = "업로드 하시겠습니까?") },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        dialogShow = false
//                        coroutineScope.launch {
//                            try {
//                                val createdArticle = Article(
//                                    id = "",
//                                    title = titleText,
//                                    content = titleContent,
//                                    maker = titleMaker
//                                )
//                                // 글 생성
//                                createArticle(createdArticle)
//                            } catch (e: Exception) {
//                                Log.d("생성버튼", e.printStackTrace().toString())
//                            }
//                        }
//                        navController.navigate("mainScreen")
//                    }
//                ) {
//                    Text(text = "확인")
//                }
//            }
//        )
    }



    Scaffold(
        topBar = { TopBar(screen = "create", navController = navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // body
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // 제목
                Row {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                    ) {
                        Text(text = "제목")
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = titleText,
                        onValueChange = {
                            titleText = it
                        },
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                // 내용
                Row {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                    ) {
                        Text(text = "내용")
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        value = titleContent,
                        onValueChange = {
                            titleContent = it
                        },
                    )

                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Button(
                        onClick = {
                            dialogShow = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.LightGray)
                    ) {
                        Text(text = "글생성", fontSize = 15.sp, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        onClick = {
                            navController.navigate("mainScreen")
                        },
                        colors = ButtonDefaults.buttonColors(Color.LightGray)
                    ) {
                        Text(text = "게시글 리스트", fontSize = 15.sp, color = Color.Black)
                    }
                }
            }
        }
    }
}

