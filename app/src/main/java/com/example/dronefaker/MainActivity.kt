package com.example.dronefaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val bluetoothManager = BluetoothManager(this)
    private val wifiManager = WifiManager(this)
    private val droneSignalGenerator = DroneSignalGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroneFakerApp()
        }
    }

    @Composable
    fun DroneFakerApp() {
        val remoteIDSignalState = remember { mutableStateOf<RemoteIDSignal?>(null) }

        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Button(onClick = { startFakingSignals(remoteIDSignalState) }) {
                            Text("Start Faking Signals")
                        }
                        Button(onClick = { stopFakingSignals() }) {
                            Text("Stop Faking Signals")
                        }
                        RemoteIDSignalDisplay(remoteIDSignalState)
                    }
                }
            }
        }
    }

    private fun startFakingSignals(remoteIDSignalState: androidx.compose.runtime.MutableState<RemoteIDSignal?>) {
        GlobalScope.launch(Dispatchers.IO) {
            val remoteIDSignal = droneSignalGenerator.generateRemoteIDSignal()
            bluetoothManager.broadcastSignal(remoteIDSignal)
            wifiManager.broadcastSignal(remoteIDSignal)
            remoteIDSignalState.value = remoteIDSignal
        }
    }

    private fun stopFakingSignals() {
        bluetoothManager.stopBroadcasting()
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DroneFakerApp()
    }
}

@Composable
fun RemoteIDSignalDisplay(remoteIDSignalState: androidx.compose.runtime.MutableState<RemoteIDSignal?>) {
    val remoteIDSignal = remoteIDSignalState.value
    if (remoteIDSignal != null) {
        Column {
            Text("Drone ID: ${remoteIDSignal.droneId}")
            Text("Latitude: ${remoteIDSignal.latitude}")
            Text("Longitude: ${remoteIDSignal.longitude}")
            Text("Altitude: ${remoteIDSignal.altitude}")
            Text("Speed: ${remoteIDSignal.speed}")
            Text("Heading: ${remoteIDSignal.heading}")
            Text("Timestamp: ${remoteIDSignal.timestamp}")
        }
    }
}