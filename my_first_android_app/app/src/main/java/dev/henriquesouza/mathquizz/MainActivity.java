package dev.henriquesouza.mathquizz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import dev.henriquesouza.mathquizz.Model.AnalisadorQuestao;
import dev.henriquesouza.mathquizz.Model.Questao;
import dev.henriquesouza.mathquizz.Model.QuestaoRepositorio;

public class MainActivity extends Activity {

    public static final String INDICE_QUESTAO = "INDICE_QUESTAO";
    //Instancia o repositorio
    private QuestaoRepositorio repositorio = new QuestaoRepositorio();

    //Setamos a variavel indice_questao como global
    private int indice_questao = 0;

    //Guarda informações de nossa localização / cultura
    private final Locale locale = new Locale("pt", "BR");

    //Tornamos os Views globais dentro de nossa Activity
    private TextView textViewTextoQuestao;
    private Button botaoResposta1;
    private Button botaoResposta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //A questão recebe uma questão contida no repositório
        Questao questao = repositorio.getListaQuestoes().get(indice_questao);

        textViewTextoQuestao = findViewById(R.id.txtQuestao); //Instancia textView
        textViewTextoQuestao.setText(questao.getTexto()); //Seta propriedade text como sendo a questão

        //View on click listener, carrega a lógica do click no botão.
        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {

                /*Casting necessário para tarnsformar a view num botão (view é a super classe
                de todos os Views (elementos de tela)                */
                final String resposta = ((Button)v).getText().toString();

                AnalisadorQuestao analisadorQuestao = new AnalisadorQuestao();
                Questao questao = repositorio.getListaQuestoes().get(indice_questao);

                String Mensagem;

                try {
                    //Formata nossa resposta para numero
                    NumberFormat format = NumberFormat.getInstance(locale);
                    Number number = format.parse(resposta);

                    //Casting do parâmetro resposta para transforma-lo num Double.
                    if (analisadorQuestao.isRespostaCorreta(questao, number.doubleValue())) {

                        Mensagem = "Parabéns, resposta correta!";

                    } else {
                        Mensagem = "Aah, resposta errada :(";
                    }
                }catch(ParseException e){
                    Mensagem = e.getMessage();
                }
                //Toast - notifica o usuário com a menssagem decorrente de sua escolha
                Toast.makeText(MainActivity.this, Mensagem, Toast.LENGTH_SHORT).show();
            }
        };

        //View OnLickListener para o botao proxima questao
        View.OnClickListener listenerProximaQuestao = new View.OnClickListener() {

            public void onClick(View view) {

                //Incrementa o indice
                indice_questao++;

                //Trata erro de IndexOutOfBounds Exception
                if(indice_questao >= repositorio.getListaQuestoes().size()){
                    indice_questao = 0;
                }

                ExibirQuestao(indice_questao);
            }
        };


        botaoResposta1 = findViewById(R.id.btnResposta1); //Instancia btnResposta1
        botaoResposta1.setText(String.valueOf(questao.getRespostaCorreta())); // Define propriedade text
        botaoResposta1.setOnClickListener(listener); // Seta ClickListener


        botaoResposta2 = findViewById(R.id.btnResposta2);
        botaoResposta2.setText(String.valueOf(questao.getRespostaIncorreta()));
        botaoResposta2.setOnClickListener(listener);


        Button botaoProximaPergunta = findViewById(R.id.btnProximaQuestao);
        botaoProximaPergunta.setOnClickListener(listenerProximaQuestao);

        if(savedInstanceState != null){
            indice_questao = savedInstanceState.getInt(INDICE_QUESTAO);
        }

        ExibirQuestao(indice_questao);
    }

    //Método usado para guardar o estado da aplicação (id da questao)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(INDICE_QUESTAO, indice_questao);
    }

    public void ExibirQuestao(final int indice_questao){
        //Obtem a nova questão baseada no indice
        Questao questao = repositorio.getListaQuestoes().get(indice_questao);

        //Para formatar a exibição dos valores
        String respostaCorreta = String.format(locale,"%.2f", questao.getRespostaCorreta());
        String respostaIncorreta = String.format(locale,"%.2f", questao.getRespostaIncorreta());

        //Seta os novaos valores aos Views
        textViewTextoQuestao.setText(questao.getTexto());
        botaoResposta1.setText(respostaCorreta);
        botaoResposta2.setText(respostaIncorreta);
    }
}
