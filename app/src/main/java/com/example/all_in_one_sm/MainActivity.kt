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

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.linearLayout, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.login_and_register)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
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
