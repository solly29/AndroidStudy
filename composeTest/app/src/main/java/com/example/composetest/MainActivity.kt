package com.example.composetest

import android.content.res.Configuration
import android.os.Bundle
import android.renderscript.Sampler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeTestTheme {
                Conversation(messages = SampleData.conversationSample)
            }

//            MessageCard(str = Message("test", "test"))
            /*ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }*/
        }
    }
}

data class Message(val str1: String, val str2: String)

@Composable
fun MessageCard(str: Message) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "test",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
//                .align(Alignment.CenterVertically)
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember {
            mutableStateOf(false)
        }
        
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) { // 세로로 나열할 수 있다.

            Text(
                text = str.str1,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp)) // 뷰와 같은 기능을함

            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                Text(
                    text = str.str2,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

}

// 미리보기 화면을 볼 수 있다. 그리고 미리보기 화면의 앱을 실행해볼 수 있다.
@Preview
@Composable
fun showMessageCard() {
    MessageCard(
        str = Message("adroid", "test")
    )
}


// 다크 모드 설정 프리뷰
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTestTheme {
        MessageCard(
            str = Message("Colleague", "Take a look at Jetpack Compose")
        )
    }
}

// 리스트 만들기
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) {
            MessageCard(str = it)
        }
    }
}

// 리스트 프리뷰
@Preview
@Composable
fun priviewConversation() {
    ComposeTestTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}


/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        Greeting("Android")
    }
}*/
