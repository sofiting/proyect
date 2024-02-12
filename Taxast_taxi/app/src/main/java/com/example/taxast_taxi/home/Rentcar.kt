package com.example.taxast_taxi.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taxast_taxi.R
import com.example.taxast_taxi.RentActivity
import com.example.taxast_taxi.account.Account
import com.example.taxast_taxi.databinding.ActivityRentcarBinding
import com.example.taxast_taxi.history.History
import com.example.taxast_taxi.menu.Favourite
import com.example.taxast_taxi.menu.Setting
import com.example.taxast_taxi.util.Utils
import java.util.Calendar
import java.util.Locale

class Rentcar : AppCompatActivity() {

    private lateinit var binding: ActivityRentcarBinding
    private var selectedDate: String = ""
    private var pickTime: String = ""
    private var returnDate: String = ""
    private var agePrice: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentcarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding = ActivityRentcarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupBottomNavigationView()

        val name = intent.getStringExtra("name")
        val dni = intent.getStringExtra("dni")
        Log.d("sofia", name.toString())
        Log.d("sofia", dni.toString())

        setupDateTimePickers()
        radioPrice()

        if (name != null && dni != null) {

            submitOption(name.toString(), dni.toString())

        }

    }

    private fun submitOption(name: String, dni: String) {
        binding.submit.setOnClickListener {
            if (checkNotEmptyR() && isReturnDateValid(returnDate)) {
                Log.d("sofia", "entra dos check")
                val rent = RentActivity(
                    name,
                    dni,
                    binding.picklocation.text.toString(),
                    selectedDate,
                    pickTime,
                    returnDate,
                    agePrice
                )
                val intent = Intent(this, ConfirmRent::class.java)
                intent.putExtra("rent", rent)
                intent.putExtra("name", name)
                intent.putExtra("dni", dni)
                startActivity(intent)
            } else {
                // Display an error message when the return date is invalid
                Utils.showToast(
                    this,
                    "Invalid return date. Please make sure it's not earlier than the pick-up date."
                )
            }
        }
    }


    private fun checkNotEmptyR(): Boolean {
        var valid = false

        val location = binding.picklocation.text.toString()
        val sDate = binding.pdate.text.toString()
        val rDate = binding.returnDateR.text.toString()
        val ptime = binding.ptime.text.toString()

        if (location.isEmpty() || sDate.isEmpty() || rDate.isEmpty() || ptime.isEmpty()) {
            Utils.showToast(this, "Please complete all required fields.")
        } else if (!binding.g1.isChecked && !binding.g2.isChecked && !binding.g3.isChecked) {
            Utils.showToast(this, "Please select a radio button.")
        } else {
            valid = true
        }

        return valid
    }

    private fun radioPrice() {
        binding.g1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                agePrice = "350"
            }
        }

        binding.g2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                agePrice = "200"
            }
        }

        binding.g3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                agePrice = "300"
            }
        }
    }

    private fun setupDateTimePickers() {
        val calendar = Calendar.getInstance()
        val currentTime = Calendar.getInstance().apply { add(Calendar.MINUTE, 30) }
        val pickDatePickerDialog =
            createDatePickerDialog(calendar) { selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.pdate.setText(selectedDate)
            }

        val returnDatePickerDialog =
            createDatePickerDialog(calendar) { selectedYear, selectedMonth, selectedDay ->
                val returnDateText = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                if (isReturnDateValid(returnDateText)) {
                    returnDate = returnDateText
                    binding.returnDateR.setText(returnDate)
                } else {
                    Utils.showToast(this, "Return date cannot be earlier than pick-up date")
                }
            }

        val pickTimePickerDialog =
            createTimePickerDialog(currentTime) { selectedHour, selectedMinute ->
                handleTimeSelection(binding.ptime, selectedHour, selectedMinute)
            }

        val returnTimePickerDialog =
            createTimePickerDialog(currentTime) { selectedHour, selectedMinute ->
                handleTimeSelection(binding.returnDateR, selectedHour, selectedMinute)
            }

        binding.pdate.setOnClickListener {
            pickDatePickerDialog.show()
        }

        binding.returnDateR.setOnClickListener {
            returnDatePickerDialog.show()
        }

        binding.ptime.setOnClickListener {
            pickTimePickerDialog.show()
        }
    }

    private fun isReturnDateValid(returnDateText: String): Boolean {
        val pickDateText = binding.pdate.text.toString()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val pickDate = dateFormat.parse(pickDateText)
            val returnDate = dateFormat.parse(returnDateText)

            // Check that the pick-up date is not after the return date
            return !pickDate.after(returnDate)
        } catch (e: java.text.ParseException) { // Use java.text.ParseException
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

        if (textView.id == R.id.ptime) {
            pickTime = "$selectedHour:$selectedMinute"
        }

        textView.text = pickTime

        if (selectedDate == returnDate) {
            Utils.showToast(this, "Return time cannot be earlier than pick-up time")
        }
    }

    // Manejar las selecciones del menú
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