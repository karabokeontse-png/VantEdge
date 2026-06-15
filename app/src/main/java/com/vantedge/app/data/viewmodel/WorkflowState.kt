package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/viewmodel/WorkflowState;", "", "()V", "AnalysisOnly", "Complete", "Loading", "ReadyForGeneration", "Lcom/vantedge/app/data/viewmodel/WorkflowState$AnalysisOnly;", "Lcom/vantedge/app/data/viewmodel/WorkflowState$Complete;", "Lcom/vantedge/app/data/viewmodel/WorkflowState$Loading;", "Lcom/vantedge/app/data/viewmodel/WorkflowState$ReadyForGeneration;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class WorkflowState {
    public static final int $stable = 0;

    public /* synthetic */ WorkflowState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/WorkflowState$Loading;", "Lcom/vantedge/app/data/viewmodel/WorkflowState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Loading extends WorkflowState {
        public static final int $stable = 0;
        public static final Loading INSTANCE = new Loading();

        private Loading() {
            super(null);
        }
    }

    private WorkflowState() {
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/WorkflowState$AnalysisOnly;", "Lcom/vantedge/app/data/viewmodel/WorkflowState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnalysisOnly extends WorkflowState {
        public static final int $stable = 0;
        public static final AnalysisOnly INSTANCE = new AnalysisOnly();

        private AnalysisOnly() {
            super(null);
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/WorkflowState$ReadyForGeneration;", "Lcom/vantedge/app/data/viewmodel/WorkflowState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReadyForGeneration extends WorkflowState {
        public static final int $stable = 0;
        public static final ReadyForGeneration INSTANCE = new ReadyForGeneration();

        private ReadyForGeneration() {
            super(null);
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/WorkflowState$Complete;", "Lcom/vantedge/app/data/viewmodel/WorkflowState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Complete extends WorkflowState {
        public static final int $stable = 0;
        public static final Complete INSTANCE = new Complete();

        private Complete() {
            super(null);
        }
    }
}
