package com.example.ex2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

        if(savedInstanceState!=null){
            onRestoreInstanceState(savedInstanceState);
            Toast.makeText(this, "dados restaurados", Toast.LENGTH_SHORT).show();
        }else{
            botaoAdapter = new BotaoAdapter(0, position -> atualizaProgresso());
            recycle.setAdapter(botaoAdapter);
            Toast.makeText(this, "inicializando", Toast.LENGTH_SHORT).show();
        }

        //botaoAdapter = new BotaoAdapter(0, position -> atualizaProgresso());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        //recycle.setAdapter(botaoAdapter);

        calcular.setOnClickListener(v -> calcularCopos());
        reset.setOnClickListener(v -> reseta());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("PESO", peso.getText().toString());
        outState.putInt("TOTAL_COPOS", totalCopos);

        if(botaoAdapter!=null){
            boolean[] estados = new boolean[botaoAdapter.getItemCount()];
            for(int i = 0; i<botaoAdapter.getItemCount(); i++){
                estados[i] = botaoAdapter.isClicked(i);
            }
            outState.putBooleanArray("ESTADOS", estados);
        }
        Toast.makeText(this, "instanceState saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String pesoSalvo = savedInstanceState.getString("PESO");
        peso.setText(pesoSalvo);

        int coposSalvos = savedInstanceState.getInt("TOTAL_COPOS", 0);
        if(coposSalvos > 0){
            totalCopos = coposSalvos;
        }

        boolean[] estadosSalvos = savedInstanceState.getBooleanArray("ESTADOS_SALVOS");
        if(estadosSalvos!=null){
            botaoAdapter = new BotaoAdapter(totalCopos, position -> atualizaProgresso());
        }

        for (int i = 0; i < estadosSalvos.length; i++){
            if(estadosSalvos[i]){
                botaoAdapter.setClicked(i, true);
            }
        }

        recycle.setAdapter(botaoAdapter);
        atualizaProgresso();
        Toast.makeText(this, "restaurado", Toast.LENGTH_SHORT).show();
    }

    private void calcularCopos(){
        try {
            double pesoInput = Double.parseDouble(peso.getText().toString());
            double totalMl = pesoInput * 35;
            totalCopos = (int) Math.ceil(totalMl / 150);

            botaoAdapter = new BotaoAdapter(totalCopos, position -> atualizaProgresso());
            recycle.setAdapter(botaoAdapter);

            atualizaProgresso();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "peso inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizaProgresso() {
        int drankMl = botaoAdapter.getClickedCount() * 150;
        int remainingMl = totalCopos * 150 - drankMl;

        bebeu.setText(drankMl + " ml");
        naoBebeu.setText(remainingMl + " ml");
    }

    private void reseta() {
        if (botaoAdapter != null) {
            botaoAdapter.resetAll();
        }
        peso.setText("");
        totalCopos = 0;
        botaoAdapter = new BotaoAdapter(0, position -> atualizaProgresso());
        recycle.setAdapter(botaoAdapter);
        bebeu.setText("0 ml");
        naoBebeu.setText("0 ml");
    }
    
}