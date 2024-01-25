package com.example.taxast_taxi.home

import Travel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taxast_taxi.R
import com.example.taxast_taxi.account.Account
import com.example.taxast_taxi.databinding.ActivityRidetaxiBinding
import com.example.taxast_taxi.history.History
import com.example.taxast_taxi.menu.Favourite
import com.example.taxast_taxi.menu.Setting
import com.example.taxast_taxi.util.Utils
import java.util.Calendar
import java.util.Locale

class Ridetaxi : AppCompatActivity() {

    private lateinit var binding: ActivityRidetaxiBinding
    private var originOption: String = ""
    private var destiOption: String = ""
    private var roundTrip: Boolean = false
    private var numberPass: String = ""
    private var pickDate: String = ""
    private var pickTime: String = ""
    private var returnDate: String = ""
    private var returnTime: String = ""

    private val place = arrayOf("Madrid", "Santander", "Santiago")
    private val passengerOptions = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRidetaxiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupBottomNavigationView()

        val name = intent.getStringExtra("name")
        val dni = intent.getStringExtra("dni")
        val pickd = binding.pickdirection.text.toString()

        setupPlaceOptions()
        setupListeners()
        setupPassengerOptions()
        setupDateTimePickers()

        if (name != null && dni != null) {
            clickConfirm(name, dni, pickd)
        }
    }

    private fun clickConfirm(name: String, dni: String, pickd: String) {
        var travel: Travel
        binding.confirm.setOnClickListener {
            if (setupTextWatchers() && checkNotEmpty()) {
                if (originOption != destiOption) {
                    if (roundTrip) {
                        travel = Travel(
                            name,
                            dni,
                            roundTrip,
                            originOption,
                            destiOption,
                            numberPass,
                            pickDate,
                            pickTime,
                            returnDate,
                            returnTime
                        )
                        val intent = Intent(this, Confirmpayment::class.java)
                        intent.putExtra("travel", travel)
                        intent.putExtra("pickd", pickd)
                        startActivity(intent)
                    } else {
                        travel = Travel(
                            name,
                            dni,
                            roundTrip,
                            originOption,
                            destiOption,
                            numberPass,
                            pickDate,
                            pickTime,
                            "",
                            ""
                        )
                        val intent = Intent(this, Confirmpayment::class.java)
                        intent.putExtra("travel", travel)
                        intent.putExtra("pickd", pickd)
                        startActivity(intent)
                    }
                } else {
                    Utils.showToast(this, "Origin and destination must be different")
                }
            } else {
                Utils.showToast(this, "Error filling in fields")
            }
        }
    }

    private fun checkNotEmpty(): Boolean {
        var valid = false
        val origin = binding.origin.text.toString()
        val destination = binding.destination.text.toString()
        val passenger = binding.passenger.text.toString()
        val pickdate = binding.pickdate.text.toString()
        val picktime = binding.picktime.text.toString()

        if (origin.isEmpty() || destination.isEmpty() || passenger.isEmpty() || pickdate.isEmpty() || picktime.isEmpty()) {
            Utils.showToast(this, "Please complete all required fields.")
        } else if (binding.goback.isChecked) {
            val returndate = binding.returndate.text.toString()
            val backtime = binding.backtime.text.toString()

            if (returndate.isEmpty() || backtime.isEmpty()) {
                Utils.showToast(this, "Please complete the return date and return time.")
            } else {
                valid = true
            }
        } else {
            valid = true
        }

        return valid
    }

    private fun setupTextWatchers(): Boolean {
        var isInputValid = true
        val textWatchers = listOf(
            Pair(binding.passenger, passengerOptions),
            Pair(binding.origin, place),
            Pair(binding.destination, place)
        )

        textWatchers.forEach { (editText, validOptions) ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val userInput = s.toString().trim()
                    if (userInput.isNotEmpty() && validOptions.none { it == userInput }) {
                        editText.error = "Invalid content"
                        isInputValid = false
                    } else {
                        editText.error = null
                    }
                }
            })
        }
        return isInputValid
    }

    private fun setupPlaceOptions() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, place)
        binding.origin.setAdapter(adapter)
        binding.origin.setOnItemClickListener { _, _, position, _ ->
            originOption = place[position]
            handleElementAvailability(originOption, destiOption)
        }

        binding.origin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.origin.showDropDown()
            }
        }

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, place)
        binding.destination.setAdapter(adapter2)
        binding.destination.setOnItemClickListener { _, _, position, _ ->
            destiOption = place[position]
            handleElementAvailability(originOption, destiOption)
        }

        binding.destination.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.destination.showDropDown()
            }
        }
    }

    private fun handleElementAvailability(origin: String, destination: String) {
        if (origin == destination) {
            Utils.showToast(this, "The places of origin and destination must be different")
        }
    }

    private fun setupListeners() {
        invisibleElements()
        binding.goback.setOnClickListener {
            visibleElements()
            roundTrip = true
        }

        binding.oneway.setOnClickListener {
            invisibleElements()
            roundTrip = false
        }
    }

    private fun invisibleElements() {
        binding.returndate.visibility = View.INVISIBLE
        binding.backtime.visibility = View.INVISIBLE
        binding.backdate.visibility = View.INVISIBLE
        binding.backtimeImage.visibility = View.INVISIBLE
        binding.returndate.isEnabled = false
        binding.backtime.isEnabled = false
    }

    private fun visibleElements() {
        binding.returndate.visibility = View.VISIBLE
        binding.backtime.visibility = View.VISIBLE
        binding.backdate.visibility = View.VISIBLE
        binding.backtimeImage.visibility = View.VISIBLE
        binding.returndate.isEnabled = true
        binding.backtime.isEnabled = true
    }

    private fun setupPassengerOptions() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, passengerOptions)
        binding.passenger.setAdapter(adapter)
        binding.passenger.setOnItemClickListener { _, _, position, _ ->
            numberPass = passengerOptions[position]
        }

        binding.passenger.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passenger.showDropDown()
            }
        }
    }

    private fun setupDateTimePickers() {
        val calendar = Calendar.getInstance()
        val currentTime = Calendar.getInstance().apply { add(Calendar.MINUTE, 30) }
        val pickDatePickerDialog =
            createDatePickerDialog(calendar) { selectedYear, selectedMonth, selectedDay ->
                pickDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.pickdate.setText(pickDate)
            }

        val returnDatePickerDialog =
            createDatePickerDialog(calendar) { selectedYear, selectedMonth, selectedDay ->
                val returnDateText = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                if (isReturnDateValid(returnDateText)) {
                    returnDate = returnDateText
                    binding.returndate.setText(returnDate)
                } else {
                    Utils.showToast(this, "Return date cannot be before pick-up date")
                }
            }

        val pickTimePickerDialog =
            createTimePickerDialog(currentTime) { selectedHour, selectedMinute ->
                handleTimeSelection(binding.picktime, selectedHour, selectedMinute)
            }

        val returnTimePickerDialog =
            createTimePickerDialog(currentTime) { selectedHour, selectedMinute ->
                handleTimeSelection(binding.backtime, selectedHour, selectedMinute)
            }

        binding.pickdate.setOnClickListener {
            pickDatePickerDialog.show()
        }

        binding.returndate.setOnClickListener {
            returnDatePickerDialog.show()
        }

        binding.picktime.setOnClickListener {
            pickTimePickerDialog.show()
        }

        binding.backtime.setOnClickListener {
            returnTimePickerDialog.show()
        }
    }

    private fun isReturnDateValid(returnDateText: String): Boolean {
        val pickDateText = binding.pickdate.text.toString()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val pickDate = dateFormat.parse(pickDateText)
            val returnDate = dateFormat.parse(returnDateText)

            // Verifica que la fecha de recogida no sea posterior a la fecha de regreso
            return !pickDate.after(returnDate)
        } catch (e: ParseException) {
            return false
        }
    }

    private fun createDatePickerDialog(
        calendar: Calendar,
        onDateSetListener: (Int, Int, Int) -> Unit
    ): DatePickerDialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                onDateSetListener(selectedYear, selectedMonth, selectedDay)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()

        return datePickerDialog
    }

    private fun createTimePickerDialog(
        currentTime: Calendar,
        onTimeSetListener: (Int, Int) -> Unit
    ): TimePickerDialog {
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        return TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                onTimeSetListener(selectedHour, selectedMinute)
            },
            hour,
            minute,
            false
        )
    }

    private fun handleTimeSelection(textView: TextView, selectedHour: Int, selectedMinute: Int) {
        val selectedTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, selectedHour)
            set(Calendar.MINUTE, selectedMinute)
        }

        val currentTimeInMillis = Calendar.getInstance().timeInMillis

        if (textView.id == R.id.picktime) {
            pickTime = "$selectedHour:$selectedMinute"
            textView.text = pickTime
            if (pickDate == returnDate && isReturnTimeBeforePickTime(pickTime, returnTime)) {
                Utils.showToast(this, "Return time cannot be earlier than pick-up time")
            }
        } else if (textView.id == R.id.backtime) {
            returnTime = "$selectedHour:$selectedMinute"
            textView.text = returnTime
            if (pickDate == returnDate && isReturnTimeBeforePickTime(pickTime, returnTime)) {
                Utils.showToast(this, "Return time cannot be earlier than pick-up time")
            }
        }
    }

    private fun isReturnTimeBeforePickTime(pickTime: String, returnTime: String): Boolean {
        val pickTimeParts = pickTime.split(":")
        val returnTimeParts = returnTime.split(":")

        val pickHour = pickTimeParts[0].toInt()
        val pickMinute = pickTimeParts[1].toInt()

        val returnHour = returnTimeParts[0].toInt()
        val returnMinute = returnTimeParts[1].toInt()

        return (returnHour < pickHour) || (returnHour == pickHour && returnMinute < pickMinute)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, Favourite::class.java))
                return true
            }

            R.id.action_settings -> {
                startActivity(Intent(this, Setting::class.java))

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView = binding.menu1

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // You are already in the Home activity, so no need to navigate
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.history -> {
                    // Navigate to the HistoryActivity
                    startActivity(Intent(this, History::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.account -> {
                    // Navigate to the AccountActivity
                    startActivity(Intent(this, Account::class.java))
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }
}

