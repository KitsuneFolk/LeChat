package com.pandacorp.lechat.utils

import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.pandacorp.lechat.di.app.App

/**
 * Retrieves package information for a given package name, with optional flags.
 *
 * @param packageName The name of the package for which to retrieve package information.
 * @param flags Additional flags to control the behavior of the method.
 * @return A PackageInfo object containing information about the specified package.
 */
fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        getPackageInfo(packageName, flags)
    }

fun getString(resId: Int) = App.instance.getString(resId)

fun getArray(resId: Int): Array<String> = App.instance.resources.getStringArray(resId)

/**
 * A compatibility wrapper around Bundle's `getParcelableExtra()` method that works on old and new API
 *
 * @param name The name of the extra to retrieve.
 * @param clazz The class of the extra to retrieve.
 * @return The Parcelable extra with the specified name and class, or null if it does not exist.
 */
inline fun <reified T : Parcelable> Bundle.getParcelableExtraSupport(name: String, clazz: Class<T>): T? {
    val extra =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelable(name, clazz)
        } else {
            @Suppress("DEPRECATION")
            getParcelable(name)
                    as? T
        }
    if (extra is T) return extra
    return null
}

/**
 * Takes a key and an argument of the supported types and stores it in the SharedPreferences.
 *
 * Supported types are: String, Int, Float, Long, and Boolean.
 *
 * If the type of the argument is not supported, an IllegalArgumentException is thrown.
 *
 * @param key the key to identify the value in the SharedPreferences
 * @param argument the value to be stored in the SharedPreferences
 * @throws IllegalArgumentException if the type of the argument is not supported
 */
fun SharedPreferences.put(key: String, argument: Any) {
    val editor = this.edit()

    when (argument) {
        is String -> editor.putString(key, argument)
        is Int -> editor.putInt(key, argument)
        is Float -> editor.putFloat(key, argument)
        is Long -> editor.putLong(key, argument)
        is Boolean -> editor.putBoolean(key, argument)
        else -> throw IllegalArgumentException("Unsupported type for SharedPreferences edit operation")
    }

    editor.apply()
}