package ru.mirea.istornikov.mireaproject.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import net.objecthunter.exp4j.ExpressionBuilder
import ru.mirea.istornikov.mireaproject.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private lateinit var binding : FragmentCalculatorBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root
        settingNumbers(binding)
        settingOperation(binding)

        return root
    }

    fun settingNumbers(binding: FragmentCalculatorBinding){
        binding.number1.setOnClickListener(this::numberClick)
        binding.number2.setOnClickListener(this::numberClick)
        binding.number3.setOnClickListener(this::numberClick)
        binding.number4.setOnClickListener(this::numberClick)
        binding.number5.setOnClickListener(this::numberClick)
        binding.number6.setOnClickListener(this::numberClick)
        binding.number7.setOnClickListener(this::numberClick)
        binding.number8.setOnClickListener(this::numberClick)
        binding.number9.setOnClickListener(this::numberClick)
        binding.number0.setOnClickListener(this::numberClick)
    }

    fun settingOperation(binding: FragmentCalculatorBinding){
        binding.operationPlus.setOnClickListener(this::operationClick)
        binding.operationMinus.setOnClickListener(this::operationClick)
        binding.operationMultiply.setOnClickListener(this::operationClick)
        binding.operationDelit.setOnClickListener(this::operationClick)
        binding.operationPow.setOnClickListener(this::operationClick)
        binding.operationEquals.setOnClickListener(this::operationClick)
        binding.clearButton.setOnClickListener(this::operationClick)
    }

    fun operationClick(view : View){
        val inputTextView = binding.input
        val resultTextView = binding.result
        when((view as Button).text){
            "+" -> {
                inputTextView.text = inputTextView.text.toString() + view.text.toString()
            }
            "-" -> {
                inputTextView.text = inputTextView.text.toString() + view.text.toString()
            }
            "*" -> {
                inputTextView.text = inputTextView.text.toString() + view.text.toString()
            }
            "/" -> {
                inputTextView.text = inputTextView.text.toString() + view.text.toString()
            }
            "^" -> {
                inputTextView.text = inputTextView.text.toString() + view.text.toString()
            }
            "=" -> {
                resultTextView.text = "=" + ExpressionBuilder(inputTextView.text.toString()).build().evaluate().toString()
            }
            "CLEAR" -> {
                if(inputTextView.text.isNotEmpty()) {
                    inputTextView.text = inputTextView.text.toString().dropLast(1)
                    resultTextView.text = ""
                }
            }
        }
    }

    fun numberClick(view: View){
        val inputTextView = binding.input
        inputTextView.text = inputTextView.text.toString() + (view as Button).text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }
}