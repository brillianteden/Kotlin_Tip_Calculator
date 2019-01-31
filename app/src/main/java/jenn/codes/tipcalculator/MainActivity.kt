// Created by Eden Mugg 01/14/2019
// This tip calculator implements a third party spinner

package jenn.codes.tipcalculator

import android.icu.text.NumberFormat
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.widget.*
import java.util.ArrayList
import com.bigkoo.pickerview.MyOptionsPickerView

class MainActivity : AppCompatActivity() {

    // gets appropriate currency for user location
    private val currencyFormat = NumberFormat.getCurrencyInstance()
    private var subTotalValue = 0F
    private var tipPercentValue = 0F
    private var tipAmount = 0F

    private lateinit var calculatorImage: ImageView
    private val drawableResource = R.drawable.calculator
    private lateinit var calculateTipButton: Button
    private lateinit var resultText: TextView
    private lateinit var subTotal: EditText
    private lateinit var spinnerText: EditText
    private var total: Float = 0F
    private var tipPercent: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculatorImage = findViewById(R.id.calculator)
        calculatorImage.setImageResource(drawableResource)
        calculateTipButton = findViewById(R.id.btnCalc)
        resultText = findViewById(R.id.txtResult)
        subTotal = findViewById(R.id.inputTxtEdit)
        spinnerText = findViewById(R.id.inputTxtEdit2)

        // third party library spinner
        val singlePicker = MyOptionsPickerView<String>(this@MainActivity)

        // options for the spinner
        val items = ArrayList<String>()
        items.add("10%")
        items.add("15%")
        items.add("18%")
        items.add("20%")
        items.add("25%")

        singlePicker.setPicker(items)
        singlePicker.setTitle("Tip Percentage")
        singlePicker.setCyclic(false)
        singlePicker.setSelectOptions(0)

        // listener recognizes when an option is selected
        singlePicker.setOnoptionsSelectListener(object : MyOptionsPickerView.OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, option2: Int, options3: Int) {

                // gets the option selected by the user
                val selectedItem = items.get(options1)

                // when statement to convert the percentage into a float
                when (selectedItem) {
                    "10%" -> tipPercent = 0.10F
                    "15%" -> tipPercent = 0.15F
                    "18%" -> tipPercent = 0.18F
                    "20%" -> tipPercent = 0.20F
                    else -> tipPercent = 0.25F
                }

                // get the subtotal provided by the user
                subTotalValue = subTotal.getText().toString().toFloat()
                tipPercentValue = tipPercent

                // calculate tip amount
                tipAmount = (tipPercentValue * subTotalValue)

                // display tip amount in location specific format for the user
                spinnerText.hint = currencyFormat.format(tipAmount).toString()

                // removing the focus from this edit text is necessary for proper
                // function of the third party spinner
                spinnerText.clearFocus()
            }
        })

        // onFocusChangeListener checks to see if the spinner has the focus
        spinnerText.setOnFocusChangeListener { view, hasFocus->

           // show the singlePicker only when it has the focus
           if(spinnerText.hasFocus())
           {
               // this line dismisses the standard keyboard to show the spinner
               spinnerText.onEditorAction(EditorInfo.IME_ACTION_DONE)

               // show the spinner
               singlePicker.show()
           }
           else
           {
               //hide spinner if it doesn't have the focus
               singlePicker.dismiss()
           }
        }

        // onClickListener recognizes when the button is pressed
        calculateTipButton.setOnClickListener {

            // calculate the total
            total = subTotalValue + (subTotalValue * tipPercentValue)

            // display the total in the user location specific currency
            resultText.setText(currencyFormat.format(total).toString())
        }
    }
}

