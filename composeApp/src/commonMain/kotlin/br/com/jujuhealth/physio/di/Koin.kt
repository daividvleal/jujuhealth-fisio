package br.com.jujuhealth.physio.di

import br.com.jujuhealth.physio.data.request.auth.ServiceAuthImpl
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import br.com.jujuhealth.physio.data.request.patient.ServicePatientContract
import br.com.jujuhealth.physio.data.request.patient.ServicePatientImpl
import br.com.jujuhealth.physio.data.use_case.AddPatientUseCase
import br.com.jujuhealth.physio.data.use_case.GetUserUseCase
import br.com.jujuhealth.physio.data.use_case.LoadPatientDiaryUseCase
import br.com.jujuhealth.physio.data.use_case.LoadPatientsUseCase
import br.com.jujuhealth.physio.data.use_case.PutPatientDiaryFeedbackUseCase
import br.com.jujuhealth.physio.data.use_case.SignInUseCase
import br.com.jujuhealth.physio.ui.add_patient.AddPatientScreenModel
import br.com.jujuhealth.physio.ui.details.patient.PatientDetailsScreenModel
import br.com.jujuhealth.physio.ui.details.training.TrainingDetailsScreenModel
import br.com.jujuhealth.physio.ui.home.HomeScreenModel
import br.com.jujuhealth.physio.ui.login.LoginScreenModel
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
    single<ServiceAuthContract> { ServiceAuthImpl(get(), get()) }
    single<ServicePatientContract> { ServicePatientImpl(get()) }
}

val useCaseModule = module {
    factory { SignInUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { LoadPatientsUseCase(get()) }
    factory { LoadPatientDiaryUseCase(get()) }
    factory { PutPatientDiaryFeedbackUseCase(get()) }
    factory { AddPatientUseCase(get()) }
}

val screenModelsModule = module {
    factory { LoginScreenModel(get()) }
    factory { HomeScreenModel(get(), get()) }
    factory { PatientDetailsScreenModel(get()) }
    factory { TrainingDetailsScreenModel(get()) }
    factory { AddPatientScreenModel(get()) }
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