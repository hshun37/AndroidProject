package ko.hshun.developernews.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import ko.hshun.developernews.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val getUrl = intent.getStringExtra("url")

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
        }
        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            loadUrl(getUrl.toString())
        }
    }
}