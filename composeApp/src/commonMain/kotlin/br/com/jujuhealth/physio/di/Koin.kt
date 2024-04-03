package br.com.jujuhealth.physio.di

import br.com.jujuhealth.physio.data.request.auth.ServiceAuth
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import br.com.jujuhealth.physio.data.use_case.ServiceAuthUseCase
import br.com.jujuhealth.physio.ui.auth.AuthScreenModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.context.startKoin
import org.koin.dsl.module

val networkModule = module {

    single<FirebaseAuth> {
        Firebase.auth
    }

    single<FirebaseFirestore> {
        Firebase.firestore.setSettings(
            persistenceEnabled = true
        )
        Firebase.firestore
    }

}

val repositoryModule = module {
    single<ServiceAuthContract> { ServiceAuth(get(), get()) }
}

val useCaseModule = module {
    factory { ServiceAuthUseCase(get()) }
}

val screenModelsModule = module {
    factory { AuthScreenModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            repositoryModule,
            useCaseModule,
            screenModelsModule,
        )
    }
}