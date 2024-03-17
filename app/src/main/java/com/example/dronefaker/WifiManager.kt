package com.example.dronefaker

import android.content.Context
import com.example.dronefaker.RemoteIDSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.Socket


class WifiManager(private val context: Context) {
    private val serverAddress = "192.168.0.1" // Example server address
    private val serverPort = 1234 // Example server port

    fun broadcastSignal(remoteIDSignal: RemoteIDSignal) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                var socket: Socket? = null
                var writer: OutputStreamWriter? = null

                try {
                    socket = Socket(serverAddress, serverPort)
                    val outputStream = socket.getOutputStream()
                    writer = OutputStreamWriter(outputStream)

                    // Send RemoteID signal data over the socket
                    writer.write(remoteIDSignal.toString())
                    writer.flush()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    // Close the writer and socket
                    try {
                        writer?.close()
                        socket?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}