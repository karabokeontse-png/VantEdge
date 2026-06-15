package com.vantedge.app.ui.screens;

import com.vantedge.app.data.model.GenerationCycle;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ResultScreen.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lcom/vantedge/app/ui/screens/ResultScreenMode;", "", "()V", "Historical", "Live", "Lcom/vantedge/app/ui/screens/ResultScreenMode$Historical;", "Lcom/vantedge/app/ui/screens/ResultScreenMode$Live;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class ResultScreenMode {
    public static final int $stable = 0;

    public /* synthetic */ ResultScreenMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: ResultScreen.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/ui/screens/ResultScreenMode$Live;", "Lcom/vantedge/app/ui/screens/ResultScreenMode;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Live extends ResultScreenMode {
        public static final int $stable = 0;
        public static final Live INSTANCE = new Live();

        private Live() {
            super(null);
        }
    }

    private ResultScreenMode() {
    }

    /* compiled from: ResultScreen.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/ui/screens/ResultScreenMode$Historical;", "Lcom/vantedge/app/ui/screens/ResultScreenMode;", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "(Lcom/vantedge/app/data/model/GenerationCycle;)V", "getCycle", "()Lcom/vantedge/app/data/model/GenerationCycle;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Historical extends ResultScreenMode {
        public static final int $stable = 8;
        private final GenerationCycle cycle;

        public static /* synthetic */ Historical copy$default(Historical historical, GenerationCycle generationCycle, int i, Object obj) {
            if ((i & 1) != 0) {
                generationCycle = historical.cycle;
            }
            return historical.copy(generationCycle);
        }

        /* renamed from: component1, reason: from getter */
        public final GenerationCycle getCycle() {
            return this.cycle;
        }

        public final Historical copy(GenerationCycle cycle) {
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            return new Historical(cycle);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Historical) && Intrinsics.areEqual(this.cycle, ((Historical) other).cycle);
        }

        public int hashCode() {
            return this.cycle.hashCode();
        }

        public String toString() {
            return "Historical(cycle=" + this.cycle + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Historical(GenerationCycle cycle) {
            super(null);
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            this.cycle = cycle;
        }

        public final GenerationCycle getCycle() {
            return this.cycle;
        }
    }
}
