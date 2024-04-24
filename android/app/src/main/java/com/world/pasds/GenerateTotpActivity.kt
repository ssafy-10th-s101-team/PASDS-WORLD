package com.world.pasds

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.world.pasds.ui.theme.PasdsworldTheme

class GenerateTotpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val totpCode = retrieveTotpKey() ?: "코드가 없습니다" // 키가 없을 경우 사용할 기본값

        setContent {
            PasdsworldTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "30초 안에",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Text(
                            text = "인증하세요",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = totpCode,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        Button(
                            onClick = {
                                // MainActivity로 이동하면서 이전 액티비티 스택을 클리어
                                val intent = Intent(
                                    this@GenerateTotpActivity,
                                    MainActivity::class.java
                                ).apply {
                                    flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                                startActivity(intent)
                                // 현재 액티비티 종료 (옵션)
                                finish()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1428A0)
                            ),
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "메인 페이지 가기",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }

        Toast.makeText(this, "${retrieveTotpKey()}", Toast.LENGTH_SHORT).show()

    }

    fun retrieveTotpKey(): String? {
        val sharedPreferences = getSharedPreferences("totpKeyPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("totpKey", null) // 키가 없을 경우 null 반환
    }

}