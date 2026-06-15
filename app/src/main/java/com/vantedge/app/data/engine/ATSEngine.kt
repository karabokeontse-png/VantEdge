package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.UserProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* compiled from: AtsEngine.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/vantedge/app/data/engine/ATSEngine;", "", "()V", "analyze", "Lcom/vantedge/app/data/engine/AtsResult;", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobDescription", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ATSEngine {
    public static final int $stable = 0;
    public static final ATSEngine INSTANCE = new ATSEngine();

    private ATSEngine() {
    }

    public final AtsResult analyze(UserProfile profile, String jobDescription) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        List keywords = CollectionsKt.take(CollectionsKt.distinct(StringsKt.split$default((CharSequence) jobDescription, new String[]{" "}, false, 0, 6, (Object) null)), 10);
        List $this$filter$iv = keywords;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (!profile.getSkills().contains(it)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List missing = (List) destination$iv$iv;
        return new AtsResult(RangesKt.coerceAtLeast(100 - (missing.size() * 5), 0), keywords, missing);
    }
}
