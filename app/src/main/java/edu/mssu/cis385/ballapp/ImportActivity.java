package edu.mssu.cis385.ballapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import edu.mssu.cis385.ballapp.model.Player;

public class ImportActivity extends AppCompatActivity {

    private LinearLayout mSubjectLayoutContainer;
    private StatFetcher mStatFetcher;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        mSubjectLayoutContainer = findViewById(R.id.subject_layout);

        // Show progress bar
        mLoadingProgressBar = findViewById(R.id.loading_progress_bar);
        mLoadingProgressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        mStatFetcher = new StatFetcher(this);
        mStatFetcher.fetchPlayers(mFetchListener, message);
    }

    private final StatFetcher.OnStatDataReceivedListener mFetchListener =
            new StatFetcher.OnStatDataReceivedListener() {

                @Override
                public void onPlayersReceived(List<Player> playerList) {

                    // Hide progress bar
                    mLoadingProgressBar.setVisibility(View.GONE);

                    // Create a checkbox for each subject
                    for (Player player : playerList) {
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setTextSize(24);
                        checkBox.setText(player.getText());
                        checkBox.setTag(player);
                        checkBox.setTextColor(Color.DKGRAY);
                        mSubjectLayoutContainer.addView(checkBox);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error loading players. Try again later.",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    mLoadingProgressBar.setVisibility(View.GONE);
                }
            };
}