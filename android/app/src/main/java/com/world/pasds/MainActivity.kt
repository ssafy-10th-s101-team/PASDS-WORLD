package com.world.pasds

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
                setPrompt("QR 코드를 스캔하세요")
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
            Toast.makeText(this, result?.contents ?: "스캔 실패", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "저장에 실패했습니다ㅜㅜ", Toast.LENGTH_SHORT).show()
        }
    }


    fun navigateToGenerateTotpActivity() {
        val intent = Intent(this, GenerateTotpActivity::class.java)
        startActivity(intent)
    }


}

@Composable
fun ButtonColumn(onQRButtonClick: () -> Unit, onGenerateTotpClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(text = "연동", onClick = onQRButtonClick)
        Spacer(modifier = Modifier.height(50.dp))  // Space between the buttons
        CustomButton(text = "코드 발급", onClick = onGenerateTotpClick)
    }
}


@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1428A0)), // 새로운 색상 코드 적용
        modifier = Modifier
            .height(80.dp)  // 버튼 높이 증가
            .fillMaxWidth()
            .padding(horizontal = 50.dp, vertical = 8.dp),  // 좌우, 상하 패딩 조정
        shape = RoundedCornerShape(12.dp)  // 버튼 모서리를 둥글게 처리
    ) {
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = androidx.compose.ui.text.TextStyle(fontSize = 30.sp)  // 글씨 크기 증가
        )
    }
}