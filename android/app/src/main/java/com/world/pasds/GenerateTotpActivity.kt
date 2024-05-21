package com.world.pasds

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.world.pasds.ui.theme.PasdsworldTheme
import java.nio.ByteBuffer
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class GenerateTotpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasdsworldTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TotpScreen()
                }
            }
        }
    }

    @Composable
    fun TotpScreen() {
        var totpKey = retrieveTotpKey() // 키를 가져옵니다.
        var totpCode by remember {
            mutableStateOf(totpKey?.let {
                generateTotpCode(
                    it,
                    LocalDateTime.now()
                )
            })
        }
        var timeLeft by remember { mutableStateOf(30) }

        LaunchedEffect(key1 = totpCode) {
            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    totpKey?.let {
                        totpCode = generateTotpCode(it, LocalDateTime.now())
                    }
                    timeLeft = 30
                    this.start()  // 타이머를 다시 시작합니다.
                }
            }.start()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${timeLeft}초 안에 입력해 주세요",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF024C7D)
            )

//            Spacer(modifier = Modifier.weight(0.5f))
            if (totpCode != null) {
                Text(
                    text = totpCode!!,
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF024C7D)
                )
            } else {
                Text(
                    text = "패스키가 없습니다!!!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF024C7D)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { finish() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF024C7D)),
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "메인 페이지 가기",
                    color = Color.White,
                    fontSize = 25.sp
                )
            }
        }
    }

    fun retrieveTotpKey(): String? {
        val sharedPreferences = getSharedPreferences("totpKeyPrefs", MODE_PRIVATE)
        return sharedPreferences.getString("totpKey", null)
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

            return String.format("%06d", binary % 1000000)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error Generating Code"
        }
    }

}