package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CompatibilityRecord.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0006HÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001c"}, d2 = {"Lcom/vantedge/app/data/model/RelevancyItem;", "", "name", "", "type", "matchPercent", "", "aiDescription", "relevancyGroup", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getAiDescription", "()Ljava/lang/String;", "getMatchPercent", "()I", "getName", "getRelevancyGroup", "getType", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class RelevancyItem {
    public static final int $stable = 0;
    private final String aiDescription;
    private final int matchPercent;
    private final String name;
    private final String relevancyGroup;
    private final String type;

    public static /* synthetic */ RelevancyItem copy$default(RelevancyItem relevancyItem, String str, String str2, int i, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = relevancyItem.name;
        }
        if ((i2 & 2) != 0) {
            str2 = relevancyItem.type;
        }
        String str5 = str2;
        if ((i2 & 4) != 0) {
            i = relevancyItem.matchPercent;
        }
        int i3 = i;
        if ((i2 & 8) != 0) {
            str3 = relevancyItem.aiDescription;
        }
        String str6 = str3;
        if ((i2 & 16) != 0) {
            str4 = relevancyItem.relevancyGroup;
        }
        return relevancyItem.copy(str, str5, i3, str6, str4);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* renamed from: component3, reason: from getter */
    public final int getMatchPercent() {
        return this.matchPercent;
    }

    /* renamed from: component4, reason: from getter */
    public final String getAiDescription() {
        return this.aiDescription;
    }

    /* renamed from: component5, reason: from getter */
    public final String getRelevancyGroup() {
        return this.relevancyGroup;
    }

    public final RelevancyItem copy(String name, String type, int matchPercent, String aiDescription, String relevancyGroup) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(aiDescription, "aiDescription");
        Intrinsics.checkNotNullParameter(relevancyGroup, "relevancyGroup");
        return new RelevancyItem(name, type, matchPercent, aiDescription, relevancyGroup);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RelevancyItem)) {
            return false;
        }
        RelevancyItem relevancyItem = (RelevancyItem) other;
        return Intrinsics.areEqual(this.name, relevancyItem.name) && Intrinsics.areEqual(this.type, relevancyItem.type) && this.matchPercent == relevancyItem.matchPercent && Intrinsics.areEqual(this.aiDescription, relevancyItem.aiDescription) && Intrinsics.areEqual(this.relevancyGroup, relevancyItem.relevancyGroup);
    }

    public int hashCode() {
        return (((((((this.name.hashCode() * 31) + this.type.hashCode()) * 31) + Integer.hashCode(this.matchPercent)) * 31) + this.aiDescription.hashCode()) * 31) + this.relevancyGroup.hashCode();
    }

    public String toString() {
        return "RelevancyItem(name=" + this.name + ", type=" + this.type + ", matchPercent=" + this.matchPercent + ", aiDescription=" + this.aiDescription + ", relevancyGroup=" + this.relevancyGroup + ")";
    }

    public RelevancyItem(String name, String type, int matchPercent, String aiDescription, String relevancyGroup) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(aiDescription, "aiDescription");
        Intrinsics.checkNotNullParameter(relevancyGroup, "relevancyGroup");
        this.name = name;
        this.type = type;
        this.matchPercent = matchPercent;
        this.aiDescription = aiDescription;
        this.relevancyGroup = relevancyGroup;
    }

    public final String getName() {
        return this.name;
    }

    public final String getType() {
        return this.type;
    }

    public final int getMatchPercent() {
        return this.matchPercent;
    }

    public final String getAiDescription() {
        return this.aiDescription;
    }

    public final String getRelevancyGroup() {
        return this.relevancyGroup;
    }
}
