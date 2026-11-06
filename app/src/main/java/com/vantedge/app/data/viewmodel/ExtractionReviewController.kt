package com.vantedge.app.data.viewmodel

import com.vantedge.app.data.model.JobExtractionResult
import com.vantedge.app.domain.extraction.ExtractionOutcome
import com.vantedge.app.domain.extraction.ExtractionReview
import com.vantedge.app.domain.extraction.toExtractionReviewOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Per-ViewModel review gate. A pending review blocks downstream use until
 * [acknowledge] is called by an explicit user action.
 */
class ExtractionReviewController {

    private val _review = MutableStateFlow<ExtractionReview?>(null)
    val review: StateFlow<ExtractionReview?> = _review

    val current: ExtractionReview?
        get() = _review.value

    val canProceed: Boolean
        get() = _review.value == null

    fun present(outcome: ExtractionOutcome) {
        _review.value = outcome.toExtractionReviewOrNull()
    }

    fun acknowledge(corrected: JobExtractionResult): JobExtractionResult {
        _review.value = null
        return corrected
    }

    fun clear() {
        _review.value = null
    }
}
