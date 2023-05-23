package com.cirodev.betmasterprime.nav

sealed class Screens(val route: String){
    object MainScreen : Screens( "main_screen")
    object GetDataSoccerTips : Screens( "get_data_soccer_tips")
    object GetDataSoccerTipsResultados : Screens( "get_data_soccer_tips_resultados")
    object IntruccionesSoccer : Screens( "intrucciones_soccer")

}
