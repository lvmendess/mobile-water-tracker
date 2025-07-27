package com.example.ex2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText peso;
    private RecyclerView recycle;
    private TextView bebeu, naoBebeu;
    private BotaoAdapter botaoAdapter;
    private int totalCopos = 0;
    private Button calcular, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        peso = findViewById(R.id.peso);
        recycle = findViewById(R.id.recycle);
        bebeu = findViewById(R.id.quantBebida);
        naoBebeu = findViewById(R.id.quantFalta);
        calcular = findViewById(R.id.calcular);
        reset = findViewById(R.id.reset);

    }
}