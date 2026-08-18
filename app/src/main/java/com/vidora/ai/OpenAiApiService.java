package com.vidora.ai;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAiApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    Call<ChatResponse> getCompletion(
            @Body ChatRequest request
    );
}
