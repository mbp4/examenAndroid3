package com.example.examenandroid3

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.time.LocalDate
import java.util.Calendar

class RegistroActivity : ComponentActivity() {

    companion object{
        val lista: MutableList<Tarea> = mutableListOf()
        val pendientes: ArrayList<Tarea> = ArrayList()
        val completadas: ArrayList<Tarea> = ArrayList()
    }

    private lateinit var textNombre: EditText
    private lateinit var textDescripcion: EditText
    private lateinit var textFecha: EditText
    private lateinit var textPrioridad: EditText
    private lateinit var textCoste: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnCancelar: Button
    private lateinit var fechaSeleccionada: LocalDate


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        textNombre = findViewById(R.id.textNombre)
        textDescripcion = findViewById(R.id.textDescripcion)
        textFecha = findViewById(R.id.textFecha)
        textPrioridad = findViewById(R.id.textPrioridad)
        textCoste = findViewById(R.id.textCoste)
        btnRegistrar = findViewById(R.id.Registrar)
        btnCancelar = findViewById(R.id.Cancelar)

        fechaSeleccionada = LocalDate.now()
        textFecha.setText(fechaSeleccionada.toString())

        textFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val año = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val día = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, año, mes, día ->
                fechaSeleccionada = LocalDate.of(año, mes + 1, día) // +1 ya que enero es 0
                textFecha.setText(fechaSeleccionada.toString())
            }, año, mes, día).show()
        }

        btnCancelar.setOnClickListener {
            Toast.makeText(this, "Tarea cancelada", Toast.LENGTH_SHORT).show()
            val Intent = Intent(this, ListadoActivity::class.java)
            startActivity(Intent)
        }

        btnRegistrar.setOnClickListener {
            registrarTarea()
        }
    }

    private fun registrarTarea() {
        val nombre = textNombre.text.toString()
        val descripcion = textDescripcion.text.toString()
        val prioridad = textPrioridad.text.toString()
        val coste = textCoste.text.toString().toIntOrNull() ?: 0

        if (nombre.isNotEmpty()) {
            val nuevaTarea = Tarea(nombre, descripcion, fechaSeleccionada, prioridad, coste)
            lista.add(nuevaTarea)
            pendientes.add(nuevaTarea)
            Toast.makeText(this, "Tarea registrada", Toast.LENGTH_SHORT).show()
            val Intent = Intent(this, ListadoActivity::class.java)
            startActivity(Intent)
        } else {
            Toast.makeText(this, "Error al añadir tarea, compruebe que el nombre no esté vacío", Toast.LENGTH_SHORT).show()
        }
    }
}
