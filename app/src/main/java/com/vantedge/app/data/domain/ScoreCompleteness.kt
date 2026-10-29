package com.vantedge.app.data.domain

import com.vantedge.app.data.model.CompatibilityRecord

object ScoreCompleteness {
    fun isAuthoritative(record: CompatibilityRecord?): Boolean =
        record?.evaluationComplete == true
}
