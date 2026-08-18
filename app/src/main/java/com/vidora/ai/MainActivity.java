package com.vidora.ai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build();

        OpenAiApiService apiService =
                retrofit.create(OpenAiApiService.class);

        generateButton.setOnClickListener(v -> {

            String prompt =
                    promptInput.getText()
                            .toString()
                            .trim();

            if (prompt.isEmpty()) {
                resultText.setText("Введите промпт!");
                return;
            }

            generateButton.setEnabled(false);
            resultText.setText("Генерация...");

            ChatRequest request =
                    new ChatRequest(prompt);

            apiService.getCompletion(request)
                    .enqueue(new Callback<ChatResponse>() {

                        @Override
                        public void onResponse(
                                Call<ChatResponse> call,
                                Response<ChatResponse> response) {

                            generateButton.setEnabled(true);

                            if (!response.isSuccessful()) {

                                resultText.setText(
                                        "Ошибка API: HTTP "
                                                + response.code()
                                );

                                return;
                            }

                            ChatResponse body =
                                    response.body();

                            if (body == null) {
                                resultText.setText(
                                        "Ошибка: пустой ответ"
                                );
                                return;
                            }

                            if (body.choices == null ||
                                    body.choices.isEmpty()) {

                                resultText.setText(
                                        "Ошибка: нет результата"
                                );
                                return;
                            }

                            if (body.choices.get(0) == null ||
                                    body.choices.get(0).message == null ||
                                    body.choices.get(0)
                                            .message.content == null) {

                                resultText.setText(
                                        "Ошибка: неправильный ответ"
                                );
                                return;
                            }

                            resultText.setText(
                                    body.choices.get(0)
                                            .message.content
                            );
                        }

                        @Override
                        public void onFailure(
                                Call<ChatResponse> call,
                                Throwable t) {

                            generateButton.setEnabled(true);

                            String error = t.getMessage();

                            if (error == null) {
                                error = "Неизвестная ошибка";
                            }

                            resultText.setText(
                                    "Ошибка подключения:\n"
                                            + error
                            );
                        }
                    });
        });
    }
}
