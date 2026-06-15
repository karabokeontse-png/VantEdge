package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.UserProfile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: ScoreEngine.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/vantedge/app/data/engine/ScoreEngine;", "", "()V", "calculate", "Lcom/vantedge/app/data/engine/VantEdgeScoreResult;", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ScoreEngine {
    public static final int $stable = 0;
    public static final ScoreEngine INSTANCE = new ScoreEngine();

    private ScoreEngine() {
    }

    public final VantEdgeScoreResult calculate(UserProfile profile) {
        String interpretation;
        Intrinsics.checkNotNullParameter(profile, "profile");
        int score = StringsKt.isBlank(profile.getName()) ^ true ? 0 + 10 : 0;
        if (!StringsKt.isBlank(profile.getSummary())) {
            score += 15;
        }
        if (!profile.getSkills().isEmpty()) {
            score += 20;
        }
        if (!profile.getWorkHistory().isEmpty()) {
            score += 25;
        }
        if (!profile.getEducation().isEmpty()) {
            score += 10;
        }
        if (!profile.getCertifications().isEmpty()) {
            score += 10;
        }
        if (!profile.getLanguages().isEmpty()) {
            score += 10;
        }
        if (score >= 80) {
            interpretation = "Strong Profile";
        } else {
            interpretation = score >= 50 ? "Moderate Profile" : "Needs Improvement";
        }
        return new VantEdgeScoreResult(score, interpretation);
    }
}
