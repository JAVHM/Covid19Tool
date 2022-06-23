package pe.edu.ulima.covid19tool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pe.edu.ulima.covid19tool.room.CasosDB


class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentMenu = menuFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragmentMenu)
            .commit()
    }

    override fun passDataCom(room: CasosDB) {
        val bundle = Bundle()
        bundle.putString("message", room.toString())

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentVDF = verDataFragment()
        fragmentVDF.arguments = bundle

        transaction.replace(R.id.fragmentcontainer, fragmentVDF)
        transaction.commit()
    }

    override fun Volver() {

        val bundle = Bundle()

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentMenu = menuFragment()
        fragmentMenu.arguments = bundle

        transaction.replace(R.id.fragmentcontainer, fragmentMenu)
        transaction.commit()
    }

}