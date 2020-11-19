package com.example.sqliteapplication.lifecycle

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.sqliteapplication.R
import com.example.sqliteapplication.databinding.ActivityDessertBinding
import timber.log.Timber

class DessertActivity : AppCompatActivity() {

    companion object {
        const val KEY_REVENUE = "revenue_key"
        const val KEY_DESSERT_SOLD = "dessert_sold_key"
        const val KEY_TIMER_SECONDS = "timer_seconds_key"
    }

    private var revenue = 0
    private var dessertsSold = 0
    private lateinit var dessertTimer : DessertTimer

    private lateinit var binding: ActivityDessertBinding
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate called")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dessert)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }
        dessertTimer = DessertTimer(this.lifecycle)
        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            dessertTimer.secondsCount = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)
            showCurrentDessert()
        }
        binding.revenue = revenue
        binding.amountSold = dessertsSold
        binding.dessertButton.setImageResource(currentDessert.imageId)
//        dessertTimer.startTimer()
    }

    private fun onDessertClicked() {
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        showCurrentDessert()
    }

    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]

        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            else break
        }

        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val settingsItem = menu!!.findItem(R.id.shareMenuButton)
        val settingsItem2 = menu.findItem(R.id.action_add)
        settingsItem.isVisible = true
        settingsItem2.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {
        super.onStop()
//        dessertTimer.stopTimer()
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState Called")
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertsSold)
        outState.putInt(KEY_TIMER_SECONDS, dessertTimer.secondsCount)
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }
}