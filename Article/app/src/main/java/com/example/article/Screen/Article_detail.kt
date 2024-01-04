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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.article.Screen.TopBar.TopBar
import com.example.article.Screen.dialog.CrudDialog
import com.example.article.deleteArticle
import com.example.article.updateArticle
import kotlinx.coroutines.launch
import java.lang.Exception

// Article 상세 페이지
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(articleID: String, navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()
    val article = remember {
        mutableStateOf<Article?>(null)
    }
    // 글 제목
    var titleText by remember {
        mutableStateOf("")
    }
    // 글 내용
    var contentText by remember {
        mutableStateOf("")
    }
    // 글쓴이
    var maker by remember {
        mutableStateOf("")
    }
    // 텍스트필드 활성화 / 비활성화를 위해 사용하는 변수
    var editing by remember {
        mutableStateOf(false)
    }
    // 수정 버튼이 눌렸는지 저장하는 변수
    var dialogShow by remember {
        mutableStateOf(false)
    }
    // 삭제 버튼이 눌렸는지 저장하는 변수
    var updateShow by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(articleID) {
        coroutineScope.launch {
            val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
            val response = retrofitInstance.getArticle(articleID)
            val result = response.body()
            if (result != null) {
                article.value = result
                titleText = result.title
                contentText = result.content
                maker = result.maker
                Log.d("단일article : ", article.value.toString())
            } else {
                Log.d("단일article 조회 오류", article.value.toString())
            }
        }
    }

    Scaffold(
        topBar = { TopBar("detail", navController) },
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
                        value = contentText,
                        onValueChange = {
                            contentText = it
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
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        updateShow = true
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "삭제", fontSize = 15.sp, color = Color.White)
                }
            }
        }
    }
    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    // 수정 버튼을 눌렀을 때 로직
    if (dialogShow) {
        CrudDialog(
            crud = "수정",
            onConfirm = {
                coroutineScope.launch {
                    try {
                        val updatedArticle = Article(
                            id = articleID,
                            title = titleText,
                            content = contentText,
                            maker = maker
                        )
                        // 글 수정
                        updateArticle(updatedArticle, articleID)
                    } catch (e: Exception) {
                        Log.d("업데이트 에러", e.printStackTrace().toString())
                    }
                }
                navController.navigate("mainScreen")
            },
            onDismiss = { dialogShow = false }
        )
    }
    // 삭제 버튼을 눌렀을 때 로직
    if (updateShow) {
        CrudDialog(
            crud = "삭제",
            onConfirm = {
                coroutineScope.launch {
                    try {
                        deleteArticle(articleID)
                    } catch (e: Exception) {
                        Log.d("삭제에러", e.printStackTrace().toString())
                    }
                }
                navController.navigate("mainScreen")
            },
            onDismiss = { updateShow = false },
        )
    }
}
