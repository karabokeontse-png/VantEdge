package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.JobExtractionResult;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "", "()V", "Extracting", "Failure", "Idle", "Success", "Lcom/vantedge/app/data/viewmodel/JobExtractionState$Extracting;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState$Failure;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState$Idle;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState$Success;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class JobExtractionState {
    public static final int $stable = 0;

    public /* synthetic */ JobExtractionState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/JobExtractionState$Idle;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Idle extends JobExtractionState {
        public static final int $stable = 0;
        public static final Idle INSTANCE = new Idle();

        private Idle() {
            super(null);
        }
    }

    private JobExtractionState() {
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/JobExtractionState$Extracting;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "statusText", "", "(Ljava/lang/String;)V", "getStatusText", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Extracting extends JobExtractionState {
        public static final int $stable = 0;
        private final String statusText;

        public static /* synthetic */ Extracting copy$default(Extracting extracting, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = extracting.statusText;
            }
            return extracting.copy(str);
        }

        /* renamed from: component1, reason: from getter */
        public final String getStatusText() {
            return this.statusText;
        }

        public final Extracting copy(String statusText) {
            Intrinsics.checkNotNullParameter(statusText, "statusText");
            return new Extracting(statusText);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Extracting) && Intrinsics.areEqual(this.statusText, ((Extracting) other).statusText);
        }

        public int hashCode() {
            return this.statusText.hashCode();
        }

        public String toString() {
            return "Extracting(statusText=" + this.statusText + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Extracting(String statusText) {
            super(null);
            Intrinsics.checkNotNullParameter(statusText, "statusText");
            this.statusText = statusText;
        }

        public final String getStatusText() {
            return this.statusText;
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/viewmodel/JobExtractionState$Success;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "result", "Lcom/vantedge/app/data/model/JobExtractionResult;", "(Lcom/vantedge/app/data/model/JobExtractionResult;)V", "getResult", "()Lcom/vantedge/app/data/model/JobExtractionResult;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Success extends JobExtractionState {
        public static final int $stable = 8;
        private final JobExtractionResult result;

        public static /* synthetic */ Success copy$default(Success success, JobExtractionResult jobExtractionResult, int i, Object obj) {
            if ((i & 1) != 0) {
                jobExtractionResult = success.result;
            }
            return success.copy(jobExtractionResult);
        }

        /* renamed from: component1, reason: from getter */
        public final JobExtractionResult getResult() {
            return this.result;
        }

        public final Success copy(JobExtractionResult result) {
            Intrinsics.checkNotNullParameter(result, "result");
            return new Success(result);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Success) && Intrinsics.areEqual(this.result, ((Success) other).result);
        }

        public int hashCode() {
            return this.result.hashCode();
        }

        public String toString() {
            return "Success(result=" + this.result + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Success(JobExtractionResult result) {
            super(null);
            Intrinsics.checkNotNullParameter(result, "result");
            this.result = result;
        }

        public final JobExtractionResult getResult() {
            return this.result;
        }
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/JobExtractionState$Failure;", "Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Failure extends JobExtractionState {
        public static final int $stable = 0;
        private final String message;

        public static /* synthetic */ Failure copy$default(Failure failure, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = failure.message;
            }
            return failure.copy(str);
        }

        /* renamed from: component1, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final Failure copy(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            return new Failure(message);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Failure) && Intrinsics.areEqual(this.message, ((Failure) other).message);
        }

        public int hashCode() {
            return this.message.hashCode();
        }

        public String toString() {
            return "Failure(message=" + this.message + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Failure(String message) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        public final String getMessage() {
            return this.message;
        }
    }
}
