package studio.zebro.networksample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.*
import pe.edu.ulima.covid19tool.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeGetApiRequest()
    }

    private fun makeGetApiRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            var httpURLConnection: HttpURLConnection? = null
            try {

                val url = URL("https://reqres.in/api/users?page=2")

                httpURLConnection = url.openConnection() as HttpURLConnection

                val code = httpURLConnection.responseCode

                if (code != 200) {
                    throw IOException("The error from the server is $code")
                }

                val bufferedReader = BufferedReader(
                    InputStreamReader(httpURLConnection.inputStream)
                )

                val jsonStringHolder: StringBuilder = StringBuilder()

                while (true) {
                    val readLine = bufferedReader.readLine() ?: break
                    jsonStringHolder.append(readLine)
                }

                //val userProfileResponse =
                    //Gson().fromJson(jsonStringHolder.toString(), UserProfileResponse::class.java)

                /*withContext(Dispatchers.Main) {
                    findViewById<TextView>(R.id.name).apply {
                        text = userProfileResponse.data[0].let {
                            "${it.firstName} ${it.lastName}"
                        }
                    }
                }*/
            } catch (ioexception : IOException){
                Log.e(this.javaClass.name, ioexception.message.toString())
            } finally {
                httpURLConnection?.disconnect()
            }
        }

    }
}