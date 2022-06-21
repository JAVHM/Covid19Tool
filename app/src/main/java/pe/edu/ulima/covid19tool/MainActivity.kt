package pe.edu.ulima.covid19tool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.ulima.covid19tool.models.beans.PositivosObjTemp
import pe.edu.ulima.covid19tool.room.CasosDB
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //readCSV()a
        //DATABASE
        val room=Room.databaseBuilder(this,CasosDB::class.java,"casosBD").build()

        lifecycleScope.launch(Dispatchers.IO){
            //room.casoPositivoDAO().insert(PositivosObjTemp(1,"lima",1231))
           // room.casoPositivoDAO().insert(PositivosObjTemp(2,"lima",1231))
            //room.casoPositivoDAO().insert(PositivosObjTemp(3,"lima",1231))

            val minput = InputStreamReader(assets.open("positivos_covid.csv"))
            val reader = BufferedReader(minput)
            //asasas
            var line : String?

            var count: Int = 0
            var countRow: Int=0
            while (reader.readLine().also { line = it } != null){
                val row : List<String> = line!!.split(";")

                if(countRow!=0){
                    val ptemp = PositivosObjTemp(count, row[1], row[7].toInt())
                    count++;

                    room.casoPositivoDAO().insert(PositivosObjTemp(ptemp.ID,ptemp.DEPARTAMENTO,ptemp.FECHA))
                    println("item"+ptemp.ID+""+ptemp.FECHA+""+ptemp.DEPARTAMENTO)
                }
                countRow++
            }

            val casos = room.casoPositivoDAO().obtenerCasos()
            println("LEER ROOM")
            for (item in casos){
                println("${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
            }
            println("BORRAR ROOM")
            for (item in casos){
                room.casoPositivoDAO().delete(item.ID)
            }
        }

    }

    private fun readCSV(){

    }

    private fun getMethod(){
        val url = URL("https://files.minsa.gob.pe/s/eRqxR35ZCxrzNgr/download%22)")
        val connection = url.openConnection()
        val contentLength = connection.contentLength

        val stream = DataInputStream(url.openStream())

        val buffer = ByteArray(contentLength)
        stream.readFully(buffer)
        stream.close()
    }

}