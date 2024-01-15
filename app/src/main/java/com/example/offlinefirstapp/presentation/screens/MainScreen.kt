package com.example.offlinefirstapp.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.offlinefirstapp.data.local.asExternalModel
import com.example.offlinefirstapp.data.models.Coin


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {




    val mainScreenViewModel: MainScreenViewModel = viewModel()


//    LaunchedEffect(key1 = Unit){
//        mainScreenViewModel.startWorkManager()
//    }

    val coins = mainScreenViewModel.coinsFromDbWithFlow.collectAsState(initial = emptyList()).value.map {
        it.asExternalModel()
    }
    val state = mainScreenViewModel.state.collectAsState().value


    val coinsOrdered = coins.sortedBy { it.rank }


    Column() {

        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(coinsOrdered){coin->
                Text(
                    text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if(coin.isActive)"active" else "inactive",
                    color = if(coin.isActive) Color.Green else Color.Red,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                )

            }

        }


        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        if(state.isLoading == true){
            CircularProgressIndicator()
        }





    }

}