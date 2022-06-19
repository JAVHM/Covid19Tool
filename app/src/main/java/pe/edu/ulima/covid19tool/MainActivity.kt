package pe.edu.ulima.covid19tool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import pe.edu.ulima.covid19tool.models.beans.PositivosObjTemp
import pe.edu.ulima.covid19tool.room.CasosDB
import pe.edu.ulima.covid19tool.room.models.CasoPositivo
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // readCSV()
        //DATABASE
        val room=Room.databaseBuilder(this,CasosDB::class.java,"casos").build()

        lifecycleScope.launch{

            room.casoPositivoDAO().insert(CasoPositivo(0,"LIMA",121212))
            room.casoPositivoDAO().insert(CasoPositivo(1,"AQP",121212))
            room.casoPositivoDAO().insert(CasoPositivo(2,"CHIM",121212))

            val casos=room.casoPositivoDAO().obtenerCasos()
            for (item in casos){
                println("${item.Departamento},${item.FechaResultado}")
            }
        }

    }




//    var listTemp = mutableListOf<PositivosObjTemp>()
//
//    private fun readCSV(){
//        val minput = InputStreamReader(assets.open("positivos_covid.csv"))
//        val reader = BufferedReader(minput)
//
//        var line : String?
//        var displayData : String = ""
//
//        while (reader.readLine().also { line = it } != null){
//            val row : List<String> = line!!.split(";")
//
//            val ptemp = PositivosObjTemp(row[1], row[7])
//
//            listTemp.add(ptemp)
//            println("Se ha agregado:" + ptemp.DEPARTAMENTO + " " + ptemp.FECHA)
//        }
//    }
}