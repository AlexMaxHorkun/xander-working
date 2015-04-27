package com.alexandermaxgorkun.coolstory;

import android.app.Activity;
import android.os.Bundle;

/**
 * Form for new story.
 *
 * @author Alexander Gorkun mindkilleralexs@gmail.com
 */
public class CreateStoryForm extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_story_form);
    }
}