package com.learning.aboutme

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.learning.aboutme.databinding.ActivityMainBinding

// solution code since my code is a bit different because of the challenge
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myName: MyName = MyName("Aleks Haecky")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.myName = myName

        binding.doneButton.setOnClickListener {
            addNickname(it)
        }

    }

    /**
     * Click handler for the Done button.
     * Demonstrates how data binding can be used to make code much more readable
     * by eliminating calls to findViewById and changing data in the binding object.
     */
    private fun addNickname(view: View) {
        binding. apply {
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }

        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

// my old code as a reference

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val myName: MyName = MyName("Aleks Haecky") // reference to the kotlin class
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.myName = myName
//
//        //replaced the findviewbyid with a binding
//        /*findViewById<Button>(R.id.done_button).setOnClickListener{
//            //it refers tro the view that has the click listener set on
//            doneClick(it)
//
//        }*/
//
//        /*findViewById<TextView>(R.id.nickname_text).setOnClickListener {
//            updateNickname(it)
//        }*/
//
//        binding.doneButton.setOnClickListener{
//            doneClick()
//        }
//
//        binding.nicknameText.setOnClickListener{
//            updateNickname()
//        }
//    }
//
//    private fun doneClick(){
//        val editText = binding.nicknameEdit
//        val nicknameTextView = binding.nicknameText
//
//        nicknameTextView.text = editText.text.toString()
//        nicknameTextView.visibility = View.VISIBLE
//
//        // Hide the done button and the edit textbox when we are done
//        binding.doneButton.visibility = View.GONE
//        editText.visibility = View.GONE
//
//        // Hide the keyboard.
//        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(binding.doneButton.windowToken, 0)
//
//    }
//
//    private fun updateNickname () {
//        val editText = binding.nicknameEdit
//        val doneButton = binding.doneButton
//
//        editText.visibility = View.VISIBLE
//        doneButton.visibility = View.VISIBLE
//        binding.nicknameText.visibility = View.GONE
//
//        // Set the focus to the edit text.
//        editText.requestFocus()
//
//        // Show the keyboard.
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(editText, 0)
//    }
//}
