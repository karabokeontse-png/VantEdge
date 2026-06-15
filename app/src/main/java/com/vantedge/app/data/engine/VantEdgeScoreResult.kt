package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ScoreEngine.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/vantedge/app/data/engine/VantEdgeScoreResult;", "", "score", "", "interpretation", "", "(ILjava/lang/String;)V", "getInterpretation", "()Ljava/lang/String;", "getScore", "()I", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class VantEdgeScoreResult {
    public static final int $stable = 0;
    private final String interpretation;
    private final int score;

    public static /* synthetic */ VantEdgeScoreResult copy$default(VantEdgeScoreResult vantEdgeScoreResult, int i, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = vantEdgeScoreResult.score;
        }
        if ((i2 & 2) != 0) {
            str = vantEdgeScoreResult.interpretation;
        }
        return vantEdgeScoreResult.copy(i, str);
    }

    /* renamed from: component1, reason: from getter */
    public final int getScore() {
        return this.score;
    }

    /* renamed from: component2, reason: from getter */
    public final String getInterpretation() {
        return this.interpretation;
    }

    public final VantEdgeScoreResult copy(int score, String interpretation) {
        Intrinsics.checkNotNullParameter(interpretation, "interpretation");
        return new VantEdgeScoreResult(score, interpretation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VantEdgeScoreResult)) {
            return false;
        }
        VantEdgeScoreResult vantEdgeScoreResult = (VantEdgeScoreResult) other;
        return this.score == vantEdgeScoreResult.score && Intrinsics.areEqual(this.interpretation, vantEdgeScoreResult.interpretation);
    }

    public int hashCode() {
        return (Integer.hashCode(this.score) * 31) + this.interpretation.hashCode();
    }

    public String toString() {
        return "VantEdgeScoreResult(score=" + this.score + ", interpretation=" + this.interpretation + ")";
    }

    public VantEdgeScoreResult(int score, String interpretation) {
        Intrinsics.checkNotNullParameter(interpretation, "interpretation");
        this.score = score;
        this.interpretation = interpretation;
    }

    public final int getScore() {
        return this.score;
    }

    public final String getInterpretation() {
        return this.interpretation;
    }
}
