package com.waleed.investapp;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    public static final int TAB_HOME = 0;
    public static final int TAB_MARKETS = 1;
    public static final int TAB_TRADE = 2;
    public static final int TAB_PROFILE = 3;

    private static final String KEY_TAB = "current_tab";

    private LinearLayout[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = new LinearLayout[]{
                findViewById(R.id.nav_home),
                findViewById(R.id.nav_markets),
                findViewById(R.id.nav_trade),
                findViewById(R.id.nav_profile)
        };

        for (int i = 0; i < tabs.length; i++) {
            final int index = i;
            tabs[i].setOnClickListener(v -> selectTab(index));
        }

        if (savedInstanceState == null) {
            selectTab(TAB_HOME);
        } else {
            updateNav(savedInstanceState.getInt(KEY_TAB, TAB_HOME));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TAB, currentTab);
    }

    private int currentTab = TAB_HOME;

    public void selectTab(int index) {
        Fragment fragment;
        switch (index) {
            case TAB_MARKETS:
                fragment = new MarketsFragment();
                break;
            case TAB_TRADE:
                fragment = new TradeFragment();
                break;
            case TAB_PROFILE:
                fragment = new ProfileFragment();
                break;
            case TAB_HOME:
            default:
                fragment = new HomeFragment();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        updateNav(index);
    }

    private void updateNav(int selected) {
        currentTab = selected;
        for (int i = 0; i < tabs.length; i++) {
            boolean on = i == selected;
            ImageView icon = (ImageView) tabs[i].getChildAt(0);
            TextView label = (TextView) tabs[i].getChildAt(1);

            int color = getColor(on ? R.color.accent : R.color.text_secondary);
            icon.setImageTintList(ColorStateList.valueOf(color));
            label.setTextColor(color);
            label.setTypeface(Typeface.create("sans-serif-medium", on ? Typeface.BOLD : Typeface.NORMAL));

            if (i == TAB_HOME) {
                icon.setImageResource(on ? R.drawable.ic_home_filled : R.drawable.ic_home_outline);
            }
        }
    }
}
