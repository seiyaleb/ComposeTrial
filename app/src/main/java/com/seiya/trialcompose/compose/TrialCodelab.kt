package com.seiya.trialcompose.compose

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seiya.trialcompose.ui.theme.TrialComposeTheme

//コードラボ参照

//再利用可能なコンポーザブル
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    //画面表示の状態を管理
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if(shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
        } else {
            Greetings()
        }
    }
}

//初回画面を構築
@Composable
private fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Leb Family")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    TrialComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

//リスト画面全体を構築
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(100) {"$it"}
) {
    LazyColumn (modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) {name ->
            Greeting(name = name)
        }
    }
}

//リスト画面の各アイテムを構築
@Composable
private fun Greeting(name: String) {
    //ボタンのクリック状態を管理
    var expanded by remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if(expanded) 50.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    val resourceIdLess = Resources.getSystem()
        .getIdentifier("show_less", "string", "android")
    val resourceIdMore = Resources.getSystem()
        .getIdentifier("show_more", "string", "android")

    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Ola,")
                Text(text = name, style = MaterialTheme.typography.displayLarge)
                if(expanded) {
                    Text(text = ("Es el mejor.").repeat(5),
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(resourceIdLess)
                    } else {
                        stringResource(resourceIdMore)
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    TrialComposeTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    TrialComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}