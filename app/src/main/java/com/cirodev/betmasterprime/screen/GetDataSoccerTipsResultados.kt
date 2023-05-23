package com.cirodev.betmasterprime.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cirodev.util.Partidos
import com.cirodev.util.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun GetDataSoccerTipsResultados(
    navController: NavController,
    sharedViewModel: SharedViewModel
){

    val context = LocalContext.current


    // obtencion de partidos desde firebase
    var partidosList by remember { mutableStateOf(listOf<Partidos>()) }

    sharedViewModel.retrievePartidos(context) {
        //println(it)//imprimir por consola el listado

          partidosList = it.filter {
                partido-> partido.status != "TO WAITING" && partido.status !="in play" }
           .sortedByDescending { it.docId }




    }

    Column( modifier = Modifier
        .fillMaxWidth()) {

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            items(partidosList){partido->

                val borderColor = when (partido.status){
                    "WIN" -> Color.Green
                    "LOSE" -> Color.Red
                    else -> Color.Gray
                }

                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp,borderColor)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Date : ${partido.fdate}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Sport : ${partido.sport}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Match : ${partido.match}",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Tips : ${partido.tip}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )


                        Text(
                            text = "Result : ${partido.status ?: "no bet"}",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Odd : ${partido.odd }",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(bottom = 8.dp)

                            )
                        Text(
                            text = "Profit : ${partido.profit } Units",
                            style = MaterialTheme.typography.body1,

                            )


                    }

                }

            }

        }


    }
}