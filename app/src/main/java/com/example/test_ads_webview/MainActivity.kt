package com.example.test_ads_webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var navigator: CustomNavigator = CustomNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        navigator.bind(supportFragmentManager)
    }

    override fun onDestroy() {
        CustomNavigator.unbind()
        super.onDestroy()
    }
}
