package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ExportState;", "", "()V", "Exporting", "Idle", "Ready", "Lcom/vantedge/app/data/viewmodel/ExportState$Exporting;", "Lcom/vantedge/app/data/viewmodel/ExportState$Idle;", "Lcom/vantedge/app/data/viewmodel/ExportState$Ready;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class ExportState {
    public static final int $stable = 0;

    public /* synthetic */ ExportState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ExportState$Idle;", "Lcom/vantedge/app/data/viewmodel/ExportState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Idle extends ExportState {
        public static final int $stable = 0;
        public static final Idle INSTANCE = new Idle();

        private Idle() {
            super(null);
        }
    }

    private ExportState() {
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ExportState$Exporting;", "Lcom/vantedge/app/data/viewmodel/ExportState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Exporting extends ExportState {
        public static final int $stable = 0;
        public static final Exporting INSTANCE = new Exporting();

        private Exporting() {
            super(null);
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ExportState$Ready;", "Lcom/vantedge/app/data/viewmodel/ExportState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Ready extends ExportState {
        public static final int $stable = 0;
        public static final Ready INSTANCE = new Ready();

        private Ready() {
            super(null);
        }
    }
}
