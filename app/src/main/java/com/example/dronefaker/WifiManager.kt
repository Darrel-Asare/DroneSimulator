package com.example.dronefaker

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.ObjectOutputStream
import java.net.Socket

class WifiManager(private val context: Context) {
    private val serverAddress = "192.168.0.1" // Example server address
    private val serverPort = 1234 // Example server port

    fun broadcastSignal(aircraftData: AircraftData) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                var socket: Socket? = null
                var outputStream: ObjectOutputStream? = null

                try {
                    socket = Socket(serverAddress, serverPort)
                    outputStream = ObjectOutputStream(socket.getOutputStream())

                    // Send Aircraft data over the socket
                    outputStream.writeObject(aircraftData.toString())
                    outputStream.flush()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    // Close the output stream and socket
                    try {
                        outputStream?.close()
                        socket?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}