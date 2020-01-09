package com.learning.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }

        val countButton: Button = findViewById(R.id.count_button)
        countButton.setOnClickListener { countUp() }
    }

    private fun rollDice(){
        //Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        val randomInt = Random().nextInt(6) + 1

        val resultText: TextView = findViewById(R.id.result_text)
        resultText.text = randomInt.toString()
    }

    private fun countUp(){
        val resultText: TextView = findViewById(R.id.result_text)
        val result: String = resultText.text.toString()

        if(result.toIntOrNull() != null && result.toInt() < 6){
            resultText.text = (result.toInt() + 1).toString()
        }
    }
}
