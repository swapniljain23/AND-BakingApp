package com.swapniljain.bakingapp.activity;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Step;

import java.util.List;

public class StepActivity extends AppCompatActivity {

    public static String STEPS_EXTRA = "steps_extra";
    public static String POSITION_EXTRA = "position_extra";
    public static String RECIPE_NAME_EXTRA = "recipe_name_extra";

    public Toolbar mToolBar;
    private Button mPreviousButton;
    private Button mNextButton;

    private FragmentManager mFragmentManager;
    private StepActivityFragment mStepActivityFragment;
    private String mRecipeName = "";

    private int mDefaultPosition = 0;
    private int mCurrentPosition;
    private List<Step> mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mPreviousButton = findViewById(R.id.previous_button);
        mNextButton = findViewById(R.id.next_button);
        mFragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if (intent == null) {
            // Handle error here.
            finish();
        }

        mSteps = intent.getParcelableArrayListExtra(STEPS_EXTRA);
        mRecipeName = intent.getStringExtra(RECIPE_NAME_EXTRA);
        if (savedInstanceState == null) {
            mCurrentPosition = intent.getIntExtra(POSITION_EXTRA, mDefaultPosition);
            populateUI();
        } else {
            mCurrentPosition = savedInstanceState.getInt(POSITION_EXTRA);
        }

        mToolBar.setTitle(mRecipeName);
        mToolBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_previous_white_24dp));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click.
                finish();
            }
        });

        enableButtons();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_EXTRA, mCurrentPosition);
    }

    // Private
    private void enableButtons() {
        if (mCurrentPosition == mSteps.size() - 1) {
            mNextButton.setEnabled(false);
        } else if (mCurrentPosition == 0) {
            mPreviousButton.setEnabled(false);
        } else {
            mNextButton.setEnabled(true);
            mPreviousButton.setEnabled(true);
        }
    }

    public void onClickNextButton(View view) {
        Log.d("onClickNextButton", "onClickNextButton");
        mCurrentPosition++;
        enableButtons();
        populateUI();
    }

    public void onClickPreviousButton(View view) {
        Log.d("onClickPreviousButton", "onClickPreviousButton");
        mCurrentPosition--;
        enableButtons();
        populateUI();
    }

    private void populateUI() {
        Step step = mSteps.get(mCurrentPosition);
        Log.d("Steps", step.toString());
        String stepDescription = step.getDescription();
        String videoUrl = step.getVideoUrl();
        //String thumbnailUrl = step.getThumbnailUrl();

        Bundle bundle = new Bundle();
        bundle.putString(StepActivityFragment.STEP_VIDEO_URL_EXTRA, videoUrl);
        bundle.putString(StepActivityFragment.STEP_DESCRIPTION_EXTRA, stepDescription);

        mStepActivityFragment = new StepActivityFragment();
        mStepActivityFragment.setArguments(bundle);

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_step, mStepActivityFragment)
                .commit();

    }
}
