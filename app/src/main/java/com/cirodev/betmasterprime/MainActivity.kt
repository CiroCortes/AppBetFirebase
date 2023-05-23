package com.cirodev.betmasterprime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cirodev.betmasterprime.adnetwork.addInterstitialCallbacks
import com.cirodev.betmasterprime.adnetwork.loadInterstitial
import com.cirodev.betmasterprime.drawer.DrawerBody
import com.cirodev.betmasterprime.drawer.DrawerHeader
import com.cirodev.betmasterprime.drawer.MyTopAppBar
import com.cirodev.betmasterprime.nav.Screens
import com.cirodev.betmasterprime.screen.GetDataSoccerTips
import com.cirodev.betmasterprime.screen.GetDataSoccerTipsResultados
import com.cirodev.betmasterprime.screen.IntruccionesSoccer
import com.cirodev.betmasterprime.screen.MainScreen
import com.cirodev.betmasterprime.ui.theme.BetMasterpRIMETheme
import com.cirodev.util.MenuItemBody
import com.cirodev.util.SharedViewModel
import com.google.android.gms.ads.MobileAds

import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {

    private lateinit var navController : NavHostController
    private val sharedViewModel : SharedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val title = remember { mutableStateOf("Home")}
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val navigationController = rememberNavController()


            BetMasterpRIMETheme {
                navController = rememberNavController()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        MyTopAppBar(title = title.value) {
                            scope.launch { scaffoldState.drawerState.open() }

                        }
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItemBody(id = "home", title = "Home", contentDescription = "go to screen home", icon = Icons.Default.Home),
                                MenuItemBody(id = "intrucciones", title = "Instructions", contentDescription = "go to screen intrucciones", icon = Icons.Default.List),
                                MenuItemBody(id = "resultados", title = "History", contentDescription = "go to screen resultados", icon = Icons.Default.List),
                                MenuItemBody(id = "soccertips", title = "Current Tips", contentDescription = "go to screen soocer tips", icon = Icons.Outlined.Star),
                                MenuItemBody(id = "share", title = "Share", contentDescription = "Share app", icon = Icons.Default.Share)
                            ),
                            onItemClick = {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                    when(it.id){
                                        "home" ->{
                                            title.value = "Home"
                                            navigationController.navigate(Screens.MainScreen.route)
                                        }
                                        "intrucciones" ->{
                                            title.value = "Instructions"
                                            navigationController.navigate(Screens.IntruccionesSoccer.route)
                                        }
                                        "soccertips" ->{
                                            title.value = "Current Tips"
                                            navigationController.navigate(Screens.GetDataSoccerTips.route)
                                        }
                                        "resultados" ->{
                                            title.value = "History"
                                            navigationController.navigate(Screens.GetDataSoccerTipsResultados.route)
                                        }
                                        // cuando el user hace click, definimos una intencion (Intent) de tiop ACTION_SEND
                                        // luego en type se define esta que sera de tipo plano y se agrega un mensaje al metodo putextra
                                        // que en este caso sera el enlace
//                                           Finalmente, se llama al método startActivity() con la nueva intención (Intent) como
//                                           argumento para mostrar al usuario una lista de aplicaciones y servicios que
//                                           pueden usarse para compartir el enlace a la aplicación.
                                        "share" ->{
                                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.cirodev.betmasterprime")
                                            }
                                            startActivity(Intent.createChooser(shareIntent,"Share app" ))
                                        }
                                    }

                                }
                            }
                        )
                    }

                ) {
                    NavHost(
                        navController = navigationController,
                        startDestination = Screens.MainScreen.route
                    ) {

                        composable(Screens.MainScreen.route){ MainScreen(
                            navController = navController,
                            sharedViewModel = sharedViewModel)}
                        composable(Screens.IntruccionesSoccer.route){ IntruccionesSoccer(navController = navController)}
                        composable(Screens.GetDataSoccerTips.route){
                            GetDataSoccerTips(
                                navController = navController,
                                sharedViewModel = sharedViewModel) }
                        composable(Screens.GetDataSoccerTipsResultados.route){
                            GetDataSoccerTipsResultados(
                                navController = navController,
                                sharedViewModel = sharedViewModel) }


                    }

                }

            }
        }

        // iniciar el SDK Ads
        MobileAds.initialize(this){}
        // load the interstitial ad
        loadInterstitial(this)

        // add the interstitial ad callbacks
        addInterstitialCallbacks(this)
    }
}
