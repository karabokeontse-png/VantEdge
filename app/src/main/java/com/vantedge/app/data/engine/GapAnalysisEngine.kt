package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.GapSeverity;
import com.vantedge.app.data.model.SkillGap;
import com.vantedge.app.data.model.UserProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GapAnalysisEngine.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¨\u0006\n"}, d2 = {"Lcom/vantedge/app/data/engine/GapAnalysisEngine;", "", "()V", "analyze", "", "Lcom/vantedge/app/data/model/SkillGap;", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobSkills", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class GapAnalysisEngine {
    public static final int $stable = 0;
    public static final GapAnalysisEngine INSTANCE = new GapAnalysisEngine();

    private GapAnalysisEngine() {
    }

    public final List<SkillGap> analyze(UserProfile profile, List<String> jobSkills) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobSkills, "jobSkills");
        Set userSkills = CollectionsKt.toSet(profile.getSkills());
        List<String> $this$map$iv = jobSkills;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String skill = (String) item$iv$iv;
            boolean hasSkill = userSkills.contains(skill);
            destination$iv$iv.add(new SkillGap(skill, hasSkill ? GapSeverity.LOW : GapSeverity.HIGH, hasSkill ? "Already present" : "Missing skill"));
        }
        return (List) destination$iv$iv;
    }
}
