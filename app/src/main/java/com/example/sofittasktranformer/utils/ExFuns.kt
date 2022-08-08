package com.example.sofittasktranformer.utils

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.example.sofittasktranformer.BuildConfig
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.stream.Collectors.toMap
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties


/**
 * @author Umer Bilal
 * Created 7/18/2022 at 2:59 PM
 */

fun Activity.isLocationProviderEnabled() = let {
    val locationManager: LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager

    when {
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
            Pair(true, LocationManager.GPS_PROVIDER)
        }
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
            Pair(true, LocationManager.NETWORK_PROVIDER)
        }
        else -> {
            Pair(false, "")
        }
    }
}
fun Fragment.showToasts(msg: String){
    requireContext().showToast(msg)
}
fun Context.showToast(msg: String) {
    if (BuildConfig.DEBUG) {

    }
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatSpinner.getSelectedText():String{
    return selectedItem as String
}
fun AppCompatSpinner.getSelectedTextInt():Int{
    return (selectedItem as String).toInt()
}

inline fun <reified T> T.showLog(msg: String) {
    Log.e(this!!::class.java.name, msg)
}

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "SalatTimings"
)

fun <T : Any> ObjecttoMap(obj: T): Map<String, Any?> {
    return (obj::class as KClass<T>).memberProperties.associate { prop ->
        prop.name to prop.get(obj)?.let { value ->
            if (value::class.isData) {
                ObjecttoMap(value)
            } else {
                value
            }
        }
    }
}


