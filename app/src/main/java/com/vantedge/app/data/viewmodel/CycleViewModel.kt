package com.vantedge.app.data.viewmodel;

import android.util.Log;
import androidx.lifecycle.CoroutineLiveDataKt;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.domain.DocumentExportUseCase;
import com.vantedge.app.data.engine.JobExtractionEngine;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.DesignConfig;
import com.vantedge.app.data.model.GapItem;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.model.GenerationMode;
import com.vantedge.app.data.model.JobSourceType;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.storage.HistoryStore;
import com.vantedge.app.data.viewmodel.CycleUiState;
import com.vantedge.app.data.viewmodel.ExportState;
import com.vantedge.app.data.viewmodel.JobExtractionState;
import com.vantedge.app.data.viewmodel.RestorationState;
import com.vantedge.app.data.viewmodel.WorkflowState;
import com.vantedge.app.domain.OptimizationOrchestrator;
import com.vantedge.app.domain.PipelineStep;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.bouncycastle.i18n.TextBundle;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000Ô\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001f\b\u0007\u0018\u0000 \u0095\u00012\u00020\u0001:\u0002\u0095\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZJ\r\u0010[\u001a\u0004\u0018\u00010\\¢\u0006\u0002\u0010]J\b\u0010^\u001a\u0004\u0018\u00010\rJ\u0006\u0010_\u001a\u00020XJ\u000e\u0010`\u001a\u00020XH\u0082@¢\u0006\u0002\u0010aJ\u0006\u0010b\u001a\u00020XJ\u000e\u0010c\u001a\u00020XH\u0086@¢\u0006\u0002\u0010aJ\u000e\u0010d\u001a\u00020E2\u0006\u0010e\u001a\u00020\u000bJ\u0006\u0010f\u001a\u00020XJ\u000e\u0010g\u001a\u00020X2\u0006\u0010h\u001a\u00020\rJ \u0010i\u001a\b\u0012\u0004\u0012\u00020k0j2\u0006\u0010l\u001a\u00020m2\b\u0010n\u001a\u0004\u0018\u00010oH\u0002J\u0014\u0010p\u001a\u0004\u0018\u00010o2\b\u0010e\u001a\u0004\u0018\u00010\u000bH\u0002J\u0012\u0010q\u001a\u00020m2\b\u0010e\u001a\u0004\u0018\u00010\u000bH\u0002J\u0006\u0010r\u001a\u00020XJ\u0006\u0010s\u001a\u00020XJ\u0016\u0010t\u001a\u00020X2\u0006\u0010u\u001a\u00020\r2\u0006\u0010v\u001a\u00020wJ \u0010x\u001a\u0004\u0018\u00010\u000b2\u0006\u0010y\u001a\u00020\r2\u0006\u0010z\u001a\u00020\r2\u0006\u0010{\u001a\u00020\rJ\u0014\u0010|\u001a\b\u0012\u0004\u0012\u00020\r0-2\u0006\u0010e\u001a\u00020\u000bJ\u0015\u0010}\u001a\u0004\u0018\u00010\\2\u0006\u0010e\u001a\u00020\u000b¢\u0006\u0002\u0010~J\u0015\u0010\u007f\u001a\u0004\u0018\u00010\\2\u0006\u0010e\u001a\u00020\u000b¢\u0006\u0002\u0010~J\u000f\u0010\u0080\u0001\u001a\u00020X2\u0006\u0010e\u001a\u00020\u000bJ\u0007\u0010\u0081\u0001\u001a\u00020\\J\t\u0010\u0082\u0001\u001a\u00020EH\u0002J/\u0010\u0083\u0001\u001a\u00020E2\b\u0010e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010S\u001a\u00020\u001d2\u0007\u0010\u0084\u0001\u001a\u00020\u001b2\t\u0010\u0085\u0001\u001a\u0004\u0018\u00010\u000bH\u0002J\u000f\u0010\u0086\u0001\u001a\u00020X2\u0006\u0010e\u001a\u00020\u000bJ\u0007\u0010\u0087\u0001\u001a\u00020XJ\u0007\u0010\u0088\u0001\u001a\u00020XJ\u000f\u0010\u0089\u0001\u001a\u00020X2\u0006\u0010e\u001a\u00020\u000bJ\u0007\u0010\u008a\u0001\u001a\u00020XJ=\u0010\u008b\u0001\u001a\u00020X2\u0007\u0010\u008c\u0001\u001a\u00020R2\u0006\u0010y\u001a\u00020\r2\u0006\u0010z\u001a\u00020\r2\u0006\u0010{\u001a\u00020\r2\u0007\u0010\u008d\u0001\u001a\u00020&2\n\b\u0002\u00107\u001a\u0004\u0018\u00010\rJ=\u0010\u008e\u0001\u001a\u00020X2\u0007\u0010\u008c\u0001\u001a\u00020R2\u0006\u0010y\u001a\u00020\r2\u0006\u0010z\u001a\u00020\r2\u0006\u0010{\u001a\u00020\r2\u0007\u0010\u008d\u0001\u001a\u00020&2\n\b\u0002\u00107\u001a\u0004\u0018\u00010\rJ\u0018\u0010\u008f\u0001\u001a\u00020X2\u0006\u0010e\u001a\u00020\u000bH\u0082@¢\u0006\u0003\u0010\u0090\u0001J\u000f\u0010\u0091\u0001\u001a\u00020X2\u0006\u0010e\u001a\u00020\u000bJ\u0007\u0010\u0092\u0001\u001a\u00020\\J\u0010\u0010\u0093\u0001\u001a\u00020X2\u0007\u0010\u0094\u0001\u001a\u00020\rR\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u001e\u001a\u0004\u0018\u00010\u000b8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001e\u0010'\u001a\u00020&2\u0006\u0010%\u001a\u00020&@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\r0\"¢\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u001d\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0-0\"¢\u0006\b\n\u0000\u001a\u0004\b.\u0010$R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001000¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0017\u00103\u001a\b\u0012\u0004\u0012\u00020\u00120\"¢\u0006\b\n\u0000\u001a\u0004\b4\u0010$R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\"¢\u0006\b\n\u0000\u001a\u0004\b6\u0010$R\"\u00107\u001a\u0004\u0018\u00010\r2\b\u0010%\u001a\u0004\u0018\u00010\r@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u000e\u0010:\u001a\u00020;X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00170\"¢\u0006\b\n\u0000\u001a\u0004\b=\u0010$R\u0017\u0010>\u001a\b\u0012\u0004\u0012\u00020\u001900¢\u0006\b\n\u0000\u001a\u0004\b?\u00102R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010@\u001a\u0004\u0018\u00010AX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010B\u001a\b\u0012\u0004\u0012\u00020\u001b0\"¢\u0006\b\n\u0000\u001a\u0004\bC\u0010$R\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020E0\"¢\u0006\b\n\u0000\u001a\u0004\bF\u0010$R\u001a\u0010G\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u00109\"\u0004\bI\u0010JR\u001a\u0010K\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u00109\"\u0004\bM\u0010JR\u001a\u0010N\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u00109\"\u0004\bP\u0010JR\u0010\u0010Q\u001a\u0004\u0018\u00010RX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010S\u001a\b\u0012\u0004\u0012\u00020\u001d0\"¢\u0006\b\n\u0000\u001a\u0004\bT\u0010$R\u001d\u0010U\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0-0\"¢\u0006\b\n\u0000\u001a\u0004\bV\u0010$¨\u0006\u0096\u0001"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleViewModel;", "Landroidx/lifecycle/ViewModel;", "orchestrator", "Lcom/vantedge/app/domain/OptimizationOrchestrator;", "historyStore", "Lcom/vantedge/app/data/storage/HistoryStore;", "exportUseCase", "Lcom/vantedge/app/data/domain/DocumentExportUseCase;", "(Lcom/vantedge/app/domain/OptimizationOrchestrator;Lcom/vantedge/app/data/storage/HistoryStore;Lcom/vantedge/app/data/domain/DocumentExportUseCase;)V", "_currentCycle", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/vantedge/app/data/model/GenerationCycle;", "_customImprovementInstructions", "", "_exportEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/vantedge/app/data/viewmodel/ExportEvent;", "_exportState", "Lcom/vantedge/app/data/viewmodel/ExportState;", "_extractionToken", "", "_improvementBaseCycle", "_jobExtractionState", "Lcom/vantedge/app/data/viewmodel/JobExtractionState;", "_navEvent", "Lcom/vantedge/app/data/viewmodel/CycleNavEvent;", "_restorationState", "Lcom/vantedge/app/data/viewmodel/RestorationState;", "_uiState", "Lcom/vantedge/app/data/viewmodel/CycleUiState;", "currentCycle", "getCurrentCycle", "()Lcom/vantedge/app/data/model/GenerationCycle;", "currentCycleFlow", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentCycleFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "<set-?>", "Lcom/vantedge/app/data/model/GenerationMode;", "currentMode", "getCurrentMode", "()Lcom/vantedge/app/data/model/GenerationMode;", "customImprovementInstructions", "getCustomImprovementInstructions", "cyclesFlow", "", "getCyclesFlow", "exportEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getExportEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "exportState", "getExportState", "improvementBaseCycle", "getImprovementBaseCycle", "improvementContext", "getImprovementContext", "()Ljava/lang/String;", "jobExtractionEngine", "Lcom/vantedge/app/data/engine/JobExtractionEngine;", "jobExtractionState", "getJobExtractionState", "navEvent", "getNavEvent", "pipelineJob", "Lkotlinx/coroutines/Job;", "restorationState", "getRestorationState", "resultViewState", "Lcom/vantedge/app/data/viewmodel/ResultViewState;", "getResultViewState", "savedCompany", "getSavedCompany", "setSavedCompany", "(Ljava/lang/String;)V", "savedJobDescription", "getSavedJobDescription", "setSavedJobDescription", "savedJobTitle", "getSavedJobTitle", "setSavedJobTitle", "savedProfile", "Lcom/vantedge/app/data/model/UserProfile;", "uiState", "getUiState", "visibleCyclesFlow", "getVisibleCyclesFlow", "applyDesign", "", "design", "Lcom/vantedge/app/data/model/DesignConfig;", "averageScore", "", "()Ljava/lang/Integer;", "bestScoringRole", "cancelPipeline", "clearActiveCycle", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "commitCurrentCycle", "commitCurrentCycleAndWait", "computeSnapshotViewState", "cycle", "continueToGeneration", "deleteCycle", "id", "deriveActions", "", "Lcom/vantedge/app/data/viewmodel/ActionType;", "workflow", "Lcom/vantedge/app/data/viewmodel/WorkflowState;", "content", "Lcom/vantedge/app/data/viewmodel/ContentState;", "deriveContentState", "deriveWorkflowState", "dismissCurrentCycle", "exportCurrentCycle", "extractJobFields", "rawText", "sourceType", "Lcom/vantedge/app/data/model/JobSourceType;", "findExistingCycleForJob", "jobTitle", "company", "jobDescription", "getGapsForCycle", "getPreviousScore", "(Lcom/vantedge/app/data/model/GenerationCycle;)Ljava/lang/Integer;", "getScoreForCycle", "improveFromCycle", "improvedApplicationsCount", "initialLoadingState", "mapToResultViewState", "restoration", "improvementBase", "openCycle", "resetExtractionState", "resetState", "restoreCycle", "retryPipeline", "runAnalysis", Scopes.PROFILE, "mode", "runPipeline", "setActiveCycle", "(Lcom/vantedge/app/data/model/GenerationCycle;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startImproveFromCycle", "totalSavedApplications", "updateCustomInstructions", TextBundle.TEXT_ENTRY, "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class CycleViewModel extends ViewModel {
    private static final String TAG = "CycleViewModel";
    private final MutableStateFlow<GenerationCycle> _currentCycle;
    private final MutableStateFlow<String> _customImprovementInstructions;
    private final MutableSharedFlow<ExportEvent> _exportEvents;
    private final MutableStateFlow<ExportState> _exportState;
    private final MutableStateFlow<Long> _extractionToken;
    private final MutableStateFlow<GenerationCycle> _improvementBaseCycle;
    private final MutableStateFlow<JobExtractionState> _jobExtractionState;
    private final MutableSharedFlow<CycleNavEvent> _navEvent;
    private final MutableStateFlow<RestorationState> _restorationState;
    private final MutableStateFlow<CycleUiState> _uiState;
    private final StateFlow<GenerationCycle> currentCycleFlow;
    private GenerationMode currentMode;
    private final StateFlow<String> customImprovementInstructions;
    private final StateFlow<List<GenerationCycle>> cyclesFlow;
    private final SharedFlow<ExportEvent> exportEvents;
    private final StateFlow<ExportState> exportState;
    private final DocumentExportUseCase exportUseCase;
    private final HistoryStore historyStore;
    private final StateFlow<GenerationCycle> improvementBaseCycle;
    private String improvementContext;
    private final JobExtractionEngine jobExtractionEngine;
    private final StateFlow<JobExtractionState> jobExtractionState;
    private final SharedFlow<CycleNavEvent> navEvent;
    private final OptimizationOrchestrator orchestrator;
    private Job pipelineJob;
    private final StateFlow<RestorationState> restorationState;
    private final StateFlow<ResultViewState> resultViewState;
    private String savedCompany;
    private String savedJobDescription;
    private String savedJobTitle;
    private UserProfile savedProfile;
    private final StateFlow<CycleUiState> uiState;
    private final StateFlow<List<GenerationCycle>> visibleCyclesFlow;
    public static final int $stable = 8;

    public CycleViewModel(OptimizationOrchestrator orchestrator, HistoryStore historyStore, DocumentExportUseCase exportUseCase) {
        Intrinsics.checkNotNullParameter(orchestrator, "orchestrator");
        Intrinsics.checkNotNullParameter(historyStore, "historyStore");
        Intrinsics.checkNotNullParameter(exportUseCase, "exportUseCase");
        this.orchestrator = orchestrator;
        this.historyStore = historyStore;
        this.exportUseCase = exportUseCase;
        Log.e(TAG, "VM_INSTANCE=" + hashCode());
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
        this._uiState = StateFlowKt.MutableStateFlow(CycleUiState.Idle.INSTANCE);
        this.uiState = this._uiState;
        this._navEvent = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5, null);
        this.navEvent = FlowKt.asSharedFlow(this._navEvent);
        this.cyclesFlow = this.historyStore.getCyclesFlow();
        this.visibleCyclesFlow = this.historyStore.getVisibleCyclesFlow();
        this._currentCycle = StateFlowKt.MutableStateFlow(null);
        this.currentCycleFlow = FlowKt.asStateFlow(this._currentCycle);
        this._restorationState = StateFlowKt.MutableStateFlow(RestorationState.Loading.INSTANCE);
        this.restorationState = FlowKt.asStateFlow(this._restorationState);
        this._improvementBaseCycle = StateFlowKt.MutableStateFlow(null);
        this.improvementBaseCycle = FlowKt.asStateFlow(this._improvementBaseCycle);
        this._customImprovementInstructions = StateFlowKt.MutableStateFlow("");
        this.customImprovementInstructions = FlowKt.asStateFlow(this._customImprovementInstructions);
        this.jobExtractionEngine = new JobExtractionEngine();
        this._jobExtractionState = StateFlowKt.MutableStateFlow(JobExtractionState.Idle.INSTANCE);
        this.jobExtractionState = FlowKt.asStateFlow(this._jobExtractionState);
        this._extractionToken = StateFlowKt.MutableStateFlow(0L);
        this._exportState = StateFlowKt.MutableStateFlow(ExportState.Idle.INSTANCE);
        this.exportState = FlowKt.asStateFlow(this._exportState);
        this._exportEvents = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 6, null);
        this.exportEvents = FlowKt.asSharedFlow(this._exportEvents);
        this.resultViewState = FlowKt.stateIn(FlowKt.combine(this._currentCycle, this._uiState, this._restorationState, this._improvementBaseCycle, new CycleViewModel$resultViewState$1(this, null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(SharingStarted.INSTANCE, CoroutineLiveDataKt.DEFAULT_TIMEOUT, 0L, 2, null), initialLoadingState());
        this.currentMode = GenerationMode.NEW_APPLICATION;
        this.savedJobTitle = "";
        this.savedCompany = "";
        this.savedJobDescription = "";
    }

    /* compiled from: CycleViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$1", f = "CycleViewModel.kt", i = {}, l = {146}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.vantedge.app.data.viewmodel.CycleViewModel$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CycleViewModel.this.new AnonymousClass1(continuation);
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
                    anonymousClass1.label = 1;
                    Object activeCycle = CycleViewModel.this.historyStore.getActiveCycle(anonymousClass1);
                    if (activeCycle != coroutine_suspended) {
                        $result = activeCycle;
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
            GenerationCycle active = (GenerationCycle) $result;
            if (active != null) {
                CycleViewModel.this._currentCycle.setValue(active);
                CycleViewModel.this._restorationState.setValue(new RestorationState.Restored(active.getId()));
            } else {
                CycleViewModel.this._restorationState.setValue(RestorationState.None.INSTANCE);
            }
            return Unit.INSTANCE;
        }
    }

    public final StateFlow<CycleUiState> getUiState() {
        return this.uiState;
    }

    public final SharedFlow<CycleNavEvent> getNavEvent() {
        return this.navEvent;
    }

    public final StateFlow<List<GenerationCycle>> getCyclesFlow() {
        return this.cyclesFlow;
    }

    public final StateFlow<List<GenerationCycle>> getVisibleCyclesFlow() {
        return this.visibleCyclesFlow;
    }

    public final StateFlow<GenerationCycle> getCurrentCycleFlow() {
        return this.currentCycleFlow;
    }

    public final StateFlow<RestorationState> getRestorationState() {
        return this.restorationState;
    }

    public final StateFlow<GenerationCycle> getImprovementBaseCycle() {
        return this.improvementBaseCycle;
    }

    public final StateFlow<String> getCustomImprovementInstructions() {
        return this.customImprovementInstructions;
    }

    public final StateFlow<JobExtractionState> getJobExtractionState() {
        return this.jobExtractionState;
    }

    public final StateFlow<ExportState> getExportState() {
        return this.exportState;
    }

    public final SharedFlow<ExportEvent> getExportEvents() {
        return this.exportEvents;
    }

    public final StateFlow<ResultViewState> getResultViewState() {
        return this.resultViewState;
    }

    private final ResultViewState initialLoadingState() {
        return new ResultViewState(null, "", "", WorkflowState.Loading.INSTANCE, false, SetsKt.emptySet(), null, true, null);
    }

    private final WorkflowState deriveWorkflowState(GenerationCycle cycle) {
        CycleState state = cycle != null ? cycle.getState() : null;
        if (state == null) {
            return WorkflowState.Loading.INSTANCE;
        }
        if (state instanceof CycleState.AnalysisOnly) {
            return WorkflowState.AnalysisOnly.INSTANCE;
        }
        if (state instanceof CycleState.GenerationReady) {
            return WorkflowState.ReadyForGeneration.INSTANCE;
        }
        if (state instanceof CycleState.FullCycle) {
            return WorkflowState.Complete.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final ContentState deriveContentState(GenerationCycle cycle) {
        String cvContent;
        CompatibilityRecord compatibility;
        String coverLetterContent;
        if (cycle == null) {
            return null;
        }
        CycleState cycleState = cycle.getState();
        CycleState.FullCycle fullCycle = cycleState instanceof CycleState.FullCycle ? (CycleState.FullCycle) cycleState : null;
        String coverLetterContent2 = "";
        if (fullCycle == null || (cvContent = fullCycle.getCvContent()) == null) {
            cvContent = "";
        }
        CycleState.FullCycle fullCycle2 = cycleState instanceof CycleState.FullCycle ? (CycleState.FullCycle) cycleState : null;
        if (fullCycle2 != null && (coverLetterContent = fullCycle2.getCoverLetterContent()) != null) {
            coverLetterContent2 = coverLetterContent;
        }
        if (cycleState instanceof CycleState.FullCycle) {
            compatibility = ((CycleState.FullCycle) cycleState).getCompatibility();
        } else if (cycleState instanceof CycleState.AnalysisOnly) {
            compatibility = ((CycleState.AnalysisOnly) cycleState).getCompatibility();
        } else {
            if (!(cycleState instanceof CycleState.GenerationReady)) {
                return null;
            }
            compatibility = ((CycleState.GenerationReady) cycleState).getCompatibility();
        }
        CompatibilityRecord compatibility2 = compatibility;
        int score = compatibility2.getScore();
        Integer previousScore = getPreviousScore(cycle);
        Integer delta = previousScore != null ? Integer.valueOf(score - previousScore.intValue()) : null;
        boolean hasDocs = (StringsKt.isBlank(cvContent) ^ true) || (StringsKt.isBlank(coverLetterContent2) ^ true);
        boolean isCvCorrupted = StringsKt.startsWith$default(StringsKt.trimStart((CharSequence) cvContent).toString(), "{", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) cvContent, (CharSequence) "\"name\"", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) cvContent, (CharSequence) "\"workHistory\"", false, 2, (Object) null);
        boolean isCoverCorrupted = StringsKt.startsWith$default(StringsKt.trimStart((CharSequence) coverLetterContent2).toString(), "{", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) coverLetterContent2, (CharSequence) "\"name\"", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) coverLetterContent2, (CharSequence) "\"workHistory\"", false, 2, (Object) null);
        if (compatibility2 == null) {
            return null;
        }
        if (!hasDocs && !(cycleState instanceof CycleState.AnalysisOnly)) {
            return null;
        }
        return new ContentState(cvContent, coverLetterContent2, compatibility2, score, previousScore, delta, hasDocs, isCvCorrupted, isCoverCorrupted);
    }

    private final Set<ActionType> deriveActions(WorkflowState workflow, ContentState content) {
        Set $this$deriveActions_u24lambda_u240 = SetsKt.createSetBuilder();
        if (Intrinsics.areEqual(workflow, WorkflowState.AnalysisOnly.INSTANCE)) {
            $this$deriveActions_u24lambda_u240.add(ActionType.SAVE_ANALYSIS);
            $this$deriveActions_u24lambda_u240.add(ActionType.CONTINUE_TO_CV);
        } else {
            if (Intrinsics.areEqual(workflow, WorkflowState.ReadyForGeneration.INSTANCE) ? true : Intrinsics.areEqual(workflow, WorkflowState.Complete.INSTANCE)) {
                $this$deriveActions_u24lambda_u240.add(ActionType.DISMISS);
                $this$deriveActions_u24lambda_u240.add(ActionType.SAVE_APPLICATION);
            }
        }
        if (content != null && content.getHasDocs()) {
            $this$deriveActions_u24lambda_u240.add(ActionType.EXPORT);
            $this$deriveActions_u24lambda_u240.add(ActionType.COPY);
            $this$deriveActions_u24lambda_u240.add(ActionType.SAVE_PDF);
        }
        return SetsKt.build($this$deriveActions_u24lambda_u240);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ResultViewState mapToResultViewState(GenerationCycle cycle, CycleUiState uiState, RestorationState restoration, GenerationCycle improvementBase) {
        ResultError error;
        String str;
        String str2;
        boolean isRestoring = restoration instanceof RestorationState.Loading;
        boolean isPipelineLoading = uiState instanceof CycleUiState.Loading;
        boolean isLoading = isRestoring || isPipelineLoading;
        if (uiState instanceof CycleUiState.Error) {
            error = new ResultError(((CycleUiState.Error) uiState).getMessage());
        } else {
            error = (!isLoading && cycle == null && (restoration instanceof RestorationState.None)) ? new ResultError("No active cycle") : null;
        }
        WorkflowState baseWorkflow = deriveWorkflowState(cycle);
        WorkflowState workflow = isLoading ? WorkflowState.Loading.INSTANCE : baseWorkflow;
        boolean isImprovement = (isLoading || improvementBase == null || !(uiState instanceof CycleUiState.AnalysisDone)) ? false : true;
        ContentState content = deriveContentState(cycle);
        Set actions = deriveActions(workflow, content);
        String id = cycle != null ? cycle.getId() : null;
        if (cycle == null || (str = cycle.getJobTitle()) == null) {
            str = "";
        }
        if (cycle == null || (str2 = cycle.getCompany()) == null) {
            str2 = "";
        }
        return new ResultViewState(id, str, str2, workflow, isImprovement, actions, content, isLoading, error);
    }

    public final ResultViewState computeSnapshotViewState(GenerationCycle cycle) {
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        WorkflowState workflow = deriveWorkflowState(cycle);
        ContentState content = deriveContentState(cycle);
        Set $this$computeSnapshotViewState_u24lambda_u241 = SetsKt.createSetBuilder();
        $this$computeSnapshotViewState_u24lambda_u241.add(ActionType.IMPROVE);
        boolean z = false;
        if (content != null && content.getHasDocs()) {
            z = true;
        }
        if (z) {
            $this$computeSnapshotViewState_u24lambda_u241.add(ActionType.COPY);
            $this$computeSnapshotViewState_u24lambda_u241.add(ActionType.SAVE_PDF);
        }
        Set actions = SetsKt.build($this$computeSnapshotViewState_u24lambda_u241);
        return new ResultViewState(cycle.getId(), cycle.getJobTitle(), cycle.getCompany(), workflow, false, actions, content, false, null);
    }

    public final GenerationCycle getCurrentCycle() {
        return this._currentCycle.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object setActiveCycle(GenerationCycle cycle, Continuation<? super Unit> continuation) {
        this._currentCycle.setValue(cycle);
        Object markActive = this.historyStore.markActive(cycle.getId(), continuation);
        return markActive == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? markActive : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object clearActiveCycle(kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.vantedge.app.data.viewmodel.CycleViewModel$clearActiveCycle$1
            if (r0 == 0) goto L14
            r0 = r6
            com.vantedge.app.data.viewmodel.CycleViewModel$clearActiveCycle$1 r0 = (com.vantedge.app.data.viewmodel.CycleViewModel$clearActiveCycle$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            com.vantedge.app.data.viewmodel.CycleViewModel$clearActiveCycle$1 r0 = new com.vantedge.app.data.viewmodel.CycleViewModel$clearActiveCycle$1
            r0.<init>(r5, r6)
        L19:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r6.label
            switch(r2) {
                case 0: goto L35;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L2d:
            java.lang.Object r1 = r6.L$0
            com.vantedge.app.data.viewmodel.CycleViewModel r1 = (com.vantedge.app.data.viewmodel.CycleViewModel) r1
            kotlin.ResultKt.throwOnFailure(r0)
            goto L48
        L35:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r5
            com.vantedge.app.data.storage.HistoryStore r3 = r2.historyStore
            r6.L$0 = r2
            r4 = 1
            r6.label = r4
            java.lang.Object r3 = r3.clearActive(r6)
            if (r3 != r1) goto L47
            return r1
        L47:
            r1 = r2
        L48:
            kotlinx.coroutines.flow.MutableStateFlow<com.vantedge.app.data.model.GenerationCycle> r2 = r1._currentCycle
            r3 = 0
            r2.setValue(r3)
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel.clearActiveCycle(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void openCycle(GenerationCycle cycle) {
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        this._currentCycle.setValue(cycle);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$openCycle$1(this, cycle, null), 3, null);
    }

    public final GenerationMode getCurrentMode() {
        return this.currentMode;
    }

    public final String getSavedJobTitle() {
        return this.savedJobTitle;
    }

    public final void setSavedJobTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.savedJobTitle = str;
    }

    public final String getSavedCompany() {
        return this.savedCompany;
    }

    public final void setSavedCompany(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.savedCompany = str;
    }

    public final String getSavedJobDescription() {
        return this.savedJobDescription;
    }

    public final void setSavedJobDescription(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.savedJobDescription = str;
    }

    public final String getImprovementContext() {
        return this.improvementContext;
    }

    public final void updateCustomInstructions(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this._customImprovementInstructions.setValue(text);
    }

    public final void extractJobFields(String rawText, JobSourceType sourceType) {
        Intrinsics.checkNotNullParameter(rawText, "rawText");
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$extractJobFields$1(this, sourceType, rawText, null), 3, null);
    }

    public final void resetExtractionState() {
        this._jobExtractionState.setValue(JobExtractionState.Idle.INSTANCE);
    }

    public final void resetState() {
        this._uiState.setValue(CycleUiState.Idle.INSTANCE);
        this._customImprovementInstructions.setValue("");
        this._improvementBaseCycle.setValue(null);
        this.improvementContext = null;
        this.currentMode = GenerationMode.NEW_APPLICATION;
    }

    public final void cancelPipeline() {
        Log.d(TAG, "ENTRY cancelPipeline");
        Job job = this.pipelineJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.pipelineJob = null;
        this._currentCycle.setValue(null);
        this._improvementBaseCycle.setValue(null);
        this._customImprovementInstructions.setValue("");
        this.improvementContext = null;
        this.currentMode = GenerationMode.NEW_APPLICATION;
        this._uiState.setValue(CycleUiState.Idle.INSTANCE);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$cancelPipeline$1(this, null), 3, null);
        Log.d(TAG, "EXIT cancelPipeline");
    }

    public final void deleteCycle(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$deleteCycle$1(id, this, null), 3, null);
    }

    public final void restoreCycle(GenerationCycle cycle) {
        CycleStage stage;
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        this._currentCycle.setValue(cycle);
        CycleState state = cycle.getState();
        if (state instanceof CycleState.AnalysisOnly) {
            stage = CycleStage.ANALYZED;
        } else if (state instanceof CycleState.GenerationReady) {
            stage = CycleStage.READY_FOR_GENERATION;
        } else {
            if (!(state instanceof CycleState.FullCycle)) {
                throw new NoWhenBranchMatchedException();
            }
            stage = CycleStage.DOCUMENTS_GENERATED;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$restoreCycle$1(this, cycle, stage, null), 3, null);
    }

    public final void improveFromCycle(GenerationCycle cycle) {
        String joinToString$default;
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        this._currentCycle.setValue(cycle);
        this.savedJobTitle = cycle.getJobTitle();
        this.savedCompany = cycle.getCompany();
        this.savedJobDescription = cycle.getJobDescription();
        CycleState s = cycle.getState();
        if (s instanceof CycleState.FullCycle) {
            joinToString$default = CollectionsKt.joinToString$default(((CycleState.FullCycle) s).getCompatibility().getGaps(), ", ", null, null, 0, null, new Function1<GapItem, CharSequence>() { // from class: com.vantedge.app.data.viewmodel.CycleViewModel$improveFromCycle$gaps$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(GapItem it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getSkill();
                }
            }, 30, null);
        } else if (s instanceof CycleState.AnalysisOnly) {
            joinToString$default = CollectionsKt.joinToString$default(((CycleState.AnalysisOnly) s).getCompatibility().getGaps(), ", ", null, null, 0, null, new Function1<GapItem, CharSequence>() { // from class: com.vantedge.app.data.viewmodel.CycleViewModel$improveFromCycle$gaps$2
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(GapItem it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getSkill();
                }
            }, 30, null);
        } else {
            if (!(s instanceof CycleState.GenerationReady)) {
                throw new NoWhenBranchMatchedException();
            }
            Iterable $this$map$iv = ((CycleState.GenerationReady) s).getCompatibility().getGaps();
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                GapItem it = (GapItem) item$iv$iv;
                destination$iv$iv.add(it.getSkill());
            }
            joinToString$default = CollectionsKt.joinToString$default((List) destination$iv$iv, ", ", null, null, 0, null, null, 62, null);
        }
        String gaps = joinToString$default;
        this.improvementContext = StringsKt.isBlank(gaps) ^ true ? "Previous version skill gaps to address: " + gaps : "Improve and strengthen the previous version.";
        this.currentMode = GenerationMode.IMPROVE;
    }

    public final void startImproveFromCycle(GenerationCycle cycle) {
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        this._improvementBaseCycle.setValue(cycle);
        this._customImprovementInstructions.setValue("");
        Log.d(TAG, "ENTRY startImproveFromCycle cycleId=" + cycle.getId() + " — improvementBaseCycle cached");
        improveFromCycle(cycle);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$startImproveFromCycle$1(this, cycle, null), 3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0080 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0033 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Integer getPreviousScore(com.vantedge.app.data.model.GenerationCycle r18) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel.getPreviousScore(com.vantedge.app.data.model.GenerationCycle):java.lang.Integer");
    }

    public final Integer getScoreForCycle(GenerationCycle cycle) {
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        CycleState s = cycle.getState();
        if (s instanceof CycleState.FullCycle) {
            return Integer.valueOf(((CycleState.FullCycle) s).getCompatibility().getScore());
        }
        if (s instanceof CycleState.AnalysisOnly) {
            return Integer.valueOf(((CycleState.AnalysisOnly) s).getCompatibility().getScore());
        }
        if (s instanceof CycleState.GenerationReady) {
            return Integer.valueOf(((CycleState.GenerationReady) s).getCompatibility().getScore());
        }
        throw new NoWhenBranchMatchedException();
    }

    public final List<String> getGapsForCycle(GenerationCycle cycle) {
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        CycleState s = cycle.getState();
        if (!(s instanceof CycleState.FullCycle)) {
            if (!(s instanceof CycleState.AnalysisOnly)) {
                if (s instanceof CycleState.GenerationReady) {
                    Iterable $this$map$iv = ((CycleState.GenerationReady) s).getCompatibility().getGaps();
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        GapItem it = (GapItem) item$iv$iv;
                        destination$iv$iv.add(it.getSkill());
                    }
                    return (List) destination$iv$iv;
                }
                throw new NoWhenBranchMatchedException();
            }
            Iterable $this$map$iv2 = ((CycleState.AnalysisOnly) s).getCompatibility().getGaps();
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            for (Object item$iv$iv2 : $this$map$iv2) {
                GapItem it2 = (GapItem) item$iv$iv2;
                destination$iv$iv2.add(it2.getSkill());
            }
            return (List) destination$iv$iv2;
        }
        Iterable $this$map$iv3 = ((CycleState.FullCycle) s).getCompatibility().getGaps();
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        for (Object item$iv$iv3 : $this$map$iv3) {
            GapItem it3 = (GapItem) item$iv$iv3;
            destination$iv$iv3.add(it3.getSkill());
        }
        return (List) destination$iv$iv3;
    }

    public final int totalSavedApplications() {
        Iterable $this$count$iv = this.historyStore.getCyclesFlow().getValue();
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            return 0;
        }
        int count$iv = 0;
        for (Object element$iv : $this$count$iv) {
            GenerationCycle it = (GenerationCycle) element$iv;
            if (it.isVisibleInHistory() && (count$iv = count$iv + 1) < 0) {
                CollectionsKt.throwCountOverflow();
            }
        }
        return count$iv;
    }

    public final Integer averageScore() {
        Iterable $this$filter$iv = this.historyStore.getCyclesFlow().getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GenerationCycle it = (GenerationCycle) element$iv$iv;
            if (it.isVisibleInHistory()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$mapNotNull$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            GenerationCycle it2 = (GenerationCycle) element$iv$iv$iv;
            Integer scoreForCycle = getScoreForCycle(it2);
            if (scoreForCycle != null) {
                destination$iv$iv2.add(scoreForCycle);
            }
        }
        List scores = (List) destination$iv$iv2;
        if (scores.isEmpty()) {
            return null;
        }
        return Integer.valueOf((int) CollectionsKt.averageOfInt(scores));
    }

    public final String bestScoringRole() {
        Object maxElem$iv;
        Iterable $this$filter$iv = this.historyStore.getCyclesFlow().getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GenerationCycle it = (GenerationCycle) element$iv$iv;
            if (it.isVisibleInHistory()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$maxByOrNull$iv = (List) destination$iv$iv;
        Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
        if (iterator$iv.hasNext()) {
            maxElem$iv = iterator$iv.next();
            if (iterator$iv.hasNext()) {
                GenerationCycle it2 = (GenerationCycle) maxElem$iv;
                Integer scoreForCycle = getScoreForCycle(it2);
                int maxValue$iv = scoreForCycle != null ? scoreForCycle.intValue() : 0;
                do {
                    Object e$iv = iterator$iv.next();
                    GenerationCycle it3 = (GenerationCycle) e$iv;
                    Integer scoreForCycle2 = getScoreForCycle(it3);
                    int v$iv = scoreForCycle2 != null ? scoreForCycle2.intValue() : 0;
                    if (maxValue$iv < v$iv) {
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    }
                } while (iterator$iv.hasNext());
            }
        } else {
            maxElem$iv = null;
        }
        GenerationCycle generationCycle = (GenerationCycle) maxElem$iv;
        if (generationCycle != null) {
            return generationCycle.getJobTitle();
        }
        return null;
    }

    public final int improvedApplicationsCount() {
        boolean z;
        Iterable $this$count$iv = this.historyStore.getCyclesFlow().getValue();
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            return 0;
        }
        int count$iv = 0;
        for (Object element$iv : $this$count$iv) {
            GenerationCycle it = (GenerationCycle) element$iv;
            if (it.isVisibleInHistory()) {
                Integer version = it.getVersion();
                if ((version != null ? version.intValue() : 0) >= 2) {
                    z = true;
                    if (z && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            z = false;
            if (z) {
                CollectionsKt.throwCountOverflow();
            }
        }
        return count$iv;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006f A[EDGE_INSN: B:14:0x006f->B:15:0x006f BREAK  A[LOOP:0: B:2:0x0020->B:18:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[LOOP:0: B:2:0x0020->B:18:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.vantedge.app.data.model.GenerationCycle findExistingCycleForJob(java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            r9 = this;
            java.lang.String r0 = "jobTitle"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            java.lang.String r0 = "company"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            java.lang.String r0 = "jobDescription"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)
            com.vantedge.app.data.storage.HistoryStore r0 = r9.historyStore
            kotlinx.coroutines.flow.StateFlow r0 = r0.getCyclesFlow()
            java.lang.Object r0 = r0.getValue()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            r1 = 0
            java.util.Iterator r2 = r0.iterator()
        L20:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L6e
            java.lang.Object r3 = r2.next()
            r4 = r3
            com.vantedge.app.data.model.GenerationCycle r4 = (com.vantedge.app.data.model.GenerationCycle) r4
            r5 = 0
            boolean r6 = r4.isVisibleInHistory()
            if (r6 == 0) goto L69
            java.lang.String r6 = r4.getJobTitle()
            r7 = 1
            boolean r6 = kotlin.text.StringsKt.equals(r6, r10, r7)
            if (r6 == 0) goto L69
            java.lang.String r6 = r4.getCompany()
            boolean r6 = kotlin.text.StringsKt.equals(r6, r11, r7)
            if (r6 == 0) goto L69
            java.lang.String r6 = r4.getJobDescription()
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            java.lang.CharSequence r6 = kotlin.text.StringsKt.trim(r6)
            java.lang.String r6 = r6.toString()
            r8 = r12
            java.lang.CharSequence r8 = (java.lang.CharSequence) r8
            java.lang.CharSequence r8 = kotlin.text.StringsKt.trim(r8)
            java.lang.String r8 = r8.toString()
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r8)
            if (r6 == 0) goto L69
            goto L6a
        L69:
            r7 = 0
        L6a:
            if (r7 == 0) goto L20
            goto L6f
        L6e:
            r3 = 0
        L6f:
            com.vantedge.app.data.model.GenerationCycle r3 = (com.vantedge.app.data.model.GenerationCycle) r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel.findExistingCycleForJob(java.lang.String, java.lang.String, java.lang.String):com.vantedge.app.data.model.GenerationCycle");
    }

    public static /* synthetic */ void runPipeline$default(CycleViewModel cycleViewModel, UserProfile userProfile, String str, String str2, String str3, GenerationMode generationMode, String str4, int i, Object obj) {
        String str5;
        if ((i & 32) == 0) {
            str5 = str4;
        } else {
            str5 = null;
        }
        cycleViewModel.runPipeline(userProfile, str, str2, str3, generationMode, str5);
    }

    public final void runPipeline(UserProfile profile, String jobTitle, String company, String jobDescription, GenerationMode mode, String improvementContext) {
        Job launch$default;
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Log.d(TAG, "ENTRY runPipeline mode=" + mode + " job=" + jobTitle);
        Job job = this.pipelineJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this._currentCycle.setValue(null);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$runPipeline$1(this, null), 3, null);
        this._improvementBaseCycle.setValue(null);
        this.currentMode = mode;
        this.savedProfile = profile;
        this.savedJobTitle = jobTitle;
        this.savedCompany = company;
        this.savedJobDescription = jobDescription;
        this._uiState.setValue(new CycleUiState.Loading(PipelineStep.ANALYSING));
        launch$default = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new CycleViewModel$runPipeline$2(mode, this, jobTitle, company, jobDescription, profile, improvementContext, null), 2, null);
        this.pipelineJob = launch$default;
        Log.d(TAG, "EXIT runPipeline launched");
    }

    public static /* synthetic */ void runAnalysis$default(CycleViewModel cycleViewModel, UserProfile userProfile, String str, String str2, String str3, GenerationMode generationMode, String str4, int i, Object obj) {
        String str5;
        if ((i & 32) == 0) {
            str5 = str4;
        } else {
            str5 = null;
        }
        cycleViewModel.runAnalysis(userProfile, str, str2, str3, generationMode, str5);
    }

    public final void runAnalysis(UserProfile profile, String jobTitle, String company, String jobDescription, GenerationMode mode, String improvementContext) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        Intrinsics.checkNotNullParameter(mode, "mode");
        runPipeline(profile, jobTitle, company, jobDescription, mode, improvementContext);
    }

    public final void retryPipeline() {
        Log.d(TAG, "ENTRY retryPipeline mode=" + this.currentMode);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new CycleViewModel$retryPipeline$1(this, null), 3, null);
        if (this.currentMode == GenerationMode.IMPROVE) {
            GenerationCycle baseCycle = this._improvementBaseCycle.getValue();
            if (baseCycle == null) {
                Log.e(TAG, "retryPipeline ABORTED [IMPROVE]: improvementBaseCycle is null — source cycle lost");
                this._uiState.setValue(new CycleUiState.Error("Cannot retry: source cycle was lost. Please start again."));
                return;
            }
            Log.d(TAG, "retryPipeline [IMPROVE]: re-invoking startImproveFromCycle cycleId=" + baseCycle.getId());
            Job job = this.pipelineJob;
            if (job != null) {
                Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
            }
            startImproveFromCycle(baseCycle);
            Log.d(TAG, "EXIT retryPipeline [IMPROVE] relaunched");
            return;
        }
        UserProfile profile = this.savedProfile;
        if (profile == null) {
            Log.e(TAG, "retryPipeline ABORTED: savedProfile is null. Was runPipeline() ever called?");
            return;
        }
        if (StringsKt.isBlank(this.savedJobTitle) || StringsKt.isBlank(this.savedJobDescription)) {
            Log.e(TAG, "retryPipeline ABORTED: job inputs blank. savedJobTitle='" + this.savedJobTitle + "' savedJobDescription='" + StringsKt.take(this.savedJobDescription, 20) + "...'");
        } else {
            this._currentCycle.setValue(null);
            Job job2 = this.pipelineJob;
            if (job2 != null) {
                Job.DefaultImpls.cancel$default(job2, (CancellationException) null, 1, (Object) null);
            }
            runPipeline$default(this, profile, this.savedJobTitle, this.savedCompany, this.savedJobDescription, this.currentMode, null, 32, null);
            Log.d(TAG, "EXIT retryPipeline relaunched");
        }
    }

    public final void applyDesign(DesignConfig design) {
        Job launch$default;
        Intrinsics.checkNotNullParameter(design, "design");
        GenerationCycle cycle = getCurrentCycle();
        if (cycle == null) {
            return;
        }
        this._uiState.setValue(new CycleUiState.Loading(PipelineStep.APPLYING_DESIGN));
        launch$default = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new CycleViewModel$applyDesign$1(this, cycle, design, null), 2, null);
        this.pipelineJob = launch$default;
    }

    public final void exportCurrentCycle() {
        GenerationCycle cycle = getCurrentCycle();
        if (cycle == null) {
            return;
        }
        this._exportState.setValue(ExportState.Exporting.INSTANCE);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new CycleViewModel$exportCurrentCycle$1(this, cycle, null), 2, null);
    }

    public final void continueToGeneration() {
        String finalContext;
        Job launch$default;
        GenerationCycle cycle = getCurrentCycle();
        if (cycle == null) {
            return;
        }
        this._uiState.setValue(new CycleUiState.Loading(PipelineStep.GENERATING_CV));
        String autoGaps = this.improvementContext;
        String custom = this._customImprovementInstructions.getValue();
        String str = autoGaps;
        if (!(str == null || StringsKt.isBlank(str)) && (!StringsKt.isBlank(custom))) {
            finalContext = "AUTO-DETECTED IMPROVEMENT OPPORTUNITIES:\n" + autoGaps + "\n\nUSER-REQUESTED IMPROVEMENTS:\n" + custom;
        } else {
            String str2 = autoGaps;
            if (str2 == null || StringsKt.isBlank(str2)) {
                finalContext = StringsKt.isBlank(custom) ^ true ? "USER-REQUESTED IMPROVEMENTS:\n" + custom : null;
            } else {
                finalContext = autoGaps;
            }
        }
        String str3 = autoGaps;
        Log.d(TAG, "ImprovementContext: gapsPresent=" + (!(str3 == null || StringsKt.isBlank(str3))) + ", customPresent=" + (true ^ StringsKt.isBlank(custom)) + ", customLength=" + custom.length() + ", mergedLength=" + (finalContext != null ? finalContext.length() : 0));
        launch$default = BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new CycleViewModel$continueToGeneration$1(this, cycle, finalContext, null), 2, null);
        this.pipelineJob = launch$default;
    }

    public final void dismissCurrentCycle() {
        GenerationCycle cycle = getCurrentCycle();
        if (cycle == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new CycleViewModel$dismissCurrentCycle$1(cycle, this, null), 2, null);
    }

    public final void commitCurrentCycle() {
        GenerationCycle cycle = getCurrentCycle();
        if (cycle == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new CycleViewModel$commitCurrentCycle$1(cycle, this, null), 2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0080 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object commitCurrentCycleAndWait(kotlin.coroutines.Continuation<? super kotlin.Unit> r22) {
        /*
            r21 = this;
            r0 = r22
            boolean r1 = r0 instanceof com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycleAndWait$1
            if (r1 == 0) goto L19
            r1 = r0
            com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycleAndWait$1 r1 = (com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycleAndWait$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L19
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r0 = r1
            r2 = r21
            goto L21
        L19:
            com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycleAndWait$1 r1 = new com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycleAndWait$1
            r2 = r21
            r1.<init>(r2, r0)
            r0 = r1
        L21:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L40;
                case 1: goto L38;
                case 2: goto L34;
                default: goto L2c;
            }
        L2c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L34:
            kotlin.ResultKt.throwOnFailure(r1)
            goto L81
        L38:
            java.lang.Object r4 = r0.L$0
            com.vantedge.app.data.viewmodel.CycleViewModel r4 = (com.vantedge.app.data.viewmodel.CycleViewModel) r4
            kotlin.ResultKt.throwOnFailure(r1)
            goto L74
        L40:
            kotlin.ResultKt.throwOnFailure(r1)
            r4 = r21
            com.vantedge.app.data.model.GenerationCycle r5 = r4.getCurrentCycle()
            if (r5 != 0) goto L4e
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        L4e:
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r16 = 0
            r17 = 1
            r18 = 0
            r19 = 3071(0xbff, float:4.303E-42)
            r20 = 0
            com.vantedge.app.data.model.GenerationCycle r5 = com.vantedge.app.data.model.GenerationCycle.copy$default(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r16, r17, r18, r19, r20)
            com.vantedge.app.data.storage.HistoryStore r6 = r4.historyStore
            r0.L$0 = r4
            r7 = 1
            r0.label = r7
            java.lang.Object r5 = r6.saveCycle(r5, r0)
            if (r5 != r3) goto L74
            return r3
        L74:
            r5 = 0
            r0.L$0 = r5
            r5 = 2
            r0.label = r5
            java.lang.Object r4 = r4.clearActiveCycle(r0)
            if (r4 != r3) goto L81
            return r3
        L81:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel.commitCurrentCycleAndWait(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
