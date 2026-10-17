package com.waleed.investapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String[][] HOLDINGS = {
            {"₿", "Bitcoin", "BTC", "£9,820.40", "+2.14%", "#F7931A", "1"},
            {"Ξ", "Ethereum", "ETH", "£4,310.12", "−0.82%", "#627EEA", "0"},
            {"A", "Apple", "AAPL", "£2,640.00", "+1.20%", "#111111", "1"}
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        setupBalance(v);
        setupPills(v);
        RowBinder.bindAssets(requireContext(), (LinearLayout) v.findViewById(R.id.holdings), HOLDINGS);

        v.findViewById(R.id.see_all).setOnClickListener(x -> openTab(MainActivity.TAB_MARKETS));
        v.findViewById(R.id.action_buy).setOnClickListener(x -> openTab(MainActivity.TAB_TRADE));
        v.findViewById(R.id.action_sell).setOnClickListener(x -> openTab(MainActivity.TAB_TRADE));
        v.findViewById(R.id.action_swap).setOnClickListener(x -> toast("Swap - coming soon"));
        v.findViewById(R.id.action_deposit).setOnClickListener(x -> toast("Deposit - coming soon"));
        v.findViewById(R.id.btn_search).setOnClickListener(x -> openTab(MainActivity.TAB_MARKETS));
        v.findViewById(R.id.btn_bell).setOnClickListener(x -> toast("No new notifications"));

        return v;
    }

    private void openTab(int tab) {
        ((MainActivity) requireActivity()).selectTab(tab);
    }

    private void toast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setupBalance(View v) {
        TextView balance = v.findViewById(R.id.balance);
        SpannableString s = new SpannableString("£18,240.56");
        int dot = s.toString().indexOf('.');
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#B4B4BC")),
                dot, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        balance.setText(s);
    }

    private void setupPills(View v) {
        final LinearLayout pills = v.findViewById(R.id.pills);
        for (int i = 0; i < pills.getChildCount(); i++) {
            final TextView pill = (TextView) pills.getChildAt(i);
            pill.setOnClickListener(x -> selectPill(pills, pill));
        }
        selectPill(pills, (TextView) pills.getChildAt(1)); // 1W
    }

    private void selectPill(LinearLayout pills, TextView selected) {
        for (int i = 0; i < pills.getChildCount(); i++) {
            TextView p = (TextView) pills.getChildAt(i);
            boolean on = p == selected;
            p.setBackgroundResource(on ? R.drawable.bg_pill_selected : 0);
            p.setTextColor(requireContext().getColor(on ? R.color.text_primary : R.color.text_secondary));
            p.setTypeface(Typeface.create("sans-serif-medium", on ? Typeface.BOLD : Typeface.NORMAL));
        }
    }
}
