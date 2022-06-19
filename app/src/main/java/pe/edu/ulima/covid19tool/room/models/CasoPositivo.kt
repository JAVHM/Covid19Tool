package pe.edu.ulima.covid19tool.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CasoPositivo (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val Departamento: String,
    val FechaResultado:Int
)
