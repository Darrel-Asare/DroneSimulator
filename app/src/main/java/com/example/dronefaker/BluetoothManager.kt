package com.example.dronefaker

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.UUID

class BluetoothManager(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    private val broadcastSockets = mutableMapOf<BluetoothDevice, BluetoothSocket>()

    fun broadcastSignal(aircraftData: AircraftData) {
        if (hasBluetoothPermission() && hasBluetoothConnectPermission()) {
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            if (pairedDevices != null) {
                pairedDevices.forEach { device ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // Handle the case when BLUETOOTH_CONNECT permission is not granted
                        // You can request the permission or show an error message to the user
                    } else {
                        try {
                            val socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
                            socket.connect()
                            broadcastSockets[device] = socket

                            // Send Aircraft data over the socket
                            val outputStream = socket.outputStream
                            val objectOutputStream = ObjectOutputStream(outputStream)
                            objectOutputStream.writeObject(aircraftData.toString())
                            objectOutputStream.flush()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            } else {
                // Handle the case when there are no paired devices
            }
        } else {
            // Handle the case when Bluetooth or BLUETOOTH_CONNECT permission is not granted
            // You can request the permissions or show an error message to the user
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the BLUETOOTH_CONNECT permission
                // You can use the EasyPermissions library or implement your own permission handling logic
            }
        }
    }

    fun stopBroadcasting() {
        broadcastSockets.values.forEach { socket ->
            try {
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        broadcastSockets.clear()
    }

    private fun hasBluetoothPermission(): Boolean {
        return context.packageManager.checkPermission(
            android.Manifest.permission.BLUETOOTH,
            context.packageName
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasBluetoothConnectPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.packageManager.checkPermission(
                Manifest.permission.BLUETOOTH_CONNECT,
                context.packageName
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // For devices running Android 11 or lower, the permission is not required
        }
    }
}