package com.example.offlinefirstapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.offlinefirstapp.domain.workers.UpdateDbWorker
import com.example.offlinefirstapp.presentation.screens.MainScreen
import com.example.offlinefirstapp.presentation.ui.theme.OfflineFirstAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.time.Duration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        workManager = WorkManager.getInstance(applicationContext)

        val workRequest = OneTimeWorkRequestBuilder<UpdateDbWorker>()
            .setInitialDelay(Duration.ofSeconds(5))
            .setBackoffCriteria(backoffPolicy = BackoffPolicy.LINEAR, duration = Duration.ofSeconds(15))
            .build()

        // enqueue = ставить в очередь

        //Так
        //   WorkManager.getInstance(applicationContext).enqueue(workRequest)
        //или так
        workManager.enqueue(workRequest)

        setContent {
            OfflineFirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OfflineFirstAppTheme {
        Greeting("Android")
    }
}