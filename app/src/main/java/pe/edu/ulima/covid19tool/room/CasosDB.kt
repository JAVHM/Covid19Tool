package pe.edu.ulima.covid19tool.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.ulima.covid19tool.room.dao.CasoPositivoDAO
import pe.edu.ulima.covid19tool.room.models.CasoPositivo

@Database(
    entities = [CasoPositivo::class],
    version = 1
)
abstract class CasosDB:RoomDatabase() {
    abstract fun casoPositivoDAO():CasoPositivoDAO
}