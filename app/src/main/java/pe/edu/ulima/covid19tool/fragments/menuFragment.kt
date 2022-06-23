package pe.edu.ulima.covid19tool

import android.content.Context
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


//        view.btnSincronizar.setOnClickListener{
//            SincronizarData(room)
//        }
        view.findViewById<Button>(R.id.btnSincronizar).setOnClickListener{
            SincronizarData(room)
        }
        //view.btnLimpiar.setOnClickListener{LimpiarData(room) }
        view.findViewById<Button>(R.id.btnLimpiar).setOnClickListener{LimpiarData(room) }
//        view.btnVerData.setOnClickListener {
//            communicator.passDataCom(room)
//        }
        view.findViewById<Button>(R.id.btnVerData).setOnClickListener {
            communicator.passDataCom(room)
        }

        return view
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

                while (reader.readLine().also { line = it } != null){
                    val row : List<String> = line!!.split(";")

                    if(countRow!=0){
                        val ptemp = PositivosObjTemp(count, row[1], row[0].toInt())
                        count++;
                        room.casoPositivoDAO().insert(PositivosObjTemp(ptemp.ID,ptemp.DEPARTAMENTO,ptemp.FECHA))
                        println("item "+ptemp.ID+" "+ptemp.FECHA+" "+ptemp.DEPARTAMENTO)
                    }
                    countRow++
                }
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
            for (item in room.casoPositivoDAO().obtenerCasos()) {
                println("DEL "+"${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
                room.casoPositivoDAO().delete(item.ID)
            }
        }
    }
}