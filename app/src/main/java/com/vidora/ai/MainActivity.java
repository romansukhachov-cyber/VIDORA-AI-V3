package com.vidora.ai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText promptInput = findViewById(R.id.promptInput);
        Button generateButton = findViewById(R.id.generateButton);
        TextView resultText = findViewById(R.id.resultText);

        // Настройка Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenAiApiService apiService = retrofit.create(OpenAiApiService.class);

        generateButton.setOnClickListener(v -> {
            String userPrompt = promptInput.getText().toString();
            if (userPrompt.isEmpty()) {
                resultText.setText("Введите промпт!");
                return;
            }

            resultText.setText("Генерация...");
            
            // Отправка запроса
            ChatRequest request = new ChatRequest(userPrompt);
            apiService.getCompletion(request).enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String answer = response.body().choices.get(0).message.content;
                        resultText.setText(answer);
                    } else {
                        resultText.setText("Ошибка: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    resultText.setText("Ошибка сети: " + t.getMessage());
                }
            });
        });
    }
}
