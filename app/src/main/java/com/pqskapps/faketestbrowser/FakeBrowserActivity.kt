package com.pqskapps.faketestbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FakeBrowserActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var addressBar: EditText
    private lateinit var goButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createView())

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        goButton.setOnClickListener {
            val url = addressBar.text.toString()
            if (url.isNotEmpty()) {
                webView.loadUrl(if (url.startsWith("http")) url else "https://$url")
            }
        }

        // Handle incoming intents
        intent?.data?.let { uri ->
            addressBar.setText(uri.toString())
            webView.loadUrl(uri.toString())
        }
    }

    // Programmatically create the UI
    private fun createView(): View {
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
        }

        addressBar = EditText(this).apply {
            hint = "Enter URL"
            setText("https://example.com") // Default URL
        }
        goButton = Button(this).apply {
            text = "Go"
        }
        webView = WebView(this)

        layout.addView(addressBar)
        layout.addView(goButton)
        layout.addView(webView)
        return layout
    }
}