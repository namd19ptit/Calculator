package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.calculator.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

private lateinit var binding:ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonClear.setOnClickListener {
            binding.input.text=" "
            binding.output.text=" "
        }


        binding.buttonPercent.setOnClickListener {
            binding.input.text = addToInputText("%")
        }
        binding.button0.setOnClickListener {
            binding.input.text = addToInputText("0")
        }
        binding.button1.setOnClickListener {
            binding.input.text = addToInputText("1")
        }
        binding.button2.setOnClickListener {
            binding.input.text = addToInputText("2")
        }
        binding.button3.setOnClickListener {
            binding.input.text = addToInputText("3")
        }
        binding.button4.setOnClickListener {
            binding.input.text = addToInputText("4")
        }
        binding.button5.setOnClickListener {
            binding.input.text = addToInputText("5")
        }
        binding.button6.setOnClickListener {
            binding.input.text = addToInputText("6")
        }
        binding.button7.setOnClickListener {
            binding.input.text = addToInputText("7")
        }
        binding.button8.setOnClickListener {
            binding.input.text = addToInputText("8")
        }
        binding.button9.setOnClickListener {
            binding.input.text = addToInputText("9")
        }
        binding.buttonDot.setOnClickListener {
            binding.input.text = addToInputText(".")
        }
        binding.buttonAddition.setOnClickListener {
            binding.input.text = addToInputText("+")
        }
        binding.buttonSubtraction.setOnClickListener {
            binding.input.text = addToInputText("-")
        }
        binding.buttonMultiply.setOnClickListener {
            binding.input.text = addToInputText("×")
        }
        binding.buttonDivision.setOnClickListener {
            binding.input.text = addToInputText("÷")
        }
        binding.equals.setOnClickListener {
            showResult()
        }
        binding.buttonBack.setOnClickListener {
            val lastIndex = binding.input.text.toString().lastIndex
            var str:String = binding.input.text.substring(0,lastIndex)
            binding.input.text = str
        }

        binding.buttonSwap.setOnClickListener {
            swapNumbers()
        }

    }
    private fun addToInputText(buttonValue:String): String{
        return "${binding.input.text}$buttonValue"
    }

    private fun getInputExpression(): String{
        var expression = binding.input.text.replace(Regex("÷"),"/")
        expression= expression.replace(Regex("×"),"*")
        return expression
    }

    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                // Show Error Message
                binding.output.text = "Error"
                binding.output.setTextColor(ContextCompat.getColor(this, R.color.red))
            } else {
                // Show Result
                binding.output.text = DecimalFormat("0.######").format(result).toString()
                binding.output.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        } catch (e: Exception) {
            // Show Error Message
            binding.output.text = "Error"
            binding.output.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }
    private fun swapNumbers() {
        val inputText = binding.input.text.toString()

        // Tìm vị trí của phép toán trong chuỗi
        val operatorIndex = inputText.indexOfAny(charArrayOf('+', '-', '×', '÷'))

        // Nếu không tìm thấy phép toán hoặc chỉ có một số
        if (operatorIndex == -1 || operatorIndex == 0 || operatorIndex == inputText.length - 1) {
            return // Không thực hiện đảo chỗ
        }

        // Tách chuỗi thành hai phần: trước và sau phép toán
        val firstPart = inputText.substring(0, operatorIndex).trim()
        val secondPart = inputText.substring(operatorIndex + 1).trim()

        // Lấy phép toán
        val operator = inputText[operatorIndex]

        // Đảo chỗ hai số
        val swappedText = "$secondPart $operator $firstPart"

        // Cập nhật lại trường nhập liệu
        binding.input.text = swappedText
    }
}