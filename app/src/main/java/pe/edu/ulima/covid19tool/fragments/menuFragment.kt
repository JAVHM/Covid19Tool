package pe.edu.ulima.covid19tool

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.ulima.covid19tool.models.beans.PositivosObjTemp
import pe.edu.ulima.covid19tool.room.CasosDB
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class menuFragment : Fragment(R.layout.menufragment) {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.menufragment, container, false)
        var room= Room.databaseBuilder(container?.context!!.applicationContext, CasosDB::class.java,"casosBD").build()
        communicator = activity as Communicator

        var btnSincronizar=view.findViewById<Button>(R.id.btnSincronizar)
        var btnLimpiar=view.findViewById<Button>(R.id.btnLimpiar)
        var btnVerData=view.findViewById<Button>(R.id.btnVerData)

        verificarTablaLlenna(room, btnSincronizar)

        btnSincronizar.setOnClickListener{
            SincronizarData(room)
            btnSincronizar.setEnabled(false);
        }

        btnLimpiar.setOnClickListener{
            LimpiarData(room)
            btnSincronizar.setEnabled(true);
        }

        btnVerData.setOnClickListener {
            communicator.passDataCom(room)
        }

        return view
    }

    private fun verificarTablaLlenna(room : CasosDB, btn: Button){
        val mCursor: Cursor = room.query("SELECT * FROM PositivosObjTemp", null)

        if (mCursor.moveToFirst()) {
            btn.setEnabled(false)
        } else {
            btn.setEnabled(true)
        }
    }

    private fun SincronizarData(room : CasosDB){
        lifecycleScope.launch(Dispatchers.IO){
            //Intento con archivo CSV
            //val minput = InputStreamReader(context?.assets?.open("positivos_covid.csv"))
            //val reader = BufferedReader(minput)

            try {
                val url = URL("https://files.minsa.gob.pe/s/eRqxR35ZCxrzNgr/download")
                val connection = url.openConnection()
                connection.doInput=true

                val reader = BufferedReader(InputStreamReader(connection.getInputStream()))

                var line : String?

                var count: Int = 0
                var countRow: Int=0

//           Inicia el progessBar
                val progressBarInn = view?.findViewById<ProgressBar>(R.id.progressBarInner)
                val progressBarOut = view?.findViewById<ProgressBar>(R.id.progressBarOuter)

                Thread(Runnable {

                    activity?.runOnUiThread(Runnable {
                        progressBarInn?.visibility=View.VISIBLE
                        progressBarOut?.visibility=View.VISIBLE
                    })
                }).start()

                for( i in 1..10000){
                    reader.readLine().also { line = it }
                    val row : List<String> = line!!.split(";")

                    if(countRow!=0){
                        if(row[7] == ""){
                            val ptemp = PositivosObjTemp(count, row[1], 0)
                            count++;
                            room.casoPositivoDAO().insert(PositivosObjTemp(ptemp.ID,ptemp.DEPARTAMENTO,ptemp.FECHA))
                            println("item "+ptemp.ID+" "+ptemp.FECHA+" "+ptemp.DEPARTAMENTO)
                        }else {
                            val ptemp = PositivosObjTemp(count, row[1], row[7].toInt())
                            count++;
                            room.casoPositivoDAO()
                                .insert(PositivosObjTemp(ptemp.ID, ptemp.DEPARTAMENTO, ptemp.FECHA))
                            println("item " + ptemp.ID + " " + ptemp.FECHA + " " + ptemp.DEPARTAMENTO)
                        }
                    }
                    countRow++
                }


/*                while (reader.readLine().also { line = it } != null){
                    val row : List<String> = line!!.split(";")

                    if(countRow!=0){
                        val ptemp = PositivosObjTemp(count, row[1], row[0].toInt())
                        count++;
                        room.casoPositivoDAO().insert(PositivosObjTemp(ptemp.ID,ptemp.DEPARTAMENTO,ptemp.FECHA))
                        println("item "+ptemp.ID+" "+ptemp.FECHA+" "+ptemp.DEPARTAMENTO)
                    }
                    countRow++
                }*/
                println("LEER ROOM")
                for (item in room.casoPositivoDAO().obtenerCasos()){
                    println("INS "+"${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
                }

//              Fin del progressbar
                Thread(Runnable {
                    activity?.runOnUiThread(Runnable {
                        progressBarInn?.visibility = View.INVISIBLE
                        progressBarOut?.visibility = View.INVISIBLE })
                }).start()

            }catch (e: Exception) {
                println("Error $e")
            }
        }
    }

    private fun LimpiarData(room : CasosDB){
        lifecycleScope.launch(Dispatchers.IO) {
            println("BORRAR ROOM")
//            for (item in room.casoPositivoDAO().obtenerCasos()) {
//                println("DEL "+"${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
//                room.casoPositivoDAO().delete(item.ID)
//            }
            room.casoPositivoDAO().deleteAll()
        }
    }
}