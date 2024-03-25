package br.com.jujuhealth.physio

import android.app.Application
import com.google.firebase.FirebaseApp

class PhysioApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}