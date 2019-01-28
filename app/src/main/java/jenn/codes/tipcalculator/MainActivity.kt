package jenn.codes.tipcalculator

import android.icu.text.NumberFormat
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v13.view.inputmethod.EditorInfoCompat
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList
import com.bigkoo.pickerview.MyOptionsPickerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private val currencyFormat = NumberFormat.getCurrencyInstance()
    private var subTotalValue = 0F
    private var totalValue = 0F
    private var tipPercentValue = 0F

    private lateinit var calculateTipButton: Button
    private lateinit var resultText: TextView
    private lateinit var subTotal: EditText
    private lateinit var spinnerText: EditText
    private var total: Float = 0F
    private var tipPercent: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculateTipButton = findViewById(R.id.btnCalc)
        resultText = findViewById(R.id.txtResult)
        subTotal = findViewById(R.id.inputTxtEdit)
        spinnerText = findViewById(R.id.inputTxtEdit2)
        val singlePicker = MyOptionsPickerView<String>(this@MainActivity)
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
        singlePicker.setOnoptionsSelectListener(object : MyOptionsPickerView.OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int) {
                //  singleTVOptions.setText("Single Picker " + items.get(options1));
                Toast.makeText(this@MainActivity, "" + items[options1], Toast.LENGTH_SHORT).show()
                //vMasker.setVisibility(View.GONE)
                val selectedItem = items.get(options1)
                when (selectedItem) {
                    "10%" -> tipPercent = 0.10F
                    "15%" -> tipPercent = 0.15F
                    "18%" -> tipPercent = 0.18F
                    "20%" -> tipPercent = 0.20F
                    else -> tipPercent = 0.25F
                }
                //do a settext on your edit text
                this@MainActivity.tipPercentValue = tipPercent
                Toast.makeText(this@MainActivity, tipPercent.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        spinnerText.setOnClickListener {
            println("XYZ Click Button")
           // spinnerText.requestFocus()
            spinnerText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            singlePicker.show()

        }
        //editText.setOnFocusChangeListener { view, b -> doSomething(b) }

       spinnerText.setOnFocusChangeListener { view, hasFocus->


           println("XYZFocus Changed")
           if(hasFocus)
           {
               println("XYZhas focus")
               spinnerText.onEditorAction(EditorInfo.IME_ACTION_DONE)
               singlePicker.show()
               //show spinner thing
           }
           else
           {
               println("XYZleav focus")
               //hide spinner


           }
       }







        calculateTipButton.setOnClickListener {

            subTotalValue = subTotal.getText().toString().toFloat()

            total = subTotalValue + (subTotalValue * tipPercentValue)
            println(total.toString())

            resultText.setText(currencyFormat.format(total).toString())

            // Hide the keyboard.
            //val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


    }

    }

