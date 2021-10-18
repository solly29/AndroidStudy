package com.example.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp{
                MyScreenContent()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    val backgroundColor by animateColorAsState(
        targetValue = if(isSelected) Color.Red else Color.Transparent
    )


    Surface(color = Color.Yellow) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(24.dp)
                .background(color = backgroundColor)
                .clickable {
                    isSelected = !isSelected
                }
        )
    }
}

// name 은 프리뷰의 이름을 설정 할 수 있다. showBackground는 프리뷰에 배경표시 유무이다.
@Preview(showBackground = true, name = "MyScreen preview")
@Composable
fun DefaultPreview() {
    MyApp{
        MyScreenContent()
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    ComposeBasicsTheme {
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(1000) {"Hello Android #$it"}) {

    var count by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameList(names = names, Modifier.weight(1f))
        Counter(
            count = count,
            updateCount = {
                count = it
            }
        )
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = {
        updateCount(count + 1)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if(count > 5) Color.Green else Color.White
        ),
    modifier = Modifier.padding(bottom = 10.dp) // padding이 마진이다..?
    ) {
        Text(text = "버튼 클릭 $count")
    }
}


@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) {
            Greeting(name = it)
            Divider(color = Color.Black)
        }
    }
}