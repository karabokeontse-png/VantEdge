package com.vantedge.app.data.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnboardingDraftEntity.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/vantedge/app/data/storage/OnboardingDraftEntity;", "", "id", "", "draftJson", "(Ljava/lang/String;Ljava/lang/String;)V", "getDraftJson", "()Ljava/lang/String;", "getId", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class OnboardingDraftEntity {
    public static final int $stable = 0;
    private final String draftJson;
    private final String id;

    public static /* synthetic */ OnboardingDraftEntity copy$default(OnboardingDraftEntity onboardingDraftEntity, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = onboardingDraftEntity.id;
        }
        if ((i & 2) != 0) {
            str2 = onboardingDraftEntity.draftJson;
        }
        return onboardingDraftEntity.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getDraftJson() {
        return this.draftJson;
    }

    public final OnboardingDraftEntity copy(String id, String draftJson) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(draftJson, "draftJson");
        return new OnboardingDraftEntity(id, draftJson);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OnboardingDraftEntity)) {
            return false;
        }
        OnboardingDraftEntity onboardingDraftEntity = (OnboardingDraftEntity) other;
        return Intrinsics.areEqual(this.id, onboardingDraftEntity.id) && Intrinsics.areEqual(this.draftJson, onboardingDraftEntity.draftJson);
    }

    public int hashCode() {
        return (this.id.hashCode() * 31) + this.draftJson.hashCode();
    }

    public String toString() {
        return "OnboardingDraftEntity(id=" + this.id + ", draftJson=" + this.draftJson + ")";
    }

    public OnboardingDraftEntity(String id, String draftJson) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(draftJson, "draftJson");
        this.id = id;
        this.draftJson = draftJson;
    }

    public /* synthetic */ OnboardingDraftEntity(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "active_draft" : str, str2);
    }

    public final String getId() {
        return this.id;
    }

    public final String getDraftJson() {
        return this.draftJson;
    }
}
