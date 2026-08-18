package com.vidora.ai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private EditText promptInput;
    private Button generateButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        promptInput = findViewById(R.id.promptInput);
        generateButton = findViewById(R.id.generateButton);
        resultText = findViewById(R.id.resultText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenAiApiService apiService =
                retrofit.create(OpenAiApiService.class);

        generateButton.setOnClickListener(v -> {

            String userPrompt =
                    promptInput.getText().toString().trim();

            if (userPrompt.isEmpty()) {
                resultText.setText("Введите промпт!");
                return;
            }

            generateButton.setEnabled(false);
            resultText.setText("Генерация...");

            ChatRequest request =
                    new ChatRequest(userPrompt);

            apiService.getCompletion(request)
                    .enqueue(new Callback<ChatResponse>() {

                        @Override
                        public void onResponse(
                                Call<ChatResponse> call,
                                Response<ChatResponse> response) {

                            generateButton.setEnabled(true);

                            if (!response.isSuccessful()) {
                                resultText.setText(
                                        "Ошибка API: " + response.code()
                                );
                                return;
                            }

                            ChatResponse body = response.body();

                            if (body == null) {
                                resultText.setText(
                                        "Ошибка: пустой ответ сервера"
                                );
                                return;
                            }

                            if (body.choices == null ||
                                    body.choices.isEmpty()) {

                                resultText.setText(
                                        "Ошибка: сервер не вернул результат"
                                );
                                return;
                            }

                            if (body.choices.get(0) == null ||
                                    body.choices.get(0).message == null ||
                                    body.choices.get(0).message.content == null) {

                                resultText.setText(
                                        "Ошибка: неправильный ответ API"
                                );
                                return;
                            }

                            String answer =
                                    body.choices.get(0)
                                            .message
                                            .content;

                            resultText.setText(answer);
                        }

                        @Override
                        public void onFailure(
                                Call<ChatResponse> call,
                                Throwable t) {

                            generateButton.setEnabled(true);

                            resultText.setText(
                                    "Ошибка сети:\n" +
                                    t.getClass().getSimpleName() +
                                    "\n" +
                                    t.getMessage()
                            );
                        }
                    });
        });
    }
}
