package com.vantedge.app.data.network;

import android.util.Log;
import com.vantedge.app.data.network.GeminiService;
import java.io.IOException;
import java.net.UnknownHostException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* compiled from: GeminiService.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/vantedge/app/data/network/GeminiService$ModelResult;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.network.GeminiService$tryModelOnce$2", f = "GeminiService.kt", i = {}, l = {277}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes10.dex */
final class GeminiService$tryModelOnce$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super GeminiService.ModelResult>, Object> {
    final /* synthetic */ String $model;
    final /* synthetic */ Request $request;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GeminiService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GeminiService$tryModelOnce$2(GeminiService geminiService, Request request, String str, Continuation<? super GeminiService$tryModelOnce$2> continuation) {
        super(2, continuation);
        this.this$0 = geminiService;
        this.$request = request;
        this.$model = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GeminiService$tryModelOnce$2(this.this$0, this.$request, this.$model, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super GeminiService.ModelResult> continuation) {
        return ((GeminiService$tryModelOnce$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        OkHttpClient okHttpClient;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                GeminiService geminiService = this.this$0;
                Request request = this.$request;
                final String str = this.$model;
                this.L$0 = geminiService;
                this.L$1 = request;
                this.L$2 = str;
                this.label = 1;
                GeminiService$tryModelOnce$2 uCont$iv = this;
                CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted(uCont$iv), 1);
                cancellable$iv.initCancellability();
                final CancellableContinuationImpl cont = cancellable$iv;
                okHttpClient = geminiService.client;
                final Call call = okHttpClient.newCall(request);
                cont.invokeOnCancellation(new Function1<Throwable, Unit>() { // from class: com.vantedge.app.data.network.GeminiService$tryModelOnce$2$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable it) {
                        Log.d("GeminiService", "Cancelling OkHttp call for model: " + str);
                        call.cancel();
                    }
                });
                call.enqueue(new Callback() { // from class: com.vantedge.app.data.network.GeminiService$tryModelOnce$2$1$2

                    /* compiled from: GeminiService.kt */
                    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                    public /* synthetic */ class WhenMappings {
                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                        static {
                            int[] iArr = new int[GeminiService.ModelResult.FailureType.values().length];
                            try {
                                iArr[GeminiService.ModelResult.FailureType.DNS.ordinal()] = 1;
                            } catch (NoSuchFieldError e) {
                            }
                            $EnumSwitchMapping$0 = iArr;
                        }
                    }

                    @Override // okhttp3.Callback
                    public void onFailure(Call call2, IOException e) {
                        Intrinsics.checkNotNullParameter(call2, "call");
                        Intrinsics.checkNotNullParameter(e, "e");
                        GeminiService.ModelResult.FailureType type = e instanceof UnknownHostException ? GeminiService.ModelResult.FailureType.DNS : GeminiService.ModelResult.FailureType.NETWORK;
                        String msg = WhenMappings.$EnumSwitchMapping$0[type.ordinal()] == 1 ? "Cannot reach AI server (DNS failure). Try switching to a different network or WiFi." : "Network error: " + e.getMessage();
                        Log.e("GeminiService", "DEBUG: [tryModel] " + type + " failure on " + str + ": " + e.getMessage());
                        if (cont.isActive()) {
                            CancellableContinuation<GeminiService.ModelResult> cancellableContinuation = cont;
                            Result.Companion companion = Result.INSTANCE;
                            cancellableContinuation.resumeWith(Result.m6582constructorimpl(new GeminiService.ModelResult.Failure(type, msg)));
                        }
                    }

                    @Override // okhttp3.Callback
                    public void onResponse(Call call2, Response response) {
                        Intrinsics.checkNotNullParameter(call2, "call");
                        Intrinsics.checkNotNullParameter(response, "response");
                        if (!cont.isActive()) {
                            Log.w("GeminiService", "DEBUG: [tryModel] Post-cancellation callback for " + str + " — ignoring");
                            return;
                        }
                        ResponseBody body = response.body();
                        String body2 = body != null ? body.string() : null;
                        int code = response.code();
                        boolean z = false;
                        if (code == 200) {
                            try {
                                String raw = new JSONObject(body2 == null ? "" : body2).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
                                Intrinsics.checkNotNull(raw);
                                String cleaned = StringsKt.trim((CharSequence) new Regex("\\*(.+?)\\*").replace(new Regex("\\*\\*(.+?)\\*\\*").replace(StringsKt.replace$default(StringsKt.replace$default(raw, "```json", "", false, 4, (Object) null), "```", "", false, 4, (Object) null), "$1"), "$1")).toString();
                                CancellableContinuation<GeminiService.ModelResult> cancellableContinuation = cont;
                                Result.Companion companion = Result.INSTANCE;
                                cancellableContinuation.resumeWith(Result.m6582constructorimpl(new GeminiService.ModelResult.Success(cleaned)));
                                return;
                            } catch (Exception e) {
                                Log.e("GeminiService", "DEBUG: [tryModel] Parse error on " + str + ": " + e.getMessage());
                                CancellableContinuation<GeminiService.ModelResult> cancellableContinuation2 = cont;
                                Result.Companion companion2 = Result.INSTANCE;
                                cancellableContinuation2.resumeWith(Result.m6582constructorimpl(new GeminiService.ModelResult.Failure(GeminiService.ModelResult.FailureType.PARSE, "AI response was unreadable. Please try again.")));
                                return;
                            }
                        }
                        if (code == 429) {
                            Log.w("GeminiService", "DEBUG: [tryModel] " + str + " returned 429 (rate limited).");
                            CancellableContinuation<GeminiService.ModelResult> cancellableContinuation3 = cont;
                            Result.Companion companion3 = Result.INSTANCE;
                            cancellableContinuation3.resumeWith(Result.m6582constructorimpl(new GeminiService.ModelResult.Failure(GeminiService.ModelResult.FailureType.RATE_LIMIT, "AI service is busy (rate limited). Please wait a moment and retry.")));
                            return;
                        }
                        if (500 <= code && code < 600) {
                            z = true;
                        }
                        if (z) {
                            Log.e("GeminiService", "DEBUG: [tryModel] Server error " + response.code() + " on " + str);
                            CancellableContinuation<GeminiService.ModelResult> cancellableContinuation4 = cont;
                            Result.Companion companion4 = Result.INSTANCE;
                            cancellableContinuation4.resumeWith(Result.m6582constructorimpl(new GeminiService.ModelResult.Failure(GeminiService.ModelResult.FailureType.SERVER_ERROR, "AI server error (" + response.code() + "). Please retry shortly.")));
                            return;
                        }
                        Log.e("GeminiService", "DEBUG: [tryModel] HTTP " + response.code() + " on " + str);
                        CancellableContinuation<GeminiService.ModelResult> cancellableContinuation5 = cont;
                        Result.Companion companion5 = Result.INSTANCE;
                        cancellableContinuation5.resumeWith(Result.m6582constructorimpl(new GeminiService.ModelResult.Failure(GeminiService.ModelResult.FailureType.UNKNOWN, "AI request failed (HTTP " + response.code() + "). Please retry.")));
                    }
                });
                Object result = cancellable$iv.getResult();
                if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended(this);
                }
                if (result == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return result;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
