package com.alexandermaxgorkun.coolstory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.alexandermaxgorkun.coolstory.domain.StoryDomain;
import com.alexandermaxgorkun.coolstory.entity.Paragraph;
import com.alexandermaxgorkun.coolstory.entity.Story;
import com.alexandermaxgorkun.coolstory.entity.validator.StoryValidator;
import com.alexandermaxgorkun.coolstory.entity.validator.Validator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Form for new story.
 *
 * @author Alexander Gorkun mindkilleralexs@gmail.com
 */
public class CreateStoryForm extends Activity {
    private Validator<Story> storyValidator;
    private StoryDomain storyDomain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_story_form);

        storyValidator = new StoryValidator();
        storyDomain = new StoryDomain();

        final EditText title = (EditText) findViewById(R.id.title);
        final EditText paragraph = (EditText) findViewById(R.id.paragraph);
        Button submitButton = (Button) findViewById(R.id.submit_story_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            private final Dialog invalidFormDialog;
            private final Dialog validFormDialog;
            private Dialog storyCreatedDialog;

            {
                final AlertDialog.Builder invalidFormValuesDialogBuilder = new AlertDialog.Builder(CreateStoryForm.this);
                invalidFormValuesDialogBuilder.setTitle(R.string.create_story_form_invalid_story);
                invalidFormDialog = invalidFormValuesDialogBuilder.create();
                final AlertDialog.Builder validFormValuesDialogBuilder = new AlertDialog.Builder(CreateStoryForm.this);
                validFormValuesDialogBuilder.setTitle(R.string.create_story_form_valid_story);
                validFormDialog = validFormValuesDialogBuilder.create();
                final AlertDialog.Builder createdDialogBuilder = new AlertDialog.Builder(CreateStoryForm.this);
                createdDialogBuilder.setTitle(R.string.create_story_form_story_created);
                createdDialogBuilder.setNeutralButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent backToMainMenu = new Intent(CreateStoryForm.this, MainMenu.class);
                        startActivity(backToMainMenu);
                    }
                });
                storyCreatedDialog = createdDialogBuilder.create();
            }

            @Override
            public void onClick(View v) {
                final Story story = new Story(title.getText().toString());
                story.getParagraphs().add(new Paragraph(paragraph.getText().toString()));
                if (!storyValidator.isValid(story)) {
                    invalidFormDialog.show();
                } else {
                    validFormDialog.show();
                    ExecutorService createProcessExecutor = Executors.newSingleThreadExecutor();
                    Future future = createProcessExecutor.submit(new Runnable() {
                        @Override
                        public void run() {
                            storyDomain.create(story);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    validFormDialog.dismiss();
                                    storyCreatedDialog.show();
                                }
                            });
                        }
                    });
                    try {
                        future.get(10, TimeUnit.SECONDS);
                    } catch (Exception ex) {
                        //well shit
                    }
                    if (!createProcessExecutor.isShutdown()) {
                        createProcessExecutor.shutdownNow();
                    }
                }
            }
        });
    }
}