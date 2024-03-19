package com.example.dronefaker

import kotlin.random.Random
import java.lang.System as JavaSystem

class DroneSignalGenerator(private val seed: Long? = null) {
    private val random: Random = if (seed != null) Random(seed) else Random.Default

    fun generateAircraftData(): Aircraft {
        return Aircraft(
            macAddress = random.nextLong(),
            connection = generateConnection(),
            identification1 = generateIdentification(),
            identification2 = generateIdentification(),
            id1Shadow = generateIdentification(),
            id2Shadow = generateIdentification(),
            location = generateLocation(),
            authentication = generateAuthentication(),
            selfId = generateSelfId(),
            system = generateSystem(),
            operatorId = generateOperatorId()
        )
    }

    private fun generateConnection(): Connection {
        return Connection(
            rssi = random.nextInt(),
            transportType = "Bluetooth",
            macAddress = "12:34:56:78:90:AB",
            lastSeen = JavaSystem.currentTimeMillis(),
            firstSeen = JavaSystem.currentTimeMillis() - random.nextLong(),
            msgDelta = random.nextLong()
        )
    }

    private fun generateIdentification(): Identification {
        return Identification(
            uaType = UAType.values().random(random),
            idType = IDType.values().random(random),
            uasId = ByteArray(20) { random.nextInt().toByte() }
        )
    }

    private fun generateLocation(): Location {
        return Location(
            status = Status.values().random(random),
            heightType = HeightType.values().random(random),
            direction = random.nextDouble(0.0, 360.0),
            speedHorizontal = random.nextDouble(0.0, 20.0),
            speedVertical = random.nextDouble(-10.0, 10.0),
            latitude = getRandomCoordinate(),
            longitude = getRandomCoordinate(),
            altitudePressure = random.nextDouble(0.0, 1000.0),
            altitudeGeodetic = random.nextDouble(0.0, 1000.0),
            height = random.nextDouble(0.0, 500.0),
            horizontalAccuracy = HorizontalAccuracy.values().random(random),
            verticalAccuracy = VerticalAccuracy.values().random(random),
            baroAccuracy = BaroAccuracy.values().random(random),
            speedAccuracy = SpeedAccuracy.values().random(random),
            locationTimestamp = JavaSystem.currentTimeMillis().toDouble(),
            timeAccuracy = random.nextDouble(0.0, 1.0),
            distance = random.nextFloat()
        )
    }

    private fun generateAuthentication(): Authentication {
        return Authentication(
            authType = AuthType.values().random(random),
            authDataPage = random.nextInt(1, 10),
            authLastPageIndex = random.nextInt(1, 10),
            authLength = random.nextInt(100, 500),
            authTimestamp = JavaSystem.currentTimeMillis(),
            authData = ByteArray(20) { random.nextInt().toByte() }
        )
    }

    private fun generateSelfId(): SelfId {
        return SelfId(
            descriptionType = DescriptionType.values().random(random),
            operationDescription = ByteArray(20) { random.nextInt().toByte() }
        )
    }

    private fun generateSystem(): System {
        return System(
            operatorLocationType = OperatorLocationType.values().random(random),
            classificationType = ClassificationType.values().random(random),
            operatorLatitude = getRandomCoordinate(),
            operatorLongitude = getRandomCoordinate(),
            areaCount = random.nextInt(1, 5),
            areaRadius = random.nextInt(100, 1000),
            areaCeiling = random.nextDouble(500.0, 1000.0),
            areaFloor = random.nextDouble(0.0, 500.0),
            category = Category.values().random(random),
            classValue = ClassValue.values().random(random),
            operatorAltitudeGeo = random.nextDouble(0.0, 1000.0),
            systemTimestamp = JavaSystem.currentTimeMillis()
        )
    }

    private fun generateOperatorId(): OperatorId {
        return OperatorId(
            operatorIdType = random.nextInt(1, 5),
            operatorId = ByteArray(20) { random.nextInt().toByte() }
        )
    }

    private fun getRandomCoordinate(): Double {
        return random.nextDouble(-90.0, 90.0)
    }
}



/*class DroneSignalGenerator {
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
} */