package com.example.all_in_one_sm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YourArticle.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemFragment : Fragment() {

    private lateinit var save1: Button
    private lateinit var cancel1: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.additem, container, false)
        save1 = view.findViewById(R.id.savebuttonAddPage1)
        cancel1 = view.findViewById(R.id.cancelbuttonAddPage1)
        save1.setOnClickListener {
            navigateToYourItemPage()
        }
        cancel1.setOnClickListener {
            navigateToYourArticlePage()
        }
        return view
    }

    companion object {
        fun newInstance() = AddItemFragment()
    }

    private fun navigateToYourItemPage() {
        val intent = Intent(requireContext(), YourItemPage::class.java)
        startActivity(intent)
    }

    private fun navigateToYourArticlePage() {
        val intent = Intent(requireContext(), YourArticle::class.java)
        startActivity(intent)
    }




}