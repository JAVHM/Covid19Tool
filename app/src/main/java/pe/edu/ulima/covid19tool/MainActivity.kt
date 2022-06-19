package pe.edu.ulima.covid19tool

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.*
import pe.edu.ulima.covid19tool.PositivosObjTemp
import pe.edu.ulima.covid19tool.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        readCSV()
    }

    var listTemp = mutableListOf<PositivosObjTemp>()

    private fun readCSV(){
        val minput = InputStreamReader(assets.open("CSVTemporal.csv"))
        val reader = BufferedReader(minput)

        var line : String?
        var displayData : String = ""

        while (reader.readLine().also { line = it } != null){
            val row : List<String> = line!!.split(",")
            val ptemp = PositivosObjTemp(row[0], row[1])
            listTemp.add(ptemp)
            println("Se ha agregado:" + ptemp.palo + " " + ptemp.valor)
        }
    }
}