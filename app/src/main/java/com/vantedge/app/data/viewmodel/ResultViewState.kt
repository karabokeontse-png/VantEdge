package com.vantedge.app.data.viewmodel;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BY\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u0006\u0010\u000f\u001a\u00020\t\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0007HÆ\u0003J\t\u0010$\u001a\u00020\tHÆ\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\f0\u000bHÆ\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u000eHÆ\u0003J\t\u0010'\u001a\u00020\tHÆ\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0011HÆ\u0003Jo\u0010)\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\t2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÆ\u0001J\u0013\u0010*\u001a\u00020\t2\b\u0010+\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010,\u001a\u00020-HÖ\u0001J\t\u0010.\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001cR\u0011\u0010\u000f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006/"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ResultViewState;", "", "cycleId", "", "jobTitle", "company", "workflow", "Lcom/vantedge/app/data/viewmodel/WorkflowState;", "isImprovementMode", "", "actions", "", "Lcom/vantedge/app/data/viewmodel/ActionType;", "content", "Lcom/vantedge/app/data/viewmodel/ContentState;", "isLoading", "error", "Lcom/vantedge/app/data/viewmodel/ResultError;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/viewmodel/WorkflowState;ZLjava/util/Set;Lcom/vantedge/app/data/viewmodel/ContentState;ZLcom/vantedge/app/data/viewmodel/ResultError;)V", "getActions", "()Ljava/util/Set;", "getCompany", "()Ljava/lang/String;", "getContent", "()Lcom/vantedge/app/data/viewmodel/ContentState;", "getCycleId", "getError", "()Lcom/vantedge/app/data/viewmodel/ResultError;", "()Z", "getJobTitle", "getWorkflow", "()Lcom/vantedge/app/data/viewmodel/WorkflowState;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public final /* data */ class ResultViewState {
    public static final int $stable = 8;
    private final Set<ActionType> actions;
    private final String company;
    private final ContentState content;
    private final String cycleId;
    private final ResultError error;
    private final boolean isImprovementMode;
    private final boolean isLoading;
    private final String jobTitle;
    private final WorkflowState workflow;

    /* renamed from: component1, reason: from getter */
    public final String getCycleId() {
        return this.cycleId;
    }

    /* renamed from: component2, reason: from getter */
    public final String getJobTitle() {
        return this.jobTitle;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    /* renamed from: component4, reason: from getter */
    public final WorkflowState getWorkflow() {
        return this.workflow;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getIsImprovementMode() {
        return this.isImprovementMode;
    }

    public final Set<ActionType> component6() {
        return this.actions;
    }

    /* renamed from: component7, reason: from getter */
    public final ContentState getContent() {
        return this.content;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean getIsLoading() {
        return this.isLoading;
    }

    /* renamed from: component9, reason: from getter */
    public final ResultError getError() {
        return this.error;
    }

    public final ResultViewState copy(String cycleId, String jobTitle, String company, WorkflowState workflow, boolean isImprovementMode, Set<? extends ActionType> actions, ContentState content, boolean isLoading, ResultError error) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(workflow, "workflow");
        Intrinsics.checkNotNullParameter(actions, "actions");
        return new ResultViewState(cycleId, jobTitle, company, workflow, isImprovementMode, actions, content, isLoading, error);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResultViewState)) {
            return false;
        }
        ResultViewState resultViewState = (ResultViewState) other;
        return Intrinsics.areEqual(this.cycleId, resultViewState.cycleId) && Intrinsics.areEqual(this.jobTitle, resultViewState.jobTitle) && Intrinsics.areEqual(this.company, resultViewState.company) && Intrinsics.areEqual(this.workflow, resultViewState.workflow) && this.isImprovementMode == resultViewState.isImprovementMode && Intrinsics.areEqual(this.actions, resultViewState.actions) && Intrinsics.areEqual(this.content, resultViewState.content) && this.isLoading == resultViewState.isLoading && Intrinsics.areEqual(this.error, resultViewState.error);
    }

    public int hashCode() {
        return ((((((((((((((((this.cycleId == null ? 0 : this.cycleId.hashCode()) * 31) + this.jobTitle.hashCode()) * 31) + this.company.hashCode()) * 31) + this.workflow.hashCode()) * 31) + Boolean.hashCode(this.isImprovementMode)) * 31) + this.actions.hashCode()) * 31) + (this.content == null ? 0 : this.content.hashCode())) * 31) + Boolean.hashCode(this.isLoading)) * 31) + (this.error != null ? this.error.hashCode() : 0);
    }

    public String toString() {
        return "ResultViewState(cycleId=" + this.cycleId + ", jobTitle=" + this.jobTitle + ", company=" + this.company + ", workflow=" + this.workflow + ", isImprovementMode=" + this.isImprovementMode + ", actions=" + this.actions + ", content=" + this.content + ", isLoading=" + this.isLoading + ", error=" + this.error + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResultViewState(String cycleId, String jobTitle, String company, WorkflowState workflow, boolean isImprovementMode, Set<? extends ActionType> actions, ContentState content, boolean isLoading, ResultError error) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(workflow, "workflow");
        Intrinsics.checkNotNullParameter(actions, "actions");
        this.cycleId = cycleId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.workflow = workflow;
        this.isImprovementMode = isImprovementMode;
        this.actions = actions;
        this.content = content;
        this.isLoading = isLoading;
        this.error = error;
    }

    public final String getCycleId() {
        return this.cycleId;
    }

    public final String getJobTitle() {
        return this.jobTitle;
    }

    public final String getCompany() {
        return this.company;
    }

    public final WorkflowState getWorkflow() {
        return this.workflow;
    }

    public final boolean isImprovementMode() {
        return this.isImprovementMode;
    }

    public final Set<ActionType> getActions() {
        return this.actions;
    }

    public final ContentState getContent() {
        return this.content;
    }

    public final boolean isLoading() {
        return this.isLoading;
    }

    public final ResultError getError() {
        return this.error;
    }
}
