package br.com.jujuhealth.physio

import android.app.Application
import br.com.jujuhealth.physio.di.initKoin
import com.google.firebase.FirebaseApp

class PhysioApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        FirebaseApp.initializeApp(this)
    }
}