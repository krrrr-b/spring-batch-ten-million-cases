package com.skeleton.batch.validator

import org.springframework.batch.core.JobParametersValidator
import kotlin.Throws
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParameters
import org.springframework.stereotype.Component

@Component
class StringJobParametersValidator : JobParametersValidator {
    @Throws(JobParametersInvalidException::class)
    override fun validate(parameters: JobParameters?) {
        parameters!!.getString("date") ?: throw JobParametersInvalidException("date is required")
    }
}
