package com.example.article.Screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.launch
import java.lang.Exception

// Article 상세 페이지
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(articleID: String, navController: NavHostController) {

    suspend fun updateArticle(article: Article, articleID: String): Article? {
        val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
        val response = retrofitInstance.updateArticle(articleID, article)
        Log.d("articleID 값 : ", articleID)
        Log.d("response.body", response.toString())
        return if (response.isSuccessful) response.body() else null
    }

    val coroutineScope = rememberCoroutineScope()
    val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
    val article = remember {
        mutableStateOf<Article?>(null)
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

    var editing by remember {
        mutableStateOf(false)
    }

    var dialogShow by remember {
        mutableStateOf(false)
    }

    if (dialogShow) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "수정확인") },
            text = { Text(text = "수정 하시겠습니까?") },
            confirmButton = {
                Button(
                    onClick = {
                        dialogShow = false
                        coroutineScope.launch {
                            try {
                                val updatedArticle = Article(
                                    id = articleID,
                                    title = titleText,
                                    content = titleContent,
                                    maker = titleMaker
                                )
//                                        Log.d("업데이트할 게시물", updatedArticle.toString())
                                updateArticle(updatedArticle, articleID)
//                                        Log.d("수정버튼클릭", "성공")
                            } catch (e: Exception) {
                                Log.d("수정버튼클릭", "에러")
                            }
                        }
                        navController.navigate("mainScreen")
                    }
                ) {
                    Text(text = "확인")
                }
            }
        )
    }


    LaunchedEffect(articleID) {
        coroutineScope.launch {
            val response = retrofitInstance.getArticle(articleID)
            val result = response.body()
            if (result != null) {
                article.value = result
                titleText = result.title
                titleContent = result.content
                titleMaker = result.maker
                Log.d("단일article : ", article.value.toString())
            } else {
                Log.d("단일article 조회 오류", article.value.toString())
            }
        }
    }

    Scaffold(
        topBar = { TopBar(navController = navController) },
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
                        enabled = editing,
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
                        enabled = editing,
                        value = titleContent,
                        onValueChange = {
                            titleContent = it
                        },
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = "번호 : $articleID")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Button(
                        onClick = {
                            editing = !editing
                            if (!editing)
                                dialogShow = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.LightGray)
                    ) {
                        if (editing)
                            Text(text = "수정완료", fontSize = 15.sp, color = Color.Black)
                        else
                            Text(text = "수정", fontSize = 15.sp, color = Color.Black)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = "수바리 게시판") },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "뒤로가기"
                )
            }
        }
    )
}