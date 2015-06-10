package com.bakel.epp.bakel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;


public class Game extends ActionBarActivity {

    private String curentQuestion = null;
    private Hashtable<String, String> table = new Hashtable<String, String>();
    private int lifes = 3;
    private MyTimer timer;
    private Thread thread;
    private final int totalLevels = 10;
    private int score = 0, level = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new MyTimer(this);
        thread = new Thread(timer);
        thread.start();
        setContentView(R.layout.activity_game_menu);
        generateQuestions();
        addRandomQuestion();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game_relative_layout);
        rl.addView(timer, new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(), 70));

    }


    /**
     * generate my questions with answers
     * first question , then corect answer , then wrong answers
     */
    public void generateQuestions() {

//        for (int i = 0; i < Database.size(); i++) {
//            addQuestion(database.getQuestion(i), database.getCorectAnswer(i), database.getWrongAnser1(i), database.getWrongAnser2(i), database.getWrongAnser3(i));
//        }
        addQuestion("Example question", "correct", "Wrong 1", "wrong 2", "wrong 3");
        addQuestion("Other Example question", "press me ", " not press me 1", " not press me 2", "not press me 3");
    }


    /**
     * buttons click listener
     *
     * @param v
     */
    public void onClickfunction(View v) {
        Button b = (Button) v;
        try {
            if (isAnswerCorrect(curentQuestion, b.getText().toString())) {
//            Log.e(b.getText().toString(), " is correct answer");
                correctAnswerFunction();
            } else {
//            Log.e(b.getText().toString(), " is wrong answer");
                wrongAnswerFunction();
            }
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "No enough questions", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * put question from table , and remove the question from table
     */
    public void addRandomQuestion() {
        Iterator iter = table.keySet().iterator();
        TextView screen = (TextView) findViewById(R.id.textView2);
        Button b1 = (Button) findViewById(R.id.answer_1);
        Button b2 = (Button) findViewById(R.id.answer_2);
        Button b3 = (Button) findViewById(R.id.answer_3);
        Button b4 = (Button) findViewById(R.id.answer_4);
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        screen.setText("");
        if (table.isEmpty()) {
            gameOverFunction();
        }

        ArrayList<Button> btns = new ArrayList<Button>();
        btns.add(b1);
        btns.add(b2);
        btns.add(b3);
        btns.add(b4);
        Collections.shuffle(btns);

        int random_number = (int) (Math.random() * table.size());
        int counter = 0;


        while (iter.hasNext()) {

            String key = (String) iter.next();
            if (counter != random_number) {
                counter++;
                continue;
            } else {
                screen.setText(key);
                curentQuestion = key;
                String answerData = table.get(key);
                ArrayList<String> answers = new ArrayList<String>();
                for (int i = 0; i < btns.size(); i++) {
                    answers.add(answerData.split(",")[i]);
                }
                while (!btns.isEmpty()) {
                    btns.remove(0).setText(answers.remove(0));
                }
                break;
            }
        }
    }

    /**
     * add parametrs
     *
     * @param question      the original question
     * @param correctAnswer the correct answer
     * @param ans1          wrong answer 1
     * @param ans2          wrong answer 2
     * @param ans3          wrong answer 3
     */
    public void addQuestion(String question, String correctAnswer, String ans1, String ans2, String ans3) {
        table.put(question, correctAnswer + "," + ans1 + "," + ans2 + "," + ans3);
    }


    /**
     * @param question the original question
     * @param answer   the selected answer
     * @return if answer is correct or mistake
     */
    public boolean isAnswerCorrect(String question, String answer) {
        boolean isCorret = false;

        if (getCorectAnswer(question).equals(answer)) {
            isCorret = true;
        }
        return isCorret;
    }


    /**
     * get the corect answer of each question
     *
     * @param question
     * @return
     */
    public String getCorectAnswer(String question) {
        String out = null;
//        Log.e("contains  =",Boolean.toString(table.contains(question)));
//        Log.e("contains key =",Boolean.toString(table.containsKey(question)));
//        Log.e("contains value =",Boolean.toString(table.containsValue(question)));
//        Log.e(question,table.get(question));
        if (question != null)
            if (table.containsKey(question)) {

                out = table.get(question);
                out = out.split(",")[0];
            } else {
                Log.e("problem at : ", "Game GetCorectAnswer function");
            }
        return out;
    }

    /**
     * reaction when answer is wrong
     */
    public void wrongAnswerFunction() {
        lifes--;
        level++;
        score -= 20;
        updateScore();
        if (level >= totalLevels) {
            gameOverFunction();
        }
        if (lifes < 0) {
            gameOverFunction();
        }
        Toast.makeText(this, "Corect answer: " + getCorectAnswer(curentQuestion), Toast.LENGTH_SHORT).show();
        table.remove(curentQuestion);
        curentQuestion = null;
        addRandomQuestion();
        timer.resetTimer();
        if (!thread.isAlive()) {
            thread = new Thread(timer);
            thread.start();
        }
    }

    /**
     * reaction when answer is correct
     */
    public void correctAnswerFunction() {
        level++;
        if (level >= totalLevels) {
            gameOverFunction();
        }
        score += 100;
        updateScore();
        timer.resetTimer();
        table.remove(curentQuestion);
        curentQuestion = null;
        addRandomQuestion();
    }


    /**
     * game over function
     */
    public void gameOverFunction() {

        Intent intent = new Intent(Game.this, GameOverActivity.class);
        intent.putExtra("Score", score);
        timer.setGameOver(true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //   super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        timer.setGameOver(true);
    }

    public void updateScore() {
        TextView txt = (TextView) findViewById(R.id.score);
        txt.setText("Score : " + Integer.toString(score));
    }

    public int getScore() {
        return score;
    }

    public int getRemainingLifes() {

        return lifes;

    }

}
