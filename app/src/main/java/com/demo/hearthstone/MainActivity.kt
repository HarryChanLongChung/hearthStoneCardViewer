package com.demo.hearthstone

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MAIN_ACTIVITY"
        private const val SEARCH_RESET = "  (click to reset)"
    }

    lateinit var allCards: List<Models.Card>
    var filteredResult: ArrayList<Models.Card> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return super.onOptionsItemSelected(item)
        }
        return when (item.itemId) {
            R.id.search_icon -> {
                openSearchInterface()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        if (::allCards.isInitialized) return

        spin_kit.visibility = View.VISIBLE
        gridview.visibility = View.GONE

        HttpRequestUtil.getallCards(object : HttpRequestUtil.Companion.HttpRequestListener {
            override fun onSuccess(response: Models.MpApiJsonResponse?) {
                if (response != null) {
                    allCards = response.Cards
                    Log.d(TAG, "Successfully get ${response.Cards.size} response")
                    runOnUiThread { httpRequestReady() }
                } else {
                    Log.d(TAG, "Response has no response")
                }
            }

            override fun onFail(e: Exception) {
                Log.d(TAG, "Something is wrong with the request, " + e.stackTrace)
            }

        })

        btSearch.setOnClickListener {
            val search = etSearchText.text.toString()
            btSearchInput.visibility = View.VISIBLE
            btSearchInput.text = search + SEARCH_RESET

            llSearchInterface.visibility = View.GONE
            spin_kit.visibility = View.VISIBLE
            gridview.visibility = View.GONE

            filteredResult.clear()
            allCards.forEach {
                if (it.name.toLowerCase().contains(search.toLowerCase())) {
                    filteredResult.add(it)
                }
            }

            spin_kit.visibility = View.GONE
            gridview.visibility = View.VISIBLE
            gridview.adapter = GridAdapter(this, filteredResult)
        }

        btCancel.setOnClickListener {
            llSearchInterface.visibility = View.GONE
        }

        btSearchInput.setOnClickListener {
            it.visibility = View.GONE
            gridview.visibility = View.VISIBLE
            gridview.adapter = GridAdapter(this, allCards)
        }
    }

    private fun httpRequestReady() {
        spin_kit.visibility = View.GONE
        gridview.visibility = View.VISIBLE
        gridview.adapter = GridAdapter(this, allCards)
    }

    private fun openSearchInterface() {
        if (!::allCards.isInitialized) return

        llSearchInterface.visibility = View.VISIBLE
        gridview.adapter = GridAdapter(this, allCards)
    }
}
