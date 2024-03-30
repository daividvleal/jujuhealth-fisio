package br.com.jujuhealth.physio.di

import br.com.jujuhealth.physio.data.request.auth.ServiceAuth
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.context.startKoin
import org.koin.dsl.module

val networkModule = module {

    single{
        Firebase.auth
    }

    single {
        Firebase.firestore.setSettings(
            persistenceEnabled = true
        )
    }

}

val repositoryModule = module {
    single<ServiceAuthContract> { ServiceAuth(get(), get()) }
}

val screenModelsModule = module {
    factory {  }
}

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            repositoryModule,
            screenModelsModule,
        )
    }
}