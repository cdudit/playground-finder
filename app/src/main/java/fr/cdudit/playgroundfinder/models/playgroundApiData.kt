package fr.cdudit.playgroundfinder.models

import com.google.gson.annotations.SerializedName

data class PlaygroundApi(
    @SerializedName("nhits")        val hitsNumber: Int,
    @SerializedName("parameters")   val parameters: Parameters,
    @SerializedName("records")      val records: List<Record>
)

data class Parameters(
    @SerializedName("dataset")  val dataset: String,
    @SerializedName("format")   val format: String,
    @SerializedName("rows")     val rows: Int,
    @SerializedName("start")    val start: Int,
    @SerializedName("timezone") val timezone: String
)

data class Record(
    @SerializedName("datasetid")        val datasetId: String,
    @SerializedName("fields")           val fields: Fields,
    @SerializedName("geometry")         val geometry: Geometry,
    @SerializedName("record_timestamp") val recordTimestamp: String,
    @SerializedName("recordid")         val recordId: String
)

data class Fields(
    @SerializedName("age_max")      val ageMax: Int,
    @SerializedName("age_min")      val ageMin: Double,
    @SerializedName("geo_point_2d") val geoPoint2d: List<Double>,
    @SerializedName("geo_shape")    val geoShape: GeoShape,
    @SerializedName("gid")          val gid: Int,
    @SerializedName("gml_id")       val gmlId: String,
    @SerializedName("nb_jeux")      val gameNumber: Int,
    @SerializedName("nom_site")     val siteName: String,
    @SerializedName("surface")      val surface: Double
)

data class Geometry(
    @SerializedName("coordinates")  val coordinates: List<Double>,
    @SerializedName("type")         val type: String
)

data class GeoShape(
    @SerializedName("coordinates")  val coordinates: List<List<List<Double>>>,
    @SerializedName("type")         val type: String
)