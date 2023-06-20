package com.example.all_in_one_sm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.all_in_one_sm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.home -> {

                    fragment = YourArticle.newInstance()
                }
                R.id.fav -> {

                    fragment = Item.newInstance()
                }
                R.id.add -> {

                    fragment = AddItemFragment.newInstance()
                }
            }

            if (fragment != null) {
                loadFragment(fragment)
                true
            } else {
                false
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.linearLayout, fragment)
            .commit()
    }
}
