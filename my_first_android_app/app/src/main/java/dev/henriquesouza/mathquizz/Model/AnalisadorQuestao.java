package dev.henriquesouza.mathquizz.Model;

public class AnalisadorQuestao {

    public boolean isRespostaCorreta(Questao questao, double resposta){
        return questao.getRespostaCorreta() == resposta;
    }

}
