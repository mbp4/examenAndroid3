package com.example.examenandroid3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import android.graphics.Color

class DetallesActivity: ComponentActivity() {

    private lateinit var textNombre: TextView
    private lateinit var textDescripcion: TextView
    private lateinit var textFecha: TextView
    private lateinit var textPrioridad: TextView
    private lateinit var textCoste: TextView
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        textNombre = findViewById(R.id.textNombre)
        textDescripcion = findViewById(R.id.textDescripcion)
        textFecha = findViewById(R.id.textFecha)
        textPrioridad = findViewById(R.id.textPrioridad)
        textCoste = findViewById(R.id.textCoste)
        btnVolver = findViewById(R.id.btnVolver)

        val tarea = intent.getSerializableExtra("tareaSeleccionada") as? Tarea

        if (tarea != null) {
            textNombre.text = tarea.nombre
            textDescripcion.text = tarea.descripcion
            textFecha.text = "Para el " + tarea.fecha.toString()
            textPrioridad.text = tarea.prioridad

            if (tarea.prioridad == "URGENTE") {
                textPrioridad.setTextColor(Color.RED)
            }

            textCoste.text = "Tiene un coste de " + tarea.coste.toString() + "€"
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

}