package dev.henriquesouza.mathquizz.Model;

import java.util.ArrayList;
import java.util.List;

public class QuestaoRepositorio {

    public List<Questao> getListaQuestoes()
    {
        return new ArrayList<Questao>(){{
            add(new Questao("Primeira questão", 1.0, 2.5));
            add(new Questao("Segunda questão", 5, 10));
            add(new Questao("Terceira questão", 5, 10));
            add(new Questao("Quarta questão", 5, 10));
            add(new Questao("Quinta questão", 5, 10));

        }};
    }

}
