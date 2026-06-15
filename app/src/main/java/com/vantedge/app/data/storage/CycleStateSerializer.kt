package com.vantedge.app.data.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.CycleState;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleStateSerializer.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/storage/CycleStateSerializer;", "Lcom/google/gson/JsonSerializer;", "Lcom/vantedge/app/data/model/CycleState;", "()V", "serialize", "Lcom/google/gson/JsonElement;", "src", "typeOfSrc", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonSerializationContext;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CycleStateSerializer implements JsonSerializer<CycleState> {
    public static final int $stable = 0;

    @Override // com.google.gson.JsonSerializer
    public JsonElement serialize(CycleState src, Type typeOfSrc, JsonSerializationContext context) {
        Type type;
        Type type2;
        Type type3;
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(typeOfSrc, "typeOfSrc");
        Intrinsics.checkNotNullParameter(context, "context");
        JsonObject obj = new JsonObject();
        if (src instanceof CycleState.AnalysisOnly) {
            obj.addProperty("type", "AnalysisOnly");
            CompatibilityRecord compatibility = ((CycleState.AnalysisOnly) src).getCompatibility();
            type3 = CycleStateSerializerKt.compatibilityType;
            obj.add("compatibility", context.serialize(compatibility, type3));
        } else if (src instanceof CycleState.GenerationReady) {
            obj.addProperty("type", "GenerationReady");
            CompatibilityRecord compatibility2 = ((CycleState.GenerationReady) src).getCompatibility();
            type2 = CycleStateSerializerKt.compatibilityType;
            obj.add("compatibility", context.serialize(compatibility2, type2));
            obj.add("matchedKeywords", context.serialize(((CycleState.GenerationReady) src).getMatchedKeywords()));
            obj.addProperty("coverLetterBody", ((CycleState.GenerationReady) src).getCoverLetterBody());
        } else if (src instanceof CycleState.FullCycle) {
            obj.addProperty("type", "FullCycle");
            CompatibilityRecord compatibility3 = ((CycleState.FullCycle) src).getCompatibility();
            type = CycleStateSerializerKt.compatibilityType;
            obj.add("compatibility", context.serialize(compatibility3, type));
            obj.addProperty("cvContent", ((CycleState.FullCycle) src).getCvContent());
            obj.addProperty("coverLetterContent", ((CycleState.FullCycle) src).getCoverLetterContent());
            obj.add("design", context.serialize(((CycleState.FullCycle) src).getDesign()));
        }
        return obj;
    }
}
