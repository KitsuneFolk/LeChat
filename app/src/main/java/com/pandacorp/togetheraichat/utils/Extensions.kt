package com.pandacorp.togetheraichat.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.pandacorp.togetheraichat.di.app.App

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