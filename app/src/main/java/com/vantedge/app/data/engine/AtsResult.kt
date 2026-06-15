package com.vantedge.app.data.engine;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AtsEngine.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lcom/vantedge/app/data/engine/AtsResult;", "", "score", "", "keywords", "", "", "missingKeywords", "(ILjava/util/List;Ljava/util/List;)V", "getKeywords", "()Ljava/util/List;", "getMissingKeywords", "getScore", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class AtsResult {
    public static final int $stable = 8;
    private final List<String> keywords;
    private final List<String> missingKeywords;
    private final int score;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AtsResult copy$default(AtsResult atsResult, int i, List list, List list2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = atsResult.score;
        }
        if ((i2 & 2) != 0) {
            list = atsResult.keywords;
        }
        if ((i2 & 4) != 0) {
            list2 = atsResult.missingKeywords;
        }
        return atsResult.copy(i, list, list2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    public final List<String> component2() {
        return this.keywords;
    }

    public final List<String> component3() {
        return this.missingKeywords;
    }

    public final AtsResult copy(int score, List<String> keywords, List<String> missingKeywords) {
        Intrinsics.checkNotNullParameter(keywords, "keywords");
        Intrinsics.checkNotNullParameter(missingKeywords, "missingKeywords");
        return new AtsResult(score, keywords, missingKeywords);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AtsResult)) {
            return false;
        }
        AtsResult atsResult = (AtsResult) other;
        return this.score == atsResult.score && Intrinsics.areEqual(this.keywords, atsResult.keywords) && Intrinsics.areEqual(this.missingKeywords, atsResult.missingKeywords);
    }

    public int hashCode() {
        return (((Integer.hashCode(this.score) * 31) + this.keywords.hashCode()) * 31) + this.missingKeywords.hashCode();
    }

    public String toString() {
        return "AtsResult(score=" + this.score + ", keywords=" + this.keywords + ", missingKeywords=" + this.missingKeywords + ")";
    }

    public AtsResult(int score, List<String> keywords, List<String> missingKeywords) {
        Intrinsics.checkNotNullParameter(keywords, "keywords");
        Intrinsics.checkNotNullParameter(missingKeywords, "missingKeywords");
        this.score = score;
        this.keywords = keywords;
        this.missingKeywords = missingKeywords;
    }

    public final int getScore() {
        return this.score;
    }

    public final List<String> getKeywords() {
        return this.keywords;
    }

    public final List<String> getMissingKeywords() {
        return this.missingKeywords;
    }
}
