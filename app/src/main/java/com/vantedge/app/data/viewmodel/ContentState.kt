package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.CompatibilityRecord;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b \b\u0087\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\f¢\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0006HÆ\u0003J\t\u0010 \u001a\u00020\bHÆ\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\bHÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010\"\u001a\u0004\u0018\u00010\bHÆ\u0003¢\u0006\u0002\u0010\u0016J\t\u0010#\u001a\u00020\fHÆ\u0003J\t\u0010$\u001a\u00020\fHÆ\u0003J\t\u0010%\u001a\u00020\fHÆ\u0003Jl\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\fHÆ\u0001¢\u0006\u0002\u0010'J\u0013\u0010(\u001a\u00020\f2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\bHÖ\u0001J\t\u0010+\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0015\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000e\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0019R\u0011\u0010\r\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0019R\u0015\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006,"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ContentState;", "", "cvContent", "", "coverLetterContent", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "score", "", "previousScore", "delta", "hasDocs", "", "isCvCorruptedJson", "isCoverLetterCorruptedJson", "(Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/CompatibilityRecord;ILjava/lang/Integer;Ljava/lang/Integer;ZZZ)V", "getCompatibility", "()Lcom/vantedge/app/data/model/CompatibilityRecord;", "getCoverLetterContent", "()Ljava/lang/String;", "getCvContent", "getDelta", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getHasDocs", "()Z", "getPreviousScore", "getScore", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/CompatibilityRecord;ILjava/lang/Integer;Ljava/lang/Integer;ZZZ)Lcom/vantedge/app/data/viewmodel/ContentState;", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final /* data */ class ContentState {
    public static final int $stable = 8;
    private final CompatibilityRecord compatibility;
    private final String coverLetterContent;
    private final String cvContent;
    private final Integer delta;
    private final boolean hasDocs;
    private final boolean isCoverLetterCorruptedJson;
    private final boolean isCvCorruptedJson;
    private final Integer previousScore;
    private final int score;

    /* renamed from: component1, reason: from getter */
    public final String getCvContent() {
        return this.cvContent;
    }

    /* renamed from: component2, reason: from getter */
    public final String getCoverLetterContent() {
        return this.coverLetterContent;
    }

    /* renamed from: component3, reason: from getter */
    public final CompatibilityRecord getCompatibility() {
        return this.compatibility;
    }

    /* renamed from: component4, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    /* renamed from: component5, reason: from getter */
    public final Integer getPreviousScore() {
        return this.previousScore;
    }

    /* renamed from: component6, reason: from getter */
    public final Integer getDelta() {
        return this.delta;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getHasDocs() {
        return this.hasDocs;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getIsCvCorruptedJson() {
        return this.isCvCorruptedJson;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean getIsCoverLetterCorruptedJson() {
        return this.isCoverLetterCorruptedJson;
    }

    public final ContentState copy(String cvContent, String coverLetterContent, CompatibilityRecord compatibility, int score, Integer previousScore, Integer delta, boolean hasDocs, boolean isCvCorruptedJson, boolean isCoverLetterCorruptedJson) {
        Intrinsics.checkNotNullParameter(cvContent, "cvContent");
        Intrinsics.checkNotNullParameter(coverLetterContent, "coverLetterContent");
        Intrinsics.checkNotNullParameter(compatibility, "compatibility");
        return new ContentState(cvContent, coverLetterContent, compatibility, score, previousScore, delta, hasDocs, isCvCorruptedJson, isCoverLetterCorruptedJson);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ContentState)) {
            return false;
        }
        ContentState contentState = (ContentState) other;
        return Intrinsics.areEqual(this.cvContent, contentState.cvContent) && Intrinsics.areEqual(this.coverLetterContent, contentState.coverLetterContent) && Intrinsics.areEqual(this.compatibility, contentState.compatibility) && this.score == contentState.score && Intrinsics.areEqual(this.previousScore, contentState.previousScore) && Intrinsics.areEqual(this.delta, contentState.delta) && this.hasDocs == contentState.hasDocs && this.isCvCorruptedJson == contentState.isCvCorruptedJson && this.isCoverLetterCorruptedJson == contentState.isCoverLetterCorruptedJson;
    }

    public int hashCode() {
        return (((((((((((((((this.cvContent.hashCode() * 31) + this.coverLetterContent.hashCode()) * 31) + this.compatibility.hashCode()) * 31) + Integer.hashCode(this.score)) * 31) + (this.previousScore == null ? 0 : this.previousScore.hashCode())) * 31) + (this.delta != null ? this.delta.hashCode() : 0)) * 31) + Boolean.hashCode(this.hasDocs)) * 31) + Boolean.hashCode(this.isCvCorruptedJson)) * 31) + Boolean.hashCode(this.isCoverLetterCorruptedJson);
    }

    public String toString() {
        return "ContentState(cvContent=" + this.cvContent + ", coverLetterContent=" + this.coverLetterContent + ", compatibility=" + this.compatibility + ", score=" + this.score + ", previousScore=" + this.previousScore + ", delta=" + this.delta + ", hasDocs=" + this.hasDocs + ", isCvCorruptedJson=" + this.isCvCorruptedJson + ", isCoverLetterCorruptedJson=" + this.isCoverLetterCorruptedJson + ")";
    }

    public ContentState(String cvContent, String coverLetterContent, CompatibilityRecord compatibility, int score, Integer previousScore, Integer delta, boolean hasDocs, boolean isCvCorruptedJson, boolean isCoverLetterCorruptedJson) {
        Intrinsics.checkNotNullParameter(cvContent, "cvContent");
        Intrinsics.checkNotNullParameter(coverLetterContent, "coverLetterContent");
        Intrinsics.checkNotNullParameter(compatibility, "compatibility");
        this.cvContent = cvContent;
        this.coverLetterContent = coverLetterContent;
        this.compatibility = compatibility;
        this.score = score;
        this.previousScore = previousScore;
        this.delta = delta;
        this.hasDocs = hasDocs;
        this.isCvCorruptedJson = isCvCorruptedJson;
        this.isCoverLetterCorruptedJson = isCoverLetterCorruptedJson;
    }

    public final String getCvContent() {
        return this.cvContent;
    }

    public final String getCoverLetterContent() {
        return this.coverLetterContent;
    }

    public final CompatibilityRecord getCompatibility() {
        return this.compatibility;
    }

    public final int getScore() {
        return this.score;
    }

    public final Integer getPreviousScore() {
        return this.previousScore;
    }

    public final Integer getDelta() {
        return this.delta;
    }

    public final boolean getHasDocs() {
        return this.hasDocs;
    }

    public final boolean isCvCorruptedJson() {
        return this.isCvCorruptedJson;
    }

    public final boolean isCoverLetterCorruptedJson() {
        return this.isCoverLetterCorruptedJson;
    }
}
