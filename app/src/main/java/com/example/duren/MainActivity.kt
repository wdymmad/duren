package com.example.duren

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duren.ui.theme.DurenTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DurenTheme {
                CaraMakanDurianApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaraMakanDurianApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cara Makan Duren",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        CaraMakanDurianScreen(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CaraMakanDurianScreen(
    modifier: Modifier = Modifier
) {
    var currentIndex by remember {
        mutableIntStateOf(0)
    }

    var clickCount by remember {
        mutableIntStateOf(0)
    }

    // Durian utuh membutuhkan 2-4 klik secara acak
    var targetClickCount by remember {
        mutableIntStateOf(Random.nextInt(2, 5))
    }

    val currentImageId = when (currentIndex) {
        0 -> R.drawable.durian_utuh
        1 -> R.drawable.durian_dibuka
        2 -> R.drawable.daging_durian
        3 -> R.drawable.durian_habis
        else -> R.drawable.durian_utuh
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = currentImageId),
            contentDescription = "Cara Makan Duren",
            modifier = Modifier
                .size(280.dp)
                .clickable {
                    when (currentIndex) {
                        0 -> {
                            // Durian utuh: 2-4 klik
                            clickCount++

                            if (clickCount >= targetClickCount) {
                                currentIndex = 1
                                clickCount = 0
                            }
                        }

                        1 -> {
                            // Durian dibuka: 1 klik
                            currentIndex = 2
                            clickCount = 0
                        }

                        2 -> {
                            // Daging durian: 1 klik untuk makan
                            currentIndex = 3
                            clickCount = 0
                        }

                        3 -> {
                            // Kembali ke awal
                            currentIndex = 0
                            clickCount = 0
                            targetClickCount = Random.nextInt(2, 5)
                        }
                    }
                }
        )

        Spacer(modifier = Modifier.height(24.dp))

        val teksTampil = when (currentIndex) {
            0 -> {
                val sisaKlik = targetClickCount - clickCount

                if (clickCount > 0) {
                    "Buka duren ($sisaKlik klik lagi)"
                } else {
                    "Klik untuk membuka duren"
                }
            }

            1 -> "Klik untuk mengambil daging duren"

            2 -> "Klik untuk mulai makan duren"

            else -> "Duren habis! Klik untuk mengulang"
        }

        Text(
            text = teksTampil,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CaraMakanDurianPreview() {
    DurenTheme {
        CaraMakanDurianApp()
    }
}