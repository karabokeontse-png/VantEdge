package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnboardingViewModel.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "", "()V", "Extracting", "Failure", "Idle", "Retrying", "Success", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Extracting;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Failure;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Idle;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Retrying;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Success;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public abstract class OnboardingExtractionState {
    public static final int $stable = 0;

    public /* synthetic */ OnboardingExtractionState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: OnboardingViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Idle;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Idle extends OnboardingExtractionState {
        public static final int $stable = 0;
        public static final Idle INSTANCE = new Idle();

        private Idle() {
            super(null);
        }
    }

    private OnboardingExtractionState() {
    }

    /* compiled from: OnboardingViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Extracting;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "statusText", "", "(Ljava/lang/String;)V", "getStatusText", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Extracting extends OnboardingExtractionState {
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

    /* compiled from: OnboardingViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Retrying;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "statusText", "", "(Ljava/lang/String;)V", "getStatusText", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Retrying extends OnboardingExtractionState {
        public static final int $stable = 0;
        private final String statusText;

        public static /* synthetic */ Retrying copy$default(Retrying retrying, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = retrying.statusText;
            }
            return retrying.copy(str);
        }

        /* renamed from: component1, reason: from getter */
        public final String getStatusText() {
            return this.statusText;
        }

        public final Retrying copy(String statusText) {
            Intrinsics.checkNotNullParameter(statusText, "statusText");
            return new Retrying(statusText);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Retrying) && Intrinsics.areEqual(this.statusText, ((Retrying) other).statusText);
        }

        public int hashCode() {
            return this.statusText.hashCode();
        }

        public String toString() {
            return "Retrying(statusText=" + this.statusText + ")";
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Retrying(String statusText) {
            super(null);
            Intrinsics.checkNotNullParameter(statusText, "statusText");
            this.statusText = statusText;
        }

        public final String getStatusText() {
            return this.statusText;
        }
    }

    /* compiled from: OnboardingViewModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Success;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "()V", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Success extends OnboardingExtractionState {
        public static final int $stable = 0;
        public static final Success INSTANCE = new Success();

        private Success() {
            super(null);
        }
    }

    /* compiled from: OnboardingViewModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState$Failure;", "Lcom/vantedge/app/data/viewmodel/OnboardingExtractionState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Failure extends OnboardingExtractionState {
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
