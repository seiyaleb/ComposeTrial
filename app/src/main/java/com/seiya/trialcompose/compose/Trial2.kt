package com.seiya.trialcompose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.seiya.trialcompose.ui.theme.TrialComposeTheme
import com.seiya.trialcompose.viewmodel.CountViewModel

//Screen2Uに表示するLazyColumnのリスト作成
val friends =
    listOf("Tom","Bob","Anny","Jane","Bak","John","Smith",
        "Rose","Chan","Morry","Champ","Fraze","Guhn")

//画面遷移
@Composable
fun NavigationTop(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Surface(modifier) {
        NavHost(navController = navController, startDestination = "topScreen") {
            composable(route = "topScreen") {
                topScreen {id ->
                    if(id == 1) {
                        navController.navigate("screen1/$id")
                    } else {
                        navController.navigate("screen2/$id")
                    }
                }
            }
            composable(
                route = "screen1/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                )
            ) {
                Screen1 { navController.navigateUp() }
            }
            composable(
                route = "screen2/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                )
            ) {
                Screen2 { navController.navigateUp() }
            }
        }
    }
}

//トップ画面
@Composable
fun topScreen(onClickButton: (Int) -> Unit) {
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Button(onClick = { onClickButton(1) },
            Modifier.padding(bottom = 50.dp)) {
            Text(text = "カウントアップ", fontSize = 18.sp)
        }
        Button(onClick = { onClickButton(2) }) {
            Text(text = "リスト表示", fontSize = 18.sp)
        }
    }
}

//カウントアップ画面
@Composable
fun Screen1(viewModel: CountViewModel = viewModel(),onClickButton: () -> Unit) {
    val count:Int by viewModel.count.observeAsState(0)
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Count: $count",Modifier.padding(top = 10.dp,bottom = 20.dp))
        Button(onClick = {viewModel.countUp()},Modifier.padding(bottom = 20.dp)) {
            Text(text = "Count")
        }
        Button(onClick = {viewModel.clearUp()},Modifier.padding(bottom = 50.dp)) {
            Text(text = "Clear")
        }
        Button(onClick = onClickButton) {
            Text(text = "Back")
        }
    }
}

//リスト表示画面→状態とロジック
@Composable
fun Screen2(onClickButton: () -> Unit) {
    //ダイアログ表示
    var selectedIndex by remember { mutableStateOf(-1)}
    if (selectedIndex >= 0) {
        AlertDialog(
            onDismissRequest = { selectedIndex = -1 },
            confirmButton = {
                TextButton(onClick = { selectedIndex = -1 }) {
                    Text("OK")
                }
            },
            text = { Text("Index $selectedIndex is clicked.")}
        )
    }
    Screen2UI(onClickButton = onClickButton, friends = friends) {
        selectedIndex = it
    }
}

//リスト表示画面→UI
@Composable
fun Screen2UI(onClickButton: () -> Unit,friends: List<String>,onClickItem: (Int)->Unit = {}) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            modifier = Modifier
                .background(Color.Gray)
                .weight(0.9F),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            itemsIndexed(friends) { index, friend ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(7.dp))
                        .clickable { onClickItem(index) }
                ) {
                    Image(
                        painter = painterResource(id = android.R.mipmap.sym_def_app_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(text = "$friend", fontSize = 20.sp)
                }
            }
        }
        Button(onClick = onClickButton,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .weight(0.1F)) {
            Text(text = "Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationTopPreview() {
    TrialComposeTheme {
        NavigationTop()
    }
}

@Preview(showBackground = true)
@Composable
fun topScreenPreview() {
    TrialComposeTheme {
        topScreen {}
    }
}

@Preview(showBackground = true)
@Composable
fun Screen1Preview() {
    TrialComposeTheme {
        Screen1 {}
    }
}

@Preview(showBackground = true)
@Composable
fun Screen2Preview() {
    TrialComposeTheme {
        Screen2 {}
    }
}