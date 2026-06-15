package com.vantedge.app.data.model;

import com.vantedge.app.data.model.OnboardingStage;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnboardingDraft.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BK\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\nHÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\fHÆ\u0003JO\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fHÆ\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0007HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015¨\u0006&"}, d2 = {"Lcom/vantedge/app/data/model/OnboardingDraft;", "", "stage", "Lcom/vantedge/app/data/model/OnboardingStage;", "acquisitionMode", "Lcom/vantedge/app/data/model/AcquisitionMode;", "uploadedCvUri", "", "rawExtractedText", "extraction", "Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "editedProfile", "Lcom/vantedge/app/data/model/UserProfile;", "(Lcom/vantedge/app/data/model/OnboardingStage;Lcom/vantedge/app/data/model/AcquisitionMode;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/StructuredProfileExtraction;Lcom/vantedge/app/data/model/UserProfile;)V", "getAcquisitionMode", "()Lcom/vantedge/app/data/model/AcquisitionMode;", "getEditedProfile", "()Lcom/vantedge/app/data/model/UserProfile;", "getExtraction", "()Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "getRawExtractedText", "()Ljava/lang/String;", "getStage", "()Lcom/vantedge/app/data/model/OnboardingStage;", "getUploadedCvUri", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class OnboardingDraft {
    public static final int $stable = 8;
    private final AcquisitionMode acquisitionMode;
    private final UserProfile editedProfile;
    private final transient StructuredProfileExtraction extraction;
    private final String rawExtractedText;
    private final OnboardingStage stage;
    private final String uploadedCvUri;

    public OnboardingDraft() {
        this(null, null, null, null, null, null, 63, null);
    }

    public static /* synthetic */ OnboardingDraft copy$default(OnboardingDraft onboardingDraft, OnboardingStage onboardingStage, AcquisitionMode acquisitionMode, String str, String str2, StructuredProfileExtraction structuredProfileExtraction, UserProfile userProfile, int i, Object obj) {
        if ((i & 1) != 0) {
            onboardingStage = onboardingDraft.stage;
        }
        if ((i & 2) != 0) {
            acquisitionMode = onboardingDraft.acquisitionMode;
        }
        AcquisitionMode acquisitionMode2 = acquisitionMode;
        if ((i & 4) != 0) {
            str = onboardingDraft.uploadedCvUri;
        }
        String str3 = str;
        if ((i & 8) != 0) {
            str2 = onboardingDraft.rawExtractedText;
        }
        String str4 = str2;
        if ((i & 16) != 0) {
            structuredProfileExtraction = onboardingDraft.extraction;
        }
        StructuredProfileExtraction structuredProfileExtraction2 = structuredProfileExtraction;
        if ((i & 32) != 0) {
            userProfile = onboardingDraft.editedProfile;
        }
        return onboardingDraft.copy(onboardingStage, acquisitionMode2, str3, str4, structuredProfileExtraction2, userProfile);
    }

    /* renamed from: component1, reason: from getter */
    public final OnboardingStage getStage() {
        return this.stage;
    }

    /* renamed from: component2, reason: from getter */
    public final AcquisitionMode getAcquisitionMode() {
        return this.acquisitionMode;
    }

    /* renamed from: component3, reason: from getter */
    public final String getUploadedCvUri() {
        return this.uploadedCvUri;
    }

    /* renamed from: component4, reason: from getter */
    public final String getRawExtractedText() {
        return this.rawExtractedText;
    }

    /* renamed from: component5, reason: from getter */
    public final StructuredProfileExtraction getExtraction() {
        return this.extraction;
    }

    /* renamed from: component6, reason: from getter */
    public final UserProfile getEditedProfile() {
        return this.editedProfile;
    }

    public final OnboardingDraft copy(OnboardingStage stage, AcquisitionMode acquisitionMode, String uploadedCvUri, String rawExtractedText, StructuredProfileExtraction extraction, UserProfile editedProfile) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        return new OnboardingDraft(stage, acquisitionMode, uploadedCvUri, rawExtractedText, extraction, editedProfile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OnboardingDraft)) {
            return false;
        }
        OnboardingDraft onboardingDraft = (OnboardingDraft) other;
        return Intrinsics.areEqual(this.stage, onboardingDraft.stage) && this.acquisitionMode == onboardingDraft.acquisitionMode && Intrinsics.areEqual(this.uploadedCvUri, onboardingDraft.uploadedCvUri) && Intrinsics.areEqual(this.rawExtractedText, onboardingDraft.rawExtractedText) && Intrinsics.areEqual(this.extraction, onboardingDraft.extraction) && Intrinsics.areEqual(this.editedProfile, onboardingDraft.editedProfile);
    }

    public int hashCode() {
        return (((((((((this.stage.hashCode() * 31) + (this.acquisitionMode == null ? 0 : this.acquisitionMode.hashCode())) * 31) + (this.uploadedCvUri == null ? 0 : this.uploadedCvUri.hashCode())) * 31) + (this.rawExtractedText == null ? 0 : this.rawExtractedText.hashCode())) * 31) + (this.extraction == null ? 0 : this.extraction.hashCode())) * 31) + (this.editedProfile != null ? this.editedProfile.hashCode() : 0);
    }

    public String toString() {
        return "OnboardingDraft(stage=" + this.stage + ", acquisitionMode=" + this.acquisitionMode + ", uploadedCvUri=" + this.uploadedCvUri + ", rawExtractedText=" + this.rawExtractedText + ", extraction=" + this.extraction + ", editedProfile=" + this.editedProfile + ")";
    }

    public OnboardingDraft(OnboardingStage stage, AcquisitionMode acquisitionMode, String uploadedCvUri, String rawExtractedText, StructuredProfileExtraction extraction, UserProfile editedProfile) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        this.stage = stage;
        this.acquisitionMode = acquisitionMode;
        this.uploadedCvUri = uploadedCvUri;
        this.rawExtractedText = rawExtractedText;
        this.extraction = extraction;
        this.editedProfile = editedProfile;
    }

    public /* synthetic */ OnboardingDraft(OnboardingStage.Welcome welcome, AcquisitionMode acquisitionMode, String str, String str2, StructuredProfileExtraction structuredProfileExtraction, UserProfile userProfile, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? OnboardingStage.Welcome.INSTANCE : welcome, (i & 2) != 0 ? null : acquisitionMode, (i & 4) != 0 ? null : str, (i & 8) != 0 ? null : str2, (i & 16) != 0 ? null : structuredProfileExtraction, (i & 32) == 0 ? userProfile : null);
    }

    public final OnboardingStage getStage() {
        return this.stage;
    }

    public final AcquisitionMode getAcquisitionMode() {
        return this.acquisitionMode;
    }

    public final String getUploadedCvUri() {
        return this.uploadedCvUri;
    }

    public final String getRawExtractedText() {
        return this.rawExtractedText;
    }

    public final StructuredProfileExtraction getExtraction() {
        return this.extraction;
    }

    public final UserProfile getEditedProfile() {
        return this.editedProfile;
    }
}
