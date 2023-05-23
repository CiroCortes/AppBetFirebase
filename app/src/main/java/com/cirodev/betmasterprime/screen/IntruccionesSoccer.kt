package com.cirodev.betmasterprime.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun IntruccionesSoccer(
    navController: NavController
) {
    val instructionsText1 = "1- Normal Bets: Here we will send the previously analyzed bets, with their respective stake, which can range from stake 1 to stake 10."




    val instructionsText3 = "2-\tIce Hockey:\n" +
            "For this strategy, we use a progressive betting system. We will invest 1 unit in a single period of a hockey game, specifically in the 'OVER 1.5 GOALS' of a specific period. If we lose our bet, we will reinvest in the next period, but now with 2.5 units. When we win this last bet, we will recover the invested unit and obtain a profit from our investments."

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Card(
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = instructionsText1,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                }

                Card(
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = instructionsText3,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                }

            }
        }
    }

}