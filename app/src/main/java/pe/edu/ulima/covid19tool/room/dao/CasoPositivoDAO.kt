package pe.edu.ulima.covid19tool.room.dao

import androidx.room.*
import pe.edu.ulima.covid19tool.models.beans.PositivosObjTemp


@Dao
interface CasoPositivoDAO {

    @Query("SELECT * FROM PositivosObjTemp")
    fun obtenerCasos(): List<PositivosObjTemp>

    @Query("SELECT * FROM PositivosObjTemp WHERE FECHA=:fecha")
    fun getByFecha(fecha: Int):PositivosObjTemp

    @Insert
    fun insert(PositivosObjTemp: PositivosObjTemp)

    @Update
    fun update(PositivosObjTemp:PositivosObjTemp)

    @Query("DELETE FROM PositivosObjTemp WHERE ID=:id")
    fun delete(id:Int)
}