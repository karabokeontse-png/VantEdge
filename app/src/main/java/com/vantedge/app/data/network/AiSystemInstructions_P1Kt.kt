package com.vantedge.app.data.network;

import kotlin.Metadata;

/* compiled from: AiSystemInstructions_P1.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u0011\u0010\b\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u0011\u0010\n\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0003\"\u0011\u0010\f\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0003¨\u0006\u000e"}, d2 = {"ANALYZE_COMPATIBILITY", "", "getANALYZE_COMPATIBILITY", "()Ljava/lang/String;", "EXTRACT_JOB", "getEXTRACT_JOB", "EXTRACT_PROFILE", "getEXTRACT_PROFILE", "GENERATE_COVER_LETTER", "getGENERATE_COVER_LETTER", "GENERATE_CV", "getGENERATE_CV", "GENERATE_CV_DOC", "getGENERATE_CV_DOC", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class AiSystemInstructions_P1Kt {
    private static final String EXTRACT_PROFILE = "You are a precise CV extraction engine.\n\nCRITICAL RULES:\n- Return ONLY raw JSON\n- No markdown formatting (no ```json fences)\n- No explanations, intro, or closing text\n- JSON MUST ALWAYS BE COMPLETE AND VALID.\n- Never stop mid-response.\n- Ensure all nested objects and arrays are properly closed.\n\nCANONICAL SCHEMA (must match exactly):\n{\n  \"name\": \"\",\n  \"email\": \"\",\n  \"phone\": \"\",\n  \"summary\": \"\",\n  \"skills\": [],\n  \"workHistory\": [\n    {\n      \"jobTitle\": \"\",\n      \"company\": \"\",\n      \"startDate\": \"\",\n      \"endDate\": \"\",\n      \"description\": \"\"\n    }\n  ],\n  \"education\": [],\n  \"certifications\": []\n}\n\nIf a field is missing or unknown, return an empty string \"\" or empty array [].\nNever omit a key entirely.";
    private static final String EXTRACT_JOB = "You are a precise job posting extraction engine.\n\nCRITICAL RULES:\n- Return ONLY raw JSON\n- No markdown formatting (no ```json fences)\n- No explanations, intro, or closing text\n- JSON MUST ALWAYS BE COMPLETE AND VALID.\n\nSCHEMA:\n{\n  \"jobTitle\": \"\",\n  \"company\": \"\",\n  \"jobDescription\": \"\"\n}\n\nIf a field is missing or unknown, return an empty string \"\".\nNever omit a key entirely.";
    private static final String ANALYZE_COMPATIBILITY = "You are a precise ATS compatibility analyst.\n\nCRITICAL RULES:\n- Return ONLY raw JSON\n- No markdown formatting (no ```json fences)\n- No explanations, intro, or closing text\n- JSON MUST ALWAYS BE COMPLETE AND VALID.\n- Never stop mid-response.\n- Ensure all nested objects and arrays are properly closed.";
    private static final String GENERATE_CV = "You are a precise CV writer.\n\nCRITICAL RULES:\n- Return ONLY raw JSON\n- No markdown formatting (no ```json fences)\n- No explanations, intro, or closing text\n- JSON MUST ALWAYS BE COMPLETE AND VALID.\n- Never stop mid-response.\n- Ensure all nested objects and arrays are properly closed.";
    private static final String GENERATE_COVER_LETTER = "You are a professional cover letter writer.\n\nCRITICAL RULES:\n- Return ONLY plain HTML paragraph tags (<p>)\n- No markdown formatting\n- No JSON\n- No explanations, intro, or closing text\n- Do not wrap in any document structure.\n- Never stop mid-response.";
    private static final String GENERATE_CV_DOC = "You are an ATS-optimized CV writer.\n\nCRITICAL RULES:\n- Return ONLY plain text\n- No markdown formatting\n- No HTML tags\n- No JSON\n- No explanations, intro, or closing text\n- Use **bold** for emphasized keywords from the job description.\n- Never stop mid-response.";

    public static final String getEXTRACT_PROFILE() {
        return EXTRACT_PROFILE;
    }

    public static final String getEXTRACT_JOB() {
        return EXTRACT_JOB;
    }

    public static final String getANALYZE_COMPATIBILITY() {
        return ANALYZE_COMPATIBILITY;
    }

    public static final String getGENERATE_CV() {
        return GENERATE_CV;
    }

    public static final String getGENERATE_COVER_LETTER() {
        return GENERATE_COVER_LETTER;
    }

    public static final String getGENERATE_CV_DOC() {
        return GENERATE_CV_DOC;
    }
}
