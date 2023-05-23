package com.cirodev.betmasterprime.screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cirodev.betmasterprime.adnetwork.addInterstitialCallbacks
import com.cirodev.betmasterprime.adnetwork.loadInterstitial
import com.cirodev.betmasterprime.adnetwork.showInterstitial
import com.cirodev.util.Partidos
import com.cirodev.util.SharedViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun GetDataSoccerTips(
    navController: NavController,
    sharedViewModel: SharedViewModel


) {


    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }


    // obtencion de partidos desde firebase
    var partidosList by remember { mutableStateOf(listOf<Partidos>()) }
    sharedViewModel.retrievePartidos(context) {
        println(it)//imprimir por consola el listado
        partidosList = it.filter { partido -> partido.status == "in play" }
        isLoading = false
    }
    // pasamos el campo fdate a Date
    val dateParser = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
    val dateFormater = SimpleDateFormat("yyyy-MM-dd hh:mma", Locale.getDefault())

    // main layout

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            loadInterstitial(context)
            showInterstitial(context)
            addInterstitialCallbacks(context)

        }

        //listado

        val currentDate = Calendar.getInstance()
        val partidosHoy = partidosList.filter { partido ->
            val date = dateParser.parse(partido.fdate)
            val partidoDate = Calendar.getInstance()
            partidoDate.timeInMillis = date.time
            partidoDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                    partidoDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR)
        }

        LaunchedEffect(isLoading) {
            delay(10000)
            isLoading = false
        }


        if (isLoading) {
            // Mostrar animación de carga
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp)

            ) {
                if (partidosHoy.isNotEmpty()) {
                    items(partidosHoy) { partido ->
                        val date = dateParser.parse(partido.fdate)
                        val dateSting = dateFormater.format(date)

                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .fillMaxWidth(),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(2.dp, Color.Gray)
                        )
                        {
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
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "League : ${partido.league}",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Match : ${partido.match}",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp)

                                )
                                Text(
                                    text = "Tips : ${partido.tip}",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Odd : ${partido.odd}",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Stake : ${partido.stake}" + " Unit",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                            }

                        }



                            }

                    // materializamos una card para mostrar que no hay partidos.
                } else {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = 8.dp,
                            backgroundColor = Color.White,
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Text(
                                text = "Nothing here yet, we'll let you know when the bets are ready",
                                modifier = Modifier.padding(16.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                        }

                    }
                }//items
            }//lazy
        }//colum
    }

}//funcion
