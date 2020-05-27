package com.acidcarpet.balance.tutorial;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.main.MainActivity;

public class TutorialActivity extends AppCompatActivity {

    private static Screen screen = Screen.INTRO;
    private static boolean active = false;

    public static void activate(){
        active = true;
        screen = Screen.INTRO;
    }
    public static void deactivate(){
        active = false;
    }
    public static void change_screen(){
        if(active){
            switch (screen){
                case INTRO: screen = Screen.GOOD; break;
                case GOOD: screen = Screen.BAD; break;
                case BAD: screen = Screen.SUMMARY; break;
                case SUMMARY:
                    active = false;

            }
        }
    }
    public Screen current_screen(){
        if(screen ==null) screen = Screen.INTRO;
        return screen;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutorial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close_button:

                TutorialActivity.activate();
                startActivity(new Intent(this, MainActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!active) {
            startActivity(new Intent(this, MainActivity.class));
        }


        setContentView(R.layout.activity_tutorial);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.tutorial_fragment_container);


        if (fragment == null) {
            fragment = TutorialFragment.newInstance(screen);

            fm.beginTransaction()
                    .add(R.id.tutorial_fragment_container, fragment)
                    .commit();
        }




    }

    public enum Screen{
        INTRO("INTRO"),
        GOOD("GOOD"),
        BAD("BAD"),
        SUMMARY("SUMMARY");

        String text;

        Screen(String text){
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}