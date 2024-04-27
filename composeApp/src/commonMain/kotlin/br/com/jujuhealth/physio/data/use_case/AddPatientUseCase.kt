package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.User
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import dev.icerock.moko.resources.StringResource

class AddPatientUseCase(
    private val serviceAuth: ServiceAuthContract
) {
    suspend fun run(
        patientToAdd: Patient,
        pwd: String,
        confirmPwd: String,
        userPwd: String,
        success: (Patient) -> Unit,
        error: (errorMessage: StringResource?) -> Unit
    ) {
        lateinit var userToUpdate: User
        lateinit var addedPatient: Patient
        if (validateField(patientToAdd, pwd, confirmPwd)) {
            lateinit var userEmail: String
            serviceAuth.getUser({
                userEmail = it?.email.orEmpty()
            }, {
                error.invoke(MR.strings.general_error_message)
            })
            serviceAuth.signIn(userEmail, userPwd, {}, {
                error.invoke(MR.strings.invalid_pwd)
            })
            serviceAuth.createPatient(name = patientToAdd.name.orEmpty(),
                email = patientToAdd.email.orEmpty(),
                userPwd = userPwd,
                password = pwd,
                success = { patient, user ->
                    userToUpdate = user
                    addedPatient = patient
                },
                error = {
                    error.invoke(MR.strings.general_error_message)
                })

            if (addedPatient.uId.isNullOrEmpty().not() &&
                userToUpdate.patients.contains(addedPatient.uId).not()
            ) {
                addedPatient.uId?.let { uid -> userToUpdate.patients.add(uid) }
                serviceAuth.updateUser(userToUpdate, { _ ->
                    success.invoke(addedPatient)
                }, {
                    error.invoke(MR.strings.general_error_message)
                })
            } else {
                error.invoke(MR.strings.general_error_message)
            }

        } else {
            error.invoke(MR.strings.invalid_fields_error_message)
        }
    }

    private fun validateField(
        patient: Patient?, pwd: String?, confirmPwd: String?
    ): Boolean =
        (pwd?.isEmpty() != false || patient?.name?.isEmpty() != false || patient.email?.isEmpty() != false || pwd != confirmPwd).not()

}