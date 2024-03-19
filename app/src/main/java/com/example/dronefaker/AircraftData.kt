package com.example.dronefaker

data class Aircraft(
    val macAddress: Long,
    val connection: Connection,
    val identification1: Identification,
    val identification2: Identification,
    val id1Shadow: Identification,
    val id2Shadow: Identification,
    val location: Location,
    val authentication: Authentication,
    val selfId: SelfId,
    val system: com.example.dronefaker.System,
    val operatorId: OperatorId
)

data class Connection(
    val rssi: Int,
    val transportType: String,
    val macAddress: String,
    val lastSeen: Long,
    val firstSeen: Long,
    val msgDelta: Long
)

data class Identification(
    val uaType: UAType,
    val idType: IDType,
    val uasId: ByteArray
)

enum class UAType {
    None,
    Aeroplane,
    Helicopter_or_Multirotor,
    Gyroplane,
    Hybrid_Lift,
    Ornithopter,
    Glider,
    Kite,
    Free_balloon,
    Captive_balloon,
    Airship,
    Free_fall_parachute,
    Rocket,
    Tethered_powered_aircraft,
    Ground_obstacle,
    Other
}

enum class IDType {
    None,
    Serial_Number,
    CAA_Registration_ID,
    UTM_Assigned_ID,
    Specific_Session_ID
}

data class Location(
    val status: Status,
    val heightType: HeightType,
    val direction: Double,
    val speedHorizontal: Double,
    val speedVertical: Double,
    val latitude: Double,
    val longitude: Double,
    val altitudePressure: Double,
    val altitudeGeodetic: Double,
    val height: Double,
    val horizontalAccuracy: HorizontalAccuracy,
    val verticalAccuracy: VerticalAccuracy,
    val baroAccuracy: BaroAccuracy,
    val speedAccuracy: SpeedAccuracy,
    val locationTimestamp: Double,
    val timeAccuracy: Double,
    val distance: Float
)

enum class Status {
    Undeclared,
    Ground,
    Airborne,
    Emergency,
    Remote_ID_System_Failure
}

enum class HeightType {
    Takeoff,
    Ground
}

enum class HorizontalAccuracy {
    Unknown,
    kilometers_18_52,
    kilometers_7_408,
    kilometers_3_704,
    kilometers_1_852,
    meters_926,
    meters_555_6,
    meters_185_2,
    meters_92_6,
    meters_30,
    meters_10,
    meters_3,
    meters_1
}

enum class VerticalAccuracy {
    Unknown,
    meters_150,
    meters_45,
    meters_25,
    meters_10,
    meters_3,
    meters_1
}

enum class BaroAccuracy {
    Unknown,
    meters_150,
    meters_45,
    meters_25,
    meters_10,
    meters_3,
    meters_1
}

enum class SpeedAccuracy {
    Unknown,
    meter_per_second_10,
    meter_per_second_3,
    meter_per_second_1,
    meter_per_second_0_3
}

data class Authentication(
    val authType: AuthType,
    val authDataPage: Int,
    val authLastPageIndex: Int,
    val authLength: Int,
    val authTimestamp: Long,
    val authData: ByteArray
)

enum class AuthType {
    None,
    UAS_ID_Signature,
    Operator_ID_Signature,
    Message_Set_Signature,
    Network_Remote_ID,
    Specific_Authentication,
    Private_Use_0xA,
    Private_Use_0xB,
    Private_Use_0xC,
    Private_Use_0xD,
    Private_Use_0xE,
    Private_Use_0xF
}

data class SelfId(
    val descriptionType: DescriptionType,
    val operationDescription: ByteArray
)

enum class DescriptionType {
    Text,
    Emergency,
    Extended_Status,
    Invalid
}

data class System(
    val operatorLocationType: OperatorLocationType,
    val classificationType: ClassificationType,
    val operatorLatitude: Double,
    val operatorLongitude: Double,
    val areaCount: Int,
    val areaRadius: Int,
    val areaCeiling: Double,
    val areaFloor: Double,
    val category: Category,
    val classValue: ClassValue,
    val operatorAltitudeGeo: Double,
    val systemTimestamp: Long
)

enum class OperatorLocationType {
    TakeOff,
    Dynamic,
    Fixed,
    Invalid
}

enum class ClassificationType {
    Undeclared,
    EU
}

enum class Category {
    Undeclared,
    EU_Open,
    EU_Specific,
    EU_Certified
}

enum class ClassValue {
    Undeclared,
    EU_Class_0,
    EU_Class_1,
    EU_Class_2,
    EU_Class_3,
    EU_Class_4,
    EU_Class_5,
    EU_Class_6
}

data class OperatorId(
    val operatorIdType: Int,
    val operatorId: ByteArray
)