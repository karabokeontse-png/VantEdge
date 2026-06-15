package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.network.AiGateway;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GeneratorEngine.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JJ\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007JT\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0014\u0010\u0013\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u00110\u0014H\u0086@¢\u0006\u0002\u0010\u0015JT\u0010\u0016\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0014\u0010\u0013\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u00110\u0014H\u0086@¢\u0006\u0002\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/vantedge/app/data/engine/GeneratorEngine;", "", "aiGateway", "Lcom/vantedge/app/data/network/AiGateway;", "(Lcom/vantedge/app/data/network/AiGateway;)V", "applyDesignToContent", "Lkotlin/Pair;", "", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobTitle", "company", "matchedKeywordsJson", "coverLetterBody", "designId", "schemeId", "generateCoverLetter", "", "jobDescription", "onResult", "Lkotlin/Function1;", "(Lcom/vantedge/app/data/model/UserProfile;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateCv", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class GeneratorEngine {
    public static final int $stable = 8;
    private final AiGateway aiGateway;

    public GeneratorEngine(AiGateway aiGateway) {
        Intrinsics.checkNotNullParameter(aiGateway, "aiGateway");
        this.aiGateway = aiGateway;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object generateCv(com.vantedge.app.data.model.UserProfile r22, java.lang.String r23, java.lang.String r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r28, kotlin.coroutines.Continuation<? super kotlin.Unit> r29) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.GeneratorEngine.generateCv(com.vantedge.app.data.model.UserProfile, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object generateCoverLetter(com.vantedge.app.data.model.UserProfile r22, java.lang.String r23, java.lang.String r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r28, kotlin.coroutines.Continuation<? super kotlin.Unit> r29) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.GeneratorEngine.generateCoverLetter(com.vantedge.app.data.model.UserProfile, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Pair<String, String> applyDesignToContent(UserProfile profile, String jobTitle, String company, String matchedKeywordsJson, String coverLetterBody, String designId, String schemeId) {
        List keywords;
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(matchedKeywordsJson, "matchedKeywordsJson");
        Intrinsics.checkNotNullParameter(coverLetterBody, "coverLetterBody");
        Intrinsics.checkNotNullParameter(designId, "designId");
        Intrinsics.checkNotNullParameter(schemeId, "schemeId");
        try {
            JSONObject json = new JSONObject(matchedKeywordsJson);
            JSONArray arr = json.getJSONArray("matchedKeywords");
            Iterable $this$map$iv = RangesKt.until(0, arr.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(arr.getString(item$iv$iv));
                json = json;
            }
            keywords = (List) destination$iv$iv;
        } catch (Exception e) {
            keywords = CollectionsKt.emptyList();
        }
        String cvHtml = CVTemplate.INSTANCE.render(profile, jobTitle, company, keywords, designId, schemeId);
        String coverLetterHtml = CVTemplate.INSTANCE.renderCoverLetter(profile, jobTitle, company, coverLetterBody, designId, schemeId);
        return new Pair<>(cvHtml, coverLetterHtml);
    }
}
