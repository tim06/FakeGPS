package com.tim.feature.gmschecker

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class GmsCheckerImpl(
    private val context: Context, private val googleApiAvailability: GoogleApiAvailability
) : GmsChecker {
    override fun isPlayServicesAvailable() =
        googleApiAvailability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
}