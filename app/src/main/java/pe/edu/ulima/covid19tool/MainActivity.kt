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
        readCSV()
        //DATABASE
        val room=Room.databaseBuilder(this,CasosDB::class.java,"casosBD").build()

        lifecycleScope.launch(Dispatchers.IO){
            //room.casoPositivoDAO().insert(PositivosObjTemp(1,"lima",1231))
           // room.casoPositivoDAO().insert(PositivosObjTemp(2,"lima",1231))
            //room.casoPositivoDAO().insert(PositivosObjTemp(3,"lima",1231))
//            listTemp.forEach{
//                    item->
//                room.casoPositivoDAO().insert(PositivosObjTemp(item.ID,item.DEPARTAMENTO,item.FECHA))
//                println("item"+item.ID+""+item.FECHA+""+item.DEPARTAMENTO)
//            }
            val casos = room.casoPositivoDAO().obtenerCasos()
            for (item in casos){
                println("${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
            }
            for (item in casos){
                println("prueba")
                println("${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
            }

        }

    }


    var listTemp = mutableListOf<PositivosObjTemp>()

    private fun readCSV(){
        val minput = InputStreamReader(assets.open("positivos_covid.csv"))
        val reader = BufferedReader(minput)
        //asasas
        var line : String?
        var displayData : String = ""

        var count: Int = 4
        var countRow: Int=0
        while (reader.readLine().also { line = it } != null){
            val row : List<String> = line!!.split(";")

            if(countRow!=0){
                val ptemp = PositivosObjTemp(count, row[1], row[7].toInt())
                count++;

                listTemp.add(ptemp)
                println("Se ha agregado:" + ptemp.ID + " " + ptemp.DEPARTAMENTO + " " + ptemp.FECHA)
            }
            countRow++
        }
//        listTemp.forEach{
//            item->
//            println("item"+item.ID+""+item.FECHA+""+item.DEPARTAMENTO)
//        }
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