package pe.edu.ulima.covid19tool

import pe.edu.ulima.covid19tool.room.CasosDB

interface Communicator {
    fun passDataCom(room : CasosDB)
}