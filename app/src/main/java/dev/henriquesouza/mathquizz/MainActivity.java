package dev.henriquesouza.mathquizz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dev.henriquesouza.mathquizz.Model.AnalisadorQuestao;
import dev.henriquesouza.mathquizz.Model.Questao;
import dev.henriquesouza.mathquizz.Model.QuestaoRepositorio;

public class MainActivity extends Activity {

    //Instancia o repositorio de questoes como uma variável global da classe
    private QuestaoRepositorio repositorio = new QuestaoRepositorio();
    //Setamos a variavel indice_questao como global e usaremos ela para dinamizar o indice do array de questoes
    private int indice_questao = 0;
    //Tornamos os Views globais dentro de nossa Activity de forma que ele possa ser acessado por todos os métodos
    private TextView textViewTextoQuestao;
    private Button botaoResposta1;
    private Button botaoResposta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Questao questao = repositorio.getListaQuestoes().get(indice_questao);

        textViewTextoQuestao = findViewById(R.id.txtQuestao);
        textViewTextoQuestao.setText(questao.getTexto());

        //View on click listener, carrega a lógica do click no botão.
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Casting necessário para tarnsformar a view num botão (view é a super classe
                de todos os Views (elementos de tela)                */
                final String resposta= ((Button)v).getText().toString();

                AnalisadorQuestao analisadorQuestao = new AnalisadorQuestao();
                Questao questao = repositorio.getListaQuestoes().get(indice_questao);

                String Mensagem;

                //Casting do parâmetro resposta para transforma-lo num Double.
                if(analisadorQuestao.isRespostaCorreta(questao,Double.valueOf(resposta))){

                    Mensagem = "Parabéns, resposta correta!";

                }else
                {
                    Mensagem = "Aah, resposta errada :(";
                }

                //Toast - notifica o usuário com a menssagem decorrente de sua escolha
                Toast.makeText(MainActivity.this, Mensagem, Toast.LENGTH_SHORT).show();
            }
        };

        //View OnLickListener para o botao proxima questao
        View.OnClickListener listenerProximaQuestao = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Incrementa o indice
                indice_questao++;

                //Obtem a nova questão baseada no indice
                Questao questao = repositorio.getListaQuestoes().get(indice_questao);

                //Seta os novaos valores aos Views
                textViewTextoQuestao.setText(questao.getTexto());
                botaoResposta1.setText(String.valueOf(questao.getRespostaCorreta()));
                botaoResposta2.setText(String.valueOf(questao.getRespostaIncorreta()));

            }
        };


        //Instancia o btnResposta1
        botaoResposta1 = findViewById(R.id.btnResposta1);
        //Seta a propriedade text como sendo o texto da questão
        botaoResposta1.setText(String.valueOf(questao.getRespostaCorreta()));
        //Setando um ClickListener no botaoResposta1
        botaoResposta1.setOnClickListener(listener);

        //Instancia o btnResposta2
        botaoResposta2 = findViewById(R.id.btnResposta2);
        //Seta a propriedade text como sendo o texto da questão
        botaoResposta2.setText(String.valueOf(questao.getRespostaIncorreta()));
        //Setando um ClickListener no botaoResposta2
        botaoResposta2.setOnClickListener(listener);

        //Instancia o botaoProximaPergunta
        Button botaoProximaPergunta = findViewById(R.id.btnProximaQuestao);
        //Seta a propriedade text como sendo "proxima pergunta"
        botaoProximaPergunta.setText("Próxima Pergunta");
        //Setando um ClickListener no botaoResposta2
        botaoProximaPergunta.setOnClickListener(listenerProximaQuestao);
    }
}
