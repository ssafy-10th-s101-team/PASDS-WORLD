package com.world.pasds

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import com.world.pasds.ui.theme.PasdsworldTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val REQUEST_CAMERA_PERMISSION = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasdsworldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ButtonColumn(
                        onQRButtonClick = { startQRCodeScanner() },
                        onGenerateTotpClick = { navigateToGenerateTotpActivity() }
                    )
                }
            }
        }
    }

    fun startQRCodeScanner() {
        if (hasCameraPermission()) {
            IntentIntegrator(this).apply {
                captureActivity = CaptureActivity::class.java
                setOrientationLocked(false)
                setBeepEnabled(true)
                setPrompt("패스키를 스캔하세요")
            }.initiateScan() // initiateScan으로 시작
        } else {
            requestCameraPermission()
        }
    }

    // In your MainActivity:
    private fun hasCameraPermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    // Override onRequestPermissionsResult to handle the permission result:
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start QR scanner
                startQRCodeScanner()
            } else {
                // Permission denied, handle as appropriate
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
//            Toast.makeText(this, "스캔됨: ${result.contents}", Toast.LENGTH_SHORT).show()
            saveQRCodeResult(result.contents) // 스캔 결과를 저장
        } else {
            Toast.makeText(this, result?.contents ?: "패스키 스캔 실패", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveQRCodeResult(result: String) {
//        Toast.makeText(this, "result: String = $result", Toast.LENGTH_SHORT).show()

        // 바이트 배열을 Base64 인코딩된 문자열로 재변환하지 않고 바로 저장
        val sharedPreferences = getSharedPreferences("totpKeyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("totpKey", result)

        val wasSuccessful = editor.commit() // 데이터를 동기적으로 저장합니다.

        if (wasSuccessful) {
            Toast.makeText(this, "패스키 저장되었습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "패스키 저장에 실패했습니다ㅜㅜ", Toast.LENGTH_SHORT).show()
        }
    }


    fun navigateToGenerateTotpActivity() {
        val intent = Intent(this, GenerateTotpActivity::class.java)
        startActivity(intent)
    }


}

@Composable
fun ImageButtonWithText(
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Icon Button",
            modifier = Modifier
                .size(200.dp)  // 이미지 사이즈 조절
                .padding(8.dp)  // 이미지 주변의 패딩
                .border(2.dp, Color(0xFF024C7D), RoundedCornerShape(10.dp))  // 테두리 추가
        )
        Text(
            text = text,
            fontSize = 35.sp,  // 글씨 크기 조절
            color = Color(0xFF024C7D)  // 글씨 색상 조정
        )
    }
}

// ButtonColumn 수정
@Composable
fun ButtonColumn(onQRButtonClick: () -> Unit, onGenerateTotpClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageButtonWithText(
            icon = R.drawable.qr_code_scan,  // QR 코드 스캔 이미지 리소스
            text = "패스키 스캔",
            onClick = onQRButtonClick
        )
        Spacer(modifier = Modifier.height(32.dp))  // 버튼 사이의 공간
        ImageButtonWithText(
            icon = R.drawable.two_factor_authentication,  // TOTP 코드 발급 이미지 리소스
            text = "패스 코드 발급",
            onClick = onGenerateTotpClick
        )
    }
}