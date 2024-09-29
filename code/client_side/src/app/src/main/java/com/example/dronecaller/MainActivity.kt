package com.example.dronecaller

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.Context
import android.os.Bundle
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dronecaller.ui.theme.DroneCallerTheme
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ChainStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroneCallerTheme {
                //MainContent()
                LocalHtmlPage("file:///android_asset/index.html")
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    Scaffold(
        content = {WebContent1()}
    )
}

@Composable
fun NativeContent() {
    
    var longtitude = 0.0
    var latitude = 0.0

    Row {
        Button(onClick = {},
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff9d242b),
                contentColor = Color(0xffeedbdc)
            )) {
            Text("SOS", fontSize = 28.sp)
        }
    }
}

//class WebAppInterface(private val mContext: Context) {
//    @JavascriptInterface
//    fun showToast(toast: String) {
//        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
//    }
//}
//
//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun LocalHtmlPage(url: String){
//    AndroidView(factory = {
//        WebView(it).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            webViewClient = WebViewClient()
//            settings.javaScriptEnabled = true
//            addJavascriptInterface(WebAppInterface(getContext()), "Android")
//            loadUrl(url)}
//    }, update = {it.loadUrl(url)})
//}
//
//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun WebContent1() {
//    var sos_clicked = false
//    val url = remember { mutableStateOf("google.com") }
//    val new_url = remember { mutableStateOf(url.value) }
//    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
//        TextField(
//            value = new_url.value,
//            textStyle = TextStyle(fontSize = 28.sp),
//            onValueChange = {inpt_url -> new_url.value = inpt_url}
//        )
//
//        Button(onClick = {url.value = new_url.value},
//            shape = RoundedCornerShape(15.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xff9d242b),
//                contentColor = Color(0xffeedbdc)
//            )
//        ) {Text("Open url", fontSize = 28.sp)}
//
//        AndroidView(factory = {
//            WebView(it).apply {
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                webViewClient = WebViewClient()
//                settings.javaScriptEnabled = true
//                addJavascriptInterface(WebAppInterface(getContext()), "Android")
//                loadUrl(url.value)}
//        }, update = {
//            it.loadUrl(url.value)
//        })
//    }
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DroneCallerTheme {
        MainContent()
    }
}