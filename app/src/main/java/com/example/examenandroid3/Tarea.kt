package com.example.examenandroid3

import java.io.Serializable
import java.time.LocalDate

data class Tarea(val nombre: String, val descripcion: String, val fecha: LocalDate, val prioridad: String, val coste: Number):
    Serializable
