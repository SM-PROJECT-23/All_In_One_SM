package com.example.all_in_one_sm

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity

class YourArticle: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        setContentView(R.layout.yourarticle)
    }

}