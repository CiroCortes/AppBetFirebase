package com.cirodev.betmasterprime.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cirodev.util.Partidos
import com.cirodev.util.SharedViewModel
import com.google.android.gms.ads.MobileAds
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {


    val context = LocalContext.current
    // obtencion de partidos desde firebase
    var partidosList by remember { mutableStateOf(listOf<Partidos>()) }
    sharedViewModel.retrievePartidos(context) {
        //println(it)//imprimir por consola el listado
        partidosList = it
    }
    // pasamos el campo fdate a Date
    val dateParser = SimpleDateFormat("dd-MM-yy", Locale.getDefault())


    // filtrar los partidos que corresponden al mes actual
    val currentMonth = LocalDate.now().monthValue
    val currentYear = LocalDate.now().year

    val currentMonthPartidos = partidosList.filter { partido ->
        val date = dateParser.parse(partido.fdate)
        val partidoLocalDate = LocalDate.of(date.year + 1900, date.month + 1, date.date)
        partidoLocalDate.monthValue == currentMonth && partidoLocalDate.year == currentYear
    }

    // calcular unidades ganadas en el mes actual y en general
    var unidadesGanadasMesActual = 0.0f
    var unidadesGanadasTotal = 0.0f
    currentMonthPartidos.forEach { partido ->
        unidadesGanadasMesActual += partido.profit ?: 0f
    }
    partidosList.forEach { partido ->
        unidadesGanadasTotal += partido.profit ?: 0f
    }
    // implementacion para numeros decimales

    val decimalFormat = DecimalFormat("0.00")



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer( modifier = Modifier.height(16.dp))
        Text(
            text = "Master Bet Strategy",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer( modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(8.dp),
            contentColor = Color.Black
        ) {
            Text(
                text = "Here you will find the best recommendations to optimize your investments and maximize your profits. Remember to check the INSTRUCTIONS section to get the most out of our strategies... And don't forget to turn on NOTIFICATIONS.",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer( modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            elevation = 4.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(8.dp),
            contentColor = Color.Black
        ) {
            //mostrar resultados
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Units. won this month: ${decimalFormat.format(unidadesGanadasMesActual)}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Total unit won:${decimalFormat.format(unidadesGanadasTotal)}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


