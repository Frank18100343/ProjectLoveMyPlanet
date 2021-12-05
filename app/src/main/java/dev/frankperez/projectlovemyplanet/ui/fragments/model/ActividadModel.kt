package dev.frankperez.projectlovemyplanet.ui.fragments.model

data class ActividadModel (
    val id:String,
    val actividad: String,
    val descripcion: String,
    val direccion: String,
    val desde: String,
    val hasta: String,
    val puntos: Int
)