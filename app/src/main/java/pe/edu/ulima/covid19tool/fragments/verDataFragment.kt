package pe.edu.ulima.covid19tool

import android.os.Bundle
import android.view.*
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.ulima.covid19tool.room.CasosDB


class verDataFragment : Fragment(R.layout.verdatafragment) {

    val departamentosArray = arrayOf<String>("AMAZONAS","ANCASH","APURIMAC","AREQUIPA","AYACUCHO",
        "CAJAMARCA", "CALLAO", "CUSCO", "HUANCAVELICA", "HUANUCO","ICA","JUNIN","LA LIBERTAD","LAMBAYEQUE","LIMA",
        "LORETO", "MADRE DE DIOS", "MOQUEGUA", "PASCO", "PIURA", "PUNO", "SAN MARTIN", "TACNA", "TUMBES", "UCAYALI")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.verdatafragment, container, false)
        val room= Room.databaseBuilder(container?.context!!.applicationContext, CasosDB::class.java,"casosBD").build()
        //LeerData(room)
        LlenarTabla(room, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun LeerData(room : CasosDB){
        lifecycleScope.launch(Dispatchers.IO){
            println("LEER ROOM")
            var temporalTableVDF=view?.findViewById<TextView>(R.id.temporalTableVDF)
            temporalTableVDF?.text = "."
            for (item in room.casoPositivoDAO().obtenerCasos()){
                temporalTableVDF?.text =  temporalTableVDF?.text.toString() + item.ID + " " + item.DEPARTAMENTO + " " + item.FECHA + " "
                println("SEE "+"${item.ID},${item.DEPARTAMENTO},${item.FECHA}")
            }
        }
    }
    private fun LlenarTabla(room: CasosDB, view: View){
        println("LLENAR TABLA")
        val tabla = view?.findViewById<TableLayout>(R.id.tlData)

        //Instanciar títulos tabla
        val registroT = LayoutInflater.from(requireContext().applicationContext).inflate(R.layout.registro_unico, null)
        var trView= registroT.findViewById<TableRow>(R.id.trView)
        val depT = trView.findViewById<View>(R.id.tvRowDep) as TextView
        val numT = trView.findViewById<View>(R.id.tvRowCount) as TextView
        depT.setText("DEPARTAMENTO")
        numT.setText("NUMERO")
        tabla!!.addView(registroT)

        //Añadir contenido
        lifecycleScope.launch(Dispatchers.IO){
            for(i in departamentosArray){
                val fila = room.casoPositivoDAO().getByDepartamento(i)
                println("Ciudad: " + i + " | Cantidad: " + fila.count())
                withContext (Dispatchers.Main) {
                    val registro = LayoutInflater.from(requireContext().applicationContext).inflate(R.layout.registro_unico, null, false)
                    val departamento = registro.findViewById<View>(R.id.tvRowDep) as TextView
                    val count = registro.findViewById<View>(R.id.tvRowCount) as TextView
                    departamento.setText(i)
                    count.setText(fila.count().toString())
                    tabla.addView(registro)
                }
            }
        }
    }
}