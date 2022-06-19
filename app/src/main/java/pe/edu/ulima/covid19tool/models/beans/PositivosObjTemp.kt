package pe.edu.ulima.covid19tool.models.beans

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class PositivosObjTemp {
    @PrimaryKey
    var ID: Int
    var DEPARTAMENTO: String
    var FECHA: Int

    constructor(ID: Int, DEPARTAMENTO: String, FECHA: Int) {
        this.ID = ID
        this.DEPARTAMENTO = DEPARTAMENTO
        this.FECHA = FECHA
    }
}