package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleNavEvent;", "", "()V", "ToAnalysisResult", "ToCycleRestored", "ToDesignPicker", "ToFinalResult", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToAnalysisResult;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToCycleRestored;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToDesignPicker;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToFinalResult;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class CycleNavEvent {
    public static final int $stable = 0;

    public /* synthetic */ CycleNavEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToAnalysisResult;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ToAnalysisResult extends CycleNavEvent {
        public static final int $stable = 0;
        public static final ToAnalysisResult INSTANCE = new ToAnalysisResult();

        private ToAnalysisResult() {
            super(null);
        }
    }

    private CycleNavEvent() {
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToDesignPicker;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ToDesignPicker extends CycleNavEvent {
        public static final int $stable = 0;
        public static final ToDesignPicker INSTANCE = new ToDesignPicker();

        private ToDesignPicker() {
            super(null);
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToFinalResult;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ToFinalResult extends CycleNavEvent {
        public static final int $stable = 0;
        public static final ToFinalResult INSTANCE = new ToFinalResult();

        private ToFinalResult() {
            super(null);
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleNavEvent$ToCycleRestored;", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent;", "cycleId", "", "stage", "Lcom/vantedge/app/data/viewmodel/CycleStage;", "(Ljava/lang/String;Lcom/vantedge/app/data/viewmodel/CycleStage;)V", "getCycleId", "()Ljava/lang/String;", "getStage", "()Lcom/vantedge/app/data/viewmodel/CycleStage;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ToCycleRestored extends CycleNavEvent {
        public static final int $stable = 0;
        private final String cycleId;
        private final CycleStage stage;

        public static /* synthetic */ ToCycleRestored copy$default(ToCycleRestored toCycleRestored, String str, CycleStage cycleStage, int i, Object obj) {
            if ((i & 1) != 0) {
                str = toCycleRestored.cycleId;
            }
            if ((i & 2) != 0) {
                cycleStage = toCycleRestored.stage;
            }
            return toCycleRestored.copy(str, cycleStage);
        }

        /* renamed from: component1, reason: from getter */
        public final String getCycleId() {
            return this.cycleId;
        }

        /* renamed from: component2, reason: from getter */
        public final CycleStage getStage() {
            return this.stage;
        }

        public final ToCycleRestored copy(String cycleId, CycleStage stage) {
            Intrinsics.checkNotNullParameter(cycleId, "cycleId");
            Intrinsics.checkNotNullParameter(stage, "stage");
            return new ToCycleRestored(cycleId, stage);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ToCycleRestored)) {
                return false;
            }
            ToCycleRestored toCycleRestored = (ToCycleRestored) other;
            return Intrinsics.areEqual(this.cycleId, toCycleRestored.cycleId) && this.stage == toCycleRestored.stage;
        }

        public int hashCode() {
            return (this.cycleId.hashCode() * 31) + this.stage.hashCode();
        }

        public String toString() {
            return "ToCycleRestored(cycleId=" + this.cycleId + ", stage=" + this.stage + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ToCycleRestored(String cycleId, CycleStage stage) {
            super(null);
            Intrinsics.checkNotNullParameter(cycleId, "cycleId");
            Intrinsics.checkNotNullParameter(stage, "stage");
            this.cycleId = cycleId;
            this.stage = stage;
        }

        public final String getCycleId() {
            return this.cycleId;
        }

        public final CycleStage getStage() {
            return this.stage;
        }
    }
}
