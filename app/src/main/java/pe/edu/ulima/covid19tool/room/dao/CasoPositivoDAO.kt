package pe.edu.ulima.covid19tool.room.dao

import androidx.room.*
import pe.edu.ulima.covid19tool.room.models.CasoPositivo


@Dao
interface CasoPositivoDAO {

    @Query("SELECT * FROM CasoPositivo")
    suspend fun obtenerCasos():List<CasoPositivo>

    @Query("SELECT * FROM CasoPositivo WHERE FechaResultado=:fecha")
    suspend fun getByFecha(fecha: Int):CasoPositivo

    @Insert
    suspend fun insert(casosPositivos: CasoPositivo)
    @Update
    suspend fun update(casoPositivo:CasoPositivo)

    @Delete
    suspend fun delete(casoPositivo:CasoPositivo )
}