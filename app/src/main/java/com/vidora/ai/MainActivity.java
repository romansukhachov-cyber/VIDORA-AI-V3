package com.vidora.ai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим элементы интерфейса
        EditText promptInput = findViewById(R.id.promptInput);
        Button generateButton = findViewById(R.id.generateButton);
        TextView resultText = findViewById(R.id.resultText);

        // Добавляем действие при клике на кнопку
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPrompt = promptInput.getText().toString();
                if (!userPrompt.isEmpty()) {
                    resultText.setText("Генерация для: " + userPrompt);
                    // Здесь позже добавим вызов AI
                } else {
                    resultText.setText("Пожалуйста, введите промпт!");
                }
            }
        });
    }
}
