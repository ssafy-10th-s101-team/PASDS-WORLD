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
import android.util.Base64
import java.nio.ByteBuffer
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class GenerateTotpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val totpKey = retrieveTotpKey() ?: "코드가 없습니다"
        val currentDateTime = LocalDateTime.now()
        val totpCode = if (totpKey != "코드가 없습니다") {
            generateTotpCode(totpKey, currentDateTime)
        } else {
            totpKey
        }

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

    fun generateTotpCode(totpKey: String, serverTime: LocalDateTime): String {
        try {
            val time = serverTime.toEpochSecond(ZoneOffset.UTC) / 30
            val timeData = ByteBuffer.allocate(8).putLong(time).array()

            val secretKey = SecretKeySpec(Base64.decode(totpKey, Base64.DEFAULT), "HmacSHA256")
            val mac = Mac.getInstance("HmacSHA256")
            mac.init(secretKey)
            val hash = mac.doFinal(timeData)

            val offset = hash.last().toInt() and 0xf
            val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                    ((hash[offset + 1].toInt() and 0xff) shl 16) or
                    ((hash[offset + 2].toInt() and 0xff) shl 8) or
                    (hash[offset + 3].toInt() and 0xff)

            val otp = binary % 1000000
            return String.format("%06d", otp)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("HmacSHA256 algorithm not supported", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Invalid key exception", e)
        }
    }

}