package com.cirodev.util

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject



import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class SharedViewModel: ViewModel() {

    //Esta función recupera todos los partidos de la colección "partidos" de Firebase,
    // los convierte en una lista de objetos Partido y los devuelve a través de la variable de
    // retorno partidos. También agregamos un ordenamiento en la consulta para que los partidos
    // se devuelvan ordenados por fecha descendente (los partidos más recientes primero).

    fun retrievePartidos(context: Context, partidos: (List<Partidos>) -> Unit) = CoroutineScope(
        Dispatchers.IO).launch {

        val firestoreRef = Firebase.firestore
            .collection("partidos")


        try {
            firestoreRef.get().addOnSuccessListener { querySnapshot ->
                val partidoList = mutableListOf<Partidos>()
                for (document in querySnapshot) {
                    val partido = document.toObject<Partidos>()
                    partido.docId = document.id
                    partidoList.add(partido)
                }
                partidos(partidoList)
            }.addOnFailureListener {
                Toast.makeText(context, "Something Was Wrong", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}