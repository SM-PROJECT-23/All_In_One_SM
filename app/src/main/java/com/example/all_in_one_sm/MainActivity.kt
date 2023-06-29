package com.example.all_in_one_sm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.all_in_one_sm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun loadFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.login_and_register)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            var containerId: Int = -1 // Initialize with an invalid container ID

            when (item.itemId) {
                R.id.add -> {
                    fragment = AddItemFragment.newInstance()
                    containerId = R.id.linearLayout // Update with the correct container ID for AddItemFragment
                }
                // Add cases for other menu items and assign corresponding fragments and container IDs
                // case R.id.item2 -> { fragment = Fragment2.newInstance(); containerId = R.id.container2 }
                // case R.id.item3 -> { fragment = Fragment3.newInstance(); containerId = R.id.container3 }
                // case R.id.item4 -> { fragment = Fragment4.newInstance(); containerId = R.id.container4 }
            }

            if (fragment != null && containerId != -1) {
                loadFragment(fragment, containerId) // Pass the fragment and container ID to the loadFragment function
                true
            } else {
                false
            }
        }
        val clickLoginPage = findViewById<Button>(R.id.loginButton)
        clickLoginPage.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        val clickRegisterPage = findViewById<Button>(R.id.registoB)
        clickRegisterPage.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

       /* val clickEditPage = findViewById<Button>(R.id.EditButton)
        clickEditPage.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        val clickArticlePage = findViewById<Button>(R.id.Article)
        clickArticlePage.setOnClickListener {
            val intent = Intent(this, ArticlesList::class.java)
            startActivity(intent)
        }
        val clickYourArticlePage = findViewById<Button>(R.id.YourArticle)
        clickYourArticlePage.setOnClickListener {
            val intent = Intent(this, YourArticle::class.java)
            startActivity(intent)
        }
        val clickYourArticleItemPage = findViewById<Button>(R.id.ArticleItem)
        clickYourArticleItemPage.setOnClickListener {
            val intent = Intent(this, ArticlesItem::class.java)
            startActivity(intent)
        }*/
    }
}