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
        val aircraftDataState = remember { mutableStateOf<Aircraft?>(null) }

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
                        Button(onClick = { startFakingSignals(aircraftDataState) }) {
                            Text("Start Faking Signals")
                        }
                        Button(onClick = { stopFakingSignals() }) {
                            Text("Stop Faking Signals")
                        }
                        AircraftDataDisplay(aircraftDataState)
                    }
                }
            }
        }
    }

    private fun startFakingSignals(aircraftDataState: androidx.compose.runtime.MutableState<Aircraft?>) {
        GlobalScope.launch(Dispatchers.IO) {
            val aircraftData = droneSignalGenerator.generateAircraftData()
            bluetoothManager.broadcastSignal(aircraftData)
            wifiManager.broadcastSignal(aircraftData)
            aircraftDataState.value = aircraftData
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
fun AircraftDataDisplay(aircraftDataState: androidx.compose.runtime.MutableState<Aircraft?>) {
    val aircraftData = aircraftDataState.value
    if (aircraftData != null) {
        Column {
            Text("MAC Address: ${aircraftData.macAddress}")
            Text("Connection RSSI: ${aircraftData.connection.rssi}")
            Text("Connection Transport Type: ${aircraftData.connection.transportType}")
            Text("Connection MAC Address: ${aircraftData.connection.macAddress}")
            Text("Identification 1 UA Type: ${aircraftData.identification1.uaType}")
            Text("Identification 1 ID Type: ${aircraftData.identification1.idType}")
            Text("Identification 1 UAS ID: ${aircraftData.identification1.uasId.contentToString()}")
            // Add more Text composables to display other properties
        }
    }
}