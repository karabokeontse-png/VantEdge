package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lcom/vantedge/app/data/viewmodel/RestorationState;", "", "()V", "Loading", "None", "Restored", "Lcom/vantedge/app/data/viewmodel/RestorationState$Loading;", "Lcom/vantedge/app/data/viewmodel/RestorationState$None;", "Lcom/vantedge/app/data/viewmodel/RestorationState$Restored;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class RestorationState {
    public static final int $stable = 0;

    public /* synthetic */ RestorationState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/RestorationState$Loading;", "Lcom/vantedge/app/data/viewmodel/RestorationState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Loading extends RestorationState {
        public static final int $stable = 0;
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    private RestorationState() {
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/RestorationState$None;", "Lcom/vantedge/app/data/viewmodel/RestorationState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class None extends RestorationState {
        public static final int $stable = 0;
        public static final None INSTANCE = new None();

        private None() {
            super(null);
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/RestorationState$Restored;", "Lcom/vantedge/app/data/viewmodel/RestorationState;", "cycleId", "", "(Ljava/lang/String;)V", "getCycleId", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Restored extends RestorationState {
        public static final int $stable = 0;
        private final String cycleId;

        public static /* synthetic */ Restored copy$default(Restored restored, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = restored.cycleId;
            }
            return restored.copy(str);
        }

        /* renamed from: component1, reason: from getter */
        public final String getCycleId() {
            return this.cycleId;
        }

        public final Restored copy(String cycleId) {
            Intrinsics.checkNotNullParameter(cycleId, "cycleId");
            return new Restored(cycleId);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Restored) && Intrinsics.areEqual(this.cycleId, ((Restored) other).cycleId);
        }

        public int hashCode() {
            return this.cycleId.hashCode();
        }

        public String toString() {
            return "Restored(cycleId=" + this.cycleId + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Restored(String cycleId) {
            super(null);
            Intrinsics.checkNotNullParameter(cycleId, "cycleId");
            this.cycleId = cycleId;
        }

        public final String getCycleId() {
            return this.cycleId;
        }
    }
}
