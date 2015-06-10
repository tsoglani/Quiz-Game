package com.bakel.epp.bakel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class TopScoresActivity extends ActionBarActivity {
    private ScrollView scroll;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scores);
        scroll = new ScrollView(this);
        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        ll = new LinearLayout(this);
        ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        ll.setOrientation(1);
        scroll.addView(ll, new LayoutParams(getWindowManager()
                .getDefaultDisplay().getWidth(), getWindowManager()
                .getDefaultDisplay().getHeight()));

        DB db = new DB(getBaseContext(), null, null, 1);
        TextView title = new TextView(this);
        title.setText("TOP 5 SCORES");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(Color.BLACK);
        title.setTextColor(Color.WHITE);
        ll.addView(title, new LayoutParams(getWindowManager()
                .getDefaultDisplay().getWidth(), getWindowManager()
                .getDefaultDisplay().getHeight() / 10));


        int scoreCounter = 1;

        for (Integer s : db.getValue()) {
            TextView txt = new TextView(this);
            txt.setText(Integer.toString(scoreCounter++) + ".                " + Integer.toString(s));

            ll.addView(txt, new LayoutParams(getWindowManager()
                    .getDefaultDisplay().getWidth(), getWindowManager()
                    .getDefaultDisplay().getHeight() / 10));
            ll.addView(new View(this), new LayoutParams(getWindowManager()
                    .getDefaultDisplay().getWidth(), getWindowManager()
                    .getDefaultDisplay().getHeight() / 200));

        }


        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        frame.addView(scroll);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_scores, menu);
        return true;
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
