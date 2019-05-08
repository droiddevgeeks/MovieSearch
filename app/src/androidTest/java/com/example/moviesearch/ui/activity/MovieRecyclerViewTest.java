package com.example.moviesearch.ui.activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MovieRecyclerViewTest {
    private static final int ITEM_BELOW_THE_FOLD = 20;

    @Before
    public void launchActivity() {
        ActivityScenario.launch(MainActivity.class);
    }

}
