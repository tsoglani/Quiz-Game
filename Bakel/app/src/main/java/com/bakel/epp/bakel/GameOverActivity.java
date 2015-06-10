package com.bakel.epp.bakel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class GameOverActivity extends ActionBarActivity {
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        score = extras.getInt("Score");
        DB db= new DB(this,null,null,1);
        db.addValue(Integer.toString(score));
        setContentView(R.layout.activity_game_over);
        TextView txtView=(TextView)findViewById(R.id.score_view);
        txtView.setText("Score : "+Integer.toString(score));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    private void gotoMenu() {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void MenuFunction(View v) {
        gotoMenu();
    }

    @Override
    public void onBackPressed() {
        gotoMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
