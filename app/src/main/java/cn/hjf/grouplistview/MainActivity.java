package cn.hjf.grouplistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void show_no(View v) {
        Intent intent = new Intent(this, ShowNo.class);
        startActivity(intent);
    }

    public void show_has(View v) {
        Intent intent = new Intent(this, ShowHas.class);
        startActivity(intent);
    }

    public void hide_no(View v) {
        Intent intent = new Intent(this, HideNo.class);
        startActivity(intent);
    }

    public void hide_has(View v) {
        Intent intent = new Intent(this, HideHas.class);
        startActivity(intent);
    }
}
