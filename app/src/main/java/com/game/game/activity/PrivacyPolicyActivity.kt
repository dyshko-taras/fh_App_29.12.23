package com.game.game.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar
import com.game.game.R
import com.game.game.data.SharedPreferencesGame

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        initViews()
        setSettings()
        webView.loadUrl(resources.getString(R.string.url))

    }

    //launch activity fun
    companion object {
        fun launch(activity: AppCompatActivity) {
            val intent = Intent(activity, PrivacyPolicyActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        webView = findViewById(R.id.webView)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        if (SharedPreferencesGame.isFirstLaunch(this)) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                SharedPreferencesGame.setFirstLaunch(this@PrivacyPolicyActivity, false)
                LoadActivity2.launch(this)
            }
        } else {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                super.onBackPressed()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setSettings() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            useWideViewPort = true
            displayZoomControls = false
            builtInZoomControls = true
            loadsImagesAutomatically = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            allowFileAccessFromFileURLs = true
        }
    }
}