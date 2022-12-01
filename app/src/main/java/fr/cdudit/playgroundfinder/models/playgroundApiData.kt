package fr.cdudit.playgroundfinder.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaygroundApi(
    @SerializedName("records") val records: List<Record>
) : Serializable

data class Record(
    @SerializedName("datasetid")        val datasetId: String,
    @SerializedName("fields")           val fields: Fields,
    @SerializedName("geometry")         val geometry: Geometry,
    @SerializedName("record_timestamp") val recordTimestamp: String,
    @SerializedName("recordid")         val recordId: String,
    var isFavorite: Boolean = false
) : Serializable {
    fun getGoogleMapsUri(): String {
        val lat = this.fields.geoPoint2d[0]
        val long = this.fields.geoPoint2d[1]
        return "https://maps.google.com/maps?q=loc:${lat},${long}"
    }
}

data class Fields(
    @SerializedName("age_max")      val ageMax: Double,
    @SerializedName("age_min")      val ageMin: Double,
    @SerializedName("geo_point_2d") val geoPoint2d: List<Double>,
    @SerializedName("gid")          val gid: Int,
    @SerializedName("gml_id")       val gmlId: String,
    @SerializedName("nb_jeux")      val gameNumber: Int,
    @SerializedName("nom_site")     val siteName: String,
    @SerializedName("surface")      val surface: Double
) : Serializable

data class Geometry(
    @SerializedName("coordinates")  val coordinates: List<Double>,
    @SerializedName("type")         val type: String
) : Serializable