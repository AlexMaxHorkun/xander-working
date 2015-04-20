package com.alexandermaxgorkun.coolstory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alexandermaxgorkun.coolstory.domain.StoryDomain;
import com.alexandermaxgorkun.coolstory.entity.Story;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Initial activity, main screen.
 *
 * @author Alexander Gorkun mindkilleralexs@gmail.com
 */
public class MainMenu extends Activity {
    private boolean storiesLoaded = false;

    private StoryDomain storyDomain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        storyDomain = new StoryDomain();
    }

    public void onResume() {
        super.onResume();
        Timer minHideGreetingTimer = new Timer("min_greeting_hide");
        Thread loadingThread = new Thread(new Loading());
        loadingThread.start();
        minHideGreetingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (storiesLoaded) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideGreeting();
                        }
                    });
                }
            }
        }, 1000);

        Timer maxHideGreetingTimer = new Timer("max_greeting_hide");
        maxHideGreetingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideGreeting();
                    }
                });
            }
        }, 5000);
    }

    private void hideGreeting() {
        View greetingWidget = findViewById(R.id.greeting);
        View mainWidget = findViewById(R.id.main_menu_block);
        greetingWidget.setVisibility(View.GONE);
        mainWidget.setVisibility(View.VISIBLE);
    }

    private class Loading implements Runnable {
        public void run() {
            final Collection<Story> stories = storyDomain.find(StoryDomain.ORDER.CREATED_DESC, 5);
            runOnUiThread(new Runnable() {
                Collection<Story> storyList = stories;

                @Override
                public void run() {
                    ViewGroup mainWidget = (ViewGroup) findViewById(R.id.main_menu_block);
                    for (Story story : stories) {
                        TextView storyWidget = new TextView(MainMenu.this);
                        storyWidget.setText(story.getTitle() + story.getCreated());
                        storyWidget.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        mainWidget.addView(storyWidget);
                        storyWidget.setVisibility(View.VISIBLE);
                    }
                    storiesLoaded = true;
                }
            });
        }
    }
}