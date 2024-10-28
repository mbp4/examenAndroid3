package com.example.examenandroid3

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import java.util.Locale

class ListadoActivity: ComponentActivity() {

    private lateinit var botonImagen: ImageButton
    private lateinit var lista: ListView
    private lateinit var btnPendientes: Button
    private lateinit var btnHechas: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var recordatoriosLista: ArrayList<String>
    private var pendientes: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        botonImagen = findViewById(R.id.imageButton)
        lista = findViewById(R.id.lista)
        btnPendientes = findViewById(R.id.Pendientes)
        btnHechas = findViewById(R.id.Hechas)

        recordatoriosLista = ArrayList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recordatoriosLista)
        lista.adapter = adapter

        registerForContextMenu(lista)

        botonImagen.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnPendientes.setOnClickListener { pendientes = true; actualizarLista() }
        btnHechas.setOnClickListener { pendientes = false; actualizarLista() }

        lista.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetallesActivity::class.java)
            intent.putExtra("posicion", position)
            startActivity(intent)
        }

        actualizarLista()


    }

    private fun actualizarLista() {
        recordatoriosLista.clear()
        if (pendientes) {
            recordatoriosLista.addAll(RegistroActivity.pendientes.map { it.nombre })
        } else {
            recordatoriosLista.addAll(RegistroActivity.completadas.map { it.nombre })
        }
        adapter.notifyDataSetChanged()

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_delete -> {
                val tarea = recordatoriosLista.removeAt(info.position)
                adapter.notifyDataSetChanged()
                RegistroActivity.pendientes.removeIf { it.nombre == tarea }
                Toast.makeText(this, "$tarea eliminada", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_done -> {
                val tareaNombre = recordatoriosLista[info.position]
                val tarea = RegistroActivity.pendientes.find { it.nombre == tareaNombre }
                if (tarea != null) {
                    RegistroActivity.pendientes.remove(tarea)
                    RegistroActivity.completadas.add(tarea)
                    recordatoriosLista[info.position] = "$tareaNombre (Hecho)"
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "$tareaNombre marcada como hecha", Toast.LENGTH_SHORT).show()
                }
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}