package com.vantedge.app.data.storage;

import com.google.gson.reflect.TypeToken;
import com.vantedge.app.data.model.CompatibilityRecord;
import java.lang.reflect.Type;
import kotlin.Metadata;

/* compiled from: CycleStateSerializer.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"TYPE_ANALYSIS_ONLY", "", "TYPE_FULL_CYCLE", "TYPE_GENERATION_READY", "TYPE_KEY", "compatibilityType", "Ljava/lang/reflect/Type;", "kotlin.jvm.PlatformType", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CycleStateSerializerKt {
    private static final String TYPE_ANALYSIS_ONLY = "AnalysisOnly";
    private static final String TYPE_FULL_CYCLE = "FullCycle";
    private static final String TYPE_GENERATION_READY = "GenerationReady";
    private static final String TYPE_KEY = "type";
    private static final Type compatibilityType = new TypeToken<CompatibilityRecord>() { // from class: com.vantedge.app.data.storage.CycleStateSerializerKt$compatibilityType$1
    }.getType();
}
