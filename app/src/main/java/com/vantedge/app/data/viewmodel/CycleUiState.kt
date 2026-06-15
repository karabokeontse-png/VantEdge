package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.domain.PipelineStep;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0006\t\n\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState;", "", "()V", "AnalysisDone", "Error", "GenerationReady", "Idle", "Loading", "Success", "Lcom/vantedge/app/data/viewmodel/CycleUiState$AnalysisDone;", "Lcom/vantedge/app/data/viewmodel/CycleUiState$Error;", "Lcom/vantedge/app/data/viewmodel/CycleUiState$GenerationReady;", "Lcom/vantedge/app/data/viewmodel/CycleUiState$Idle;", "Lcom/vantedge/app/data/viewmodel/CycleUiState$Loading;", "Lcom/vantedge/app/data/viewmodel/CycleUiState$Success;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class CycleUiState {
    public static final int $stable = 0;

    public /* synthetic */ CycleUiState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState$Idle;", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Idle extends CycleUiState {
        public static final int $stable = 0;
        public static final Idle INSTANCE = new Idle();

        private Idle() {
            super(null);
        }
    }

    private CycleUiState() {
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState$Loading;", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "step", "Lcom/vantedge/app/domain/PipelineStep;", "(Lcom/vantedge/app/domain/PipelineStep;)V", "getStep", "()Lcom/vantedge/app/domain/PipelineStep;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Loading extends CycleUiState {
        public static final int $stable = 0;
        private final PipelineStep step;

        /* JADX WARN: Multi-variable type inference failed */
        public Loading() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public static /* synthetic */ Loading copy$default(Loading loading, PipelineStep pipelineStep, int i, Object obj) {
            if ((i & 1) != 0) {
                pipelineStep = loading.step;
            }
            return loading.copy(pipelineStep);
        }

        /* renamed from: component1, reason: from getter */
        public final PipelineStep getStep() {
            return this.step;
        }

        public final Loading copy(PipelineStep step) {
            Intrinsics.checkNotNullParameter(step, "step");
            return new Loading(step);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Loading) && this.step == ((Loading) other).step;
        }

        public int hashCode() {
            return this.step.hashCode();
        }

        public String toString() {
            return "Loading(step=" + this.step + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Loading(PipelineStep step) {
            super(null);
            Intrinsics.checkNotNullParameter(step, "step");
            this.step = step;
        }

        public /* synthetic */ Loading(PipelineStep pipelineStep, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? PipelineStep.ANALYSING : pipelineStep);
        }

        public final PipelineStep getStep() {
            return this.step;
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState$AnalysisDone;", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "(Lcom/vantedge/app/data/model/GenerationCycle;)V", "getCycle", "()Lcom/vantedge/app/data/model/GenerationCycle;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class AnalysisDone extends CycleUiState {
        public static final int $stable = 8;
        private final GenerationCycle cycle;

        public static /* synthetic */ AnalysisDone copy$default(AnalysisDone analysisDone, GenerationCycle generationCycle, int i, Object obj) {
            if ((i & 1) != 0) {
                generationCycle = analysisDone.cycle;
            }
            return analysisDone.copy(generationCycle);
        }

        /* renamed from: component1, reason: from getter */
        public final GenerationCycle getCycle() {
            return this.cycle;
        }

        public final AnalysisDone copy(GenerationCycle cycle) {
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            return new AnalysisDone(cycle);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof AnalysisDone) && Intrinsics.areEqual(this.cycle, ((AnalysisDone) other).cycle);
        }

        public int hashCode() {
            return this.cycle.hashCode();
        }

        public String toString() {
            return "AnalysisDone(cycle=" + this.cycle + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnalysisDone(GenerationCycle cycle) {
            super(null);
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            this.cycle = cycle;
        }

        public final GenerationCycle getCycle() {
            return this.cycle;
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState$GenerationReady;", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "(Lcom/vantedge/app/data/model/GenerationCycle;)V", "getCycle", "()Lcom/vantedge/app/data/model/GenerationCycle;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class GenerationReady extends CycleUiState {
        public static final int $stable = 8;
        private final GenerationCycle cycle;

        public static /* synthetic */ GenerationReady copy$default(GenerationReady generationReady, GenerationCycle generationCycle, int i, Object obj) {
            if ((i & 1) != 0) {
                generationCycle = generationReady.cycle;
            }
            return generationReady.copy(generationCycle);
        }

        /* renamed from: component1, reason: from getter */
        public final GenerationCycle getCycle() {
            return this.cycle;
        }

        public final GenerationReady copy(GenerationCycle cycle) {
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            return new GenerationReady(cycle);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof GenerationReady) && Intrinsics.areEqual(this.cycle, ((GenerationReady) other).cycle);
        }

        public int hashCode() {
            return this.cycle.hashCode();
        }

        public String toString() {
            return "GenerationReady(cycle=" + this.cycle + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GenerationReady(GenerationCycle cycle) {
            super(null);
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            this.cycle = cycle;
        }

        public final GenerationCycle getCycle() {
            return this.cycle;
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState$Success;", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "(Lcom/vantedge/app/data/model/GenerationCycle;)V", "getCycle", "()Lcom/vantedge/app/data/model/GenerationCycle;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Success extends CycleUiState {
        public static final int $stable = 8;
        private final GenerationCycle cycle;

        public static /* synthetic */ Success copy$default(Success success, GenerationCycle generationCycle, int i, Object obj) {
            if ((i & 1) != 0) {
                generationCycle = success.cycle;
            }
            return success.copy(generationCycle);
        }

        /* renamed from: component1, reason: from getter */
        public final GenerationCycle getCycle() {
            return this.cycle;
        }

        public final Success copy(GenerationCycle cycle) {
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            return new Success(cycle);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Success) && Intrinsics.areEqual(this.cycle, ((Success) other).cycle);
        }

        public int hashCode() {
            return this.cycle.hashCode();
        }

        public String toString() {
            return "Success(cycle=" + this.cycle + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Success(GenerationCycle cycle) {
            super(null);
            Intrinsics.checkNotNullParameter(cycle, "cycle");
            this.cycle = cycle;
        }

        public final GenerationCycle getCycle() {
            return this.cycle;
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleUiState$Error;", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Error extends CycleUiState {
        public static final int $stable = 0;
        private final String message;

        public static /* synthetic */ Error copy$default(Error error, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = error.message;
            }
            return error.copy(str);
        }

        /* renamed from: component1, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final Error copy(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            return new Error(message);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Error) && Intrinsics.areEqual(this.message, ((Error) other).message);
        }

        public int hashCode() {
            return this.message.hashCode();
        }

        public String toString() {
            return "Error(message=" + this.message + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Error(String message) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        public final String getMessage() {
            return this.message;
        }
    }
}
