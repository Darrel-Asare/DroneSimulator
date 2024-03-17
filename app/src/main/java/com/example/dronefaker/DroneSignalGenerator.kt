package com.example.dronefaker

class DroneSignalGenerator {
    fun generateRemoteIDSignal(): RemoteIDSignal {
        // Implement logic to generate a fake RemoteID signal
        return RemoteIDSignal(
            droneId = "FAKE_DRONE_ID",
            latitude = 37.7749,
            longitude = -122.4194,
            altitude = 100.0,
            speed = 10.0,
            heading = 90.0,
            timestamp = System.currentTimeMillis()
        )
    }
}