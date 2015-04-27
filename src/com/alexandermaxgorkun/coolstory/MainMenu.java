package com.alexandermaxgorkun.coolstory;

import android.app.Activity;
import android.content.Intent;
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
        View createButton = (View) findViewById(R.id.create_story_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createForm = new Intent(MainMenu.this, CreateStoryForm.class);
                startActivity(createForm);
            }
        });
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
        private class ShowStories implements Runnable {
            private final Collection<Story> stories;
            private final boolean append;

            public ShowStories(Collection<Story> s, boolean append) {
                stories = s;
                this.append = append;
            }

            @Override
            public void run() {
                ViewGroup recentStoriesView = (ViewGroup) findViewById(R.id.recent_stories);
                if (!append) {
                    recentStoriesView.removeAllViews();
                }
                for (Story story : stories) {
                    TextView storyWidget = new TextView(MainMenu.this);
                    storyWidget.setText(story.getTitle() + story.getCreated());
                    storyWidget.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    recentStoriesView.addView(storyWidget);
                    storyWidget.setVisibility(View.VISIBLE);
                }
                storiesLoaded = true;
            }
        }

        public void run() {
            Collection<Story> stories = storyDomain.findLocal(StoryDomain.ORDER.CREATED_DESC, 5);
            if (stories.size() > 0) {
                runOnUiThread(new ShowStories(stories, false));
            }
            stories = storyDomain.find(StoryDomain.ORDER.CREATED_DESC, 5);
            runOnUiThread(new ShowStories(stories, false));
        }
    }
}