package com.example.article.Screen.dialog

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@SuppressLint("SuspiciousIndentation")
@Composable
fun CrudDialog(
    crud: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit

) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(text = crud)
        },
        text = { Text(text = "글을 $crud 하시겠습니까?") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(text = "확인")
            }
        }
    )
}