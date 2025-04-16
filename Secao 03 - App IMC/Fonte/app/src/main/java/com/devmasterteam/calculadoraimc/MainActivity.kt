package com.devmasterteam.calculadoraimc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val editTextWeight = findViewById<EditText>(R.id.edittext_weight)
        val seekBarHeight = findViewById<SeekBar>(R.id.seek_height)
        val textHeightValue = findViewById<TextView>(R.id.text_height_value)
        val textResult = findViewById<TextView>(R.id.text_result)
        val buttonCalculate = findViewById<Button>(R.id.button_calculate)
        val buttonClear = findViewById<Button>(R.id.button_clear)

        // Atualizar valor da altura ao mover o SeekBar
        seekBarHeight.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                textHeightValue.text = "$progress cm"
                textHeightValue.visibility = View.VISIBLE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        // Calcular IMC ao clicar no botão
        buttonCalculate.setOnClickListener {
            try {
                val weight = editTextWeight.text.toString().toDouble()
                val height = seekBarHeight.progress.toDouble() / 100

                if (height > 0) {
                    val imc = weight / (height * height)
                    textResult.text = String.format("IMC: %.2f", imc)
                    textResult.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, R.string.msg_invalid_height, Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, R.string.msg_invalid_weight, Toast.LENGTH_SHORT).show()
            }
        }

        // Limpar os campos ao clicar no botão
        buttonClear.setOnClickListener {
            editTextWeight.setText("")
            seekBarHeight.progress = 0
            textResult.text = ""
            textHeightValue.visibility = TextView.GONE
            textResult.visibility = TextView.GONE
        }
    }
}