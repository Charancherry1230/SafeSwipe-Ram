package com.example.safeswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.safeswipe.ui.theme.SafeSwipeTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
// Optional: Uncomment if you plan to test Crashlytics
// import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Firebase Analytics
        firebaseAnalytics = Firebase.analytics

        // ✅ Log a test event to check if Firebase is working
        firebaseAnalytics.logEvent("app_opened", null)

        // ✅ Optional: Log custom crash event for testing Crashlytics (commented)
        // FirebaseCrashlytics.getInstance().log("MainActivity created")
        // FirebaseCrashlytics.getInstance().recordException(Exception("Sample crash event"))

        // ✅ Your existing UI setup
        setContent {
            SafeSwipeTheme {
                SafeSwipeScreen()
            }
        }
    }
}
