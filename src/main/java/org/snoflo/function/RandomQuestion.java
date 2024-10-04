package org.snoflo.function;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.snoflo.domain.Question;

public class RandomQuestion {

    private Random random;

    public RandomQuestion() {
        this.random = new Random();
    }

    
    public Object getResultQuestion(List<Question> list) throws IllegalArgumentException, IllegalAccessException {

       /* field를 QuestionDto로 변환해보자
        */ 
            Question question = getRandomElement(list);
            Field[] fields = Question.class.getDeclaredFields();
            int randomIndex = random.nextInt(fields.length);

            fields[randomIndex].setAccessible(true);
            Object questionField = fields[randomIndex].get(question);

            int otherIndex = (randomIndex + 1) % fields.length;
            fields[otherIndex].setAccessible(true);
            Object otherField = fields[otherIndex].get(question);

        return null;

    }

    private Question getRandomElement(List<Question> list) {
        int randomElement = random.nextInt(list.size());
        Question element = list.get(randomElement);
        list.remove(element);
        return element;
    }

}
