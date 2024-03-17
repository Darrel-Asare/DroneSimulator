package com.example.dronefaker

import kotlin.random.Random

class DroneSignalGenerator {
    fun generateRemoteIDSignal(): RemoteIDSignal {
        return RemoteIDSignal(
            droneId = "FAKE_DRONE_ID",
            latitude = getRandomCoordinate(),
            longitude = getRandomCoordinate(),
            altitude = Random.nextDouble(0.0, 500.0),
            speed = Random.nextDouble(0.0, 20.0),
            heading = Random.nextDouble(0.0, 360.0),
            timestamp = System.currentTimeMillis()
        )
    }

    private fun getRandomCoordinate(): Double {
        return Random.nextDouble(-90.0, 90.0)
    }
}