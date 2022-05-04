package com.example.a5amclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import kotlin.concurrent.thread
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    var listQuotes= mutableListOf(
        R.string.quote_string1,R.string.quote_string2,R.string.quote_string3,R.string.quote_string4,R.string.quote_string5,R.string.quote_string6,R.string.quote_string7,R.string.quote_string8,R.string.quote_string9,R.string.quote_string10,
        R.string.quote_string11,R.string.quote_string12,R.string.quote_string13,R.string.quote_string14,R.string.quote_string15,R.string.quote_string16,R.string.quote_string17,R.string.quote_string18,R.string.quote_string19,R.string.quote_string20,
        R.string.quote_string21,R.string.quote_string22,R.string.quote_string23,R.string.quote_string24,R.string.quote_string25,R.string.quote_string26,R.string.quote_string27,R.string.quote_string28,R.string.quote_string29,R.string.quote_string30,
        R.string.quote_string31,R.string.quote_string32,R.string.quote_string33,R.string.quote_string34,R.string.quote_string35
        )
    var quoteNumber=0
    var mainText =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quoteOnAppLoaded()
        clickNewQuote()
    }
    private  fun clickNewQuote(){
        fab_newQuote.setOnClickListener{
            fab_newQuote.isEnabled= false
            if (quoteNumber == listQuotes.size){
                quoteOnAppLoaded()
            }else{
                typeText(getString(listQuotes[quoteNumber]))
                ++quoteNumber
            }
        }
    }
    private fun quoteOnAppLoaded(){
        quoteNumber =0
        listQuotes.shuffle()
        typeText(getString(listQuotes[quoteNumber]))
        ++quoteNumber
    }
    private fun typeText(text: String){
        mainText=""
        val textDelay: Long= 50L

      GlobalScope.launch(Dispatchers.IO){
          val sb =StringBuilder()
          val updatedText=""

          for (i in text.indices){
              mainText =sb.append(updatedText + text[i]).toString()
              Thread.sleep(textDelay)

          }
      }
        val handler =Handler()
        Log.d("Main","Handler started")
        val runnable= object : Runnable{
            override fun run() {
                tv_text.text = "$mainText|"
                handler.postDelayed(this,10)
                if (text == mainText){
                    handler.removeCallbacks(this)
                    tv_text.text = mainText
                    fab_newQuote.isEnabled=true
                }
            }

        }
        handler.postDelayed(runnable,0)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate( R.menu.nav_menu,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.nav_share ->{
                val shareIntent = Intent()
                shareIntent.action =Intent.ACTION_SEND
                shareIntent.type ="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, mainText)
                startActivity(Intent.createChooser(shareIntent,"share this quote"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}