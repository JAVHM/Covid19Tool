package pe.edu.ulima.covid19tool.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.ulima.covid19tool.models.beans.PositivosObjTemp
import pe.edu.ulima.covid19tool.room.dao.CasoPositivoDAO

@Database(
    entities = [PositivosObjTemp::class],
    version = 1
)
abstract class CasosDB: RoomDatabase() {
    abstract fun casoPositivoDAO():CasoPositivoDAO
}