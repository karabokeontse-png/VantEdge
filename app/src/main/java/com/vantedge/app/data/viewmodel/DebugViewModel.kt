package com.vantedge.app.data.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;
import com.vantedge.app.data.model.TelemetryRecord;
import com.vantedge.app.data.storage.TelemetryStorage;
import com.vantedge.app.util.TelemetryExportService;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: DebugViewmodel.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001dR\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\f0\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0019R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/vantedge/app/data/viewmodel/DebugViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "telemetryStorage", "Lcom/vantedge/app/data/storage/TelemetryStorage;", "(Landroid/content/Context;Lcom/vantedge/app/data/storage/TelemetryStorage;)V", "_exportEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Landroid/content/Intent;", "_isExporting", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_records", "", "Lcom/vantedge/app/data/model/TelemetryRecord;", "appContext", "exportEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getExportEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "exportService", "Lcom/vantedge/app/util/TelemetryExportService;", "isExporting", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "records", "getRecords", "exportTelemetry", "", "refreshRecords", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class DebugViewModel extends ViewModel {
    private static final String TAG = "DebugViewModel";
    private final MutableSharedFlow<Intent> _exportEvent;
    private final MutableStateFlow<Boolean> _isExporting;
    private final MutableStateFlow<List<TelemetryRecord>> _records;
    private final Context appContext;
    private final SharedFlow<Intent> exportEvent;
    private final TelemetryExportService exportService;
    private final StateFlow<Boolean> isExporting;
    private final StateFlow<List<TelemetryRecord>> records;
    private final TelemetryStorage telemetryStorage;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int $stable = 8;

    public DebugViewModel(Context context, TelemetryStorage telemetryStorage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(telemetryStorage, "telemetryStorage");
        this.telemetryStorage = telemetryStorage;
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.appContext = applicationContext;
        this.exportService = new TelemetryExportService(this.appContext, this.telemetryStorage);
        this._records = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.records = FlowKt.asStateFlow(this._records);
        this._exportEvent = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5, null);
        this.exportEvent = FlowKt.asSharedFlow(this._exportEvent);
        this._isExporting = StateFlowKt.MutableStateFlow(false);
        this.isExporting = FlowKt.asStateFlow(this._isExporting);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    /* compiled from: DebugViewmodel.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/viewmodel/DebugViewModel$Companion;", "", "()V", "TAG", "", "factory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "context", "Landroid/content/Context;", "telemetryStorage", "Lcom/vantedge/app/data/storage/TelemetryStorage;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ViewModelProvider.Factory factory(final Context context, final TelemetryStorage telemetryStorage) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(telemetryStorage, "telemetryStorage");
            return new ViewModelProvider.Factory() { // from class: com.vantedge.app.data.viewmodel.DebugViewModel$Companion$factory$1
                @Override // androidx.lifecycle.ViewModelProvider.Factory
                public <T extends ViewModel> T create(Class<T> modelClass) {
                    Intrinsics.checkNotNullParameter(modelClass, "modelClass");
                    Context applicationContext = context.getApplicationContext();
                    Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                    return new DebugViewModel(applicationContext, telemetryStorage);
                }
            };
        }
    }

    public final StateFlow<List<TelemetryRecord>> getRecords() {
        return this.records;
    }

    public final SharedFlow<Intent> getExportEvent() {
        return this.exportEvent;
    }

    public final StateFlow<Boolean> isExporting() {
        return this.isExporting;
    }

    /* compiled from: DebugViewmodel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.vantedge.app.data.viewmodel.DebugViewModel$1", f = "DebugViewmodel.kt", i = {}, l = {118}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.vantedge.app.data.viewmodel.DebugViewModel$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return DebugViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) {
            AnonymousClass1 anonymousClass1;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    anonymousClass1 = this;
                    Log.i(DebugViewModel.TAG, "[DebugViewModel] Init — loading recent records.");
                    anonymousClass1.label = 1;
                    Object recentRecords$default = TelemetryStorage.getRecentRecords$default(DebugViewModel.this.telemetryStorage, 0, anonymousClass1, 1, null);
                    if (recentRecords$default != coroutine_suspended) {
                        $result = recentRecords$default;
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                case 1:
                    ResultKt.throwOnFailure($result);
                    anonymousClass1 = this;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            List parsed = (List) $result;
            DebugViewModel.this._records.setValue(parsed);
            Log.i(DebugViewModel.TAG, "[DebugViewModel] Init complete — loaded " + parsed.size() + " records.");
            return Unit.INSTANCE;
        }
    }

    public final void refreshRecords() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new DebugViewModel$refreshRecords$1(this, null), 3, null);
    }

    public final void exportTelemetry() {
        if (!this._isExporting.getValue().booleanValue()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new DebugViewModel$exportTelemetry$1(this, null), 3, null);
        } else {
            Log.w(TAG, "[DebugViewModel] Export already in progress — ignoring call.");
        }
    }
}
