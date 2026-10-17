package com.waleed.investapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class TradeFragment extends Fragment {

    private static final double BTC_PRICE = 62418.10;

    private boolean buyMode = true;
    private TextView segBuy;
    private TextView segSell;
    private TextView submit;
    private TextView estimate;
    private EditText amount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trade, container, false);

        segBuy = v.findViewById(R.id.seg_buy);
        segSell = v.findViewById(R.id.seg_sell);
        submit = v.findViewById(R.id.submit);
        estimate = v.findViewById(R.id.estimate);
        amount = v.findViewById(R.id.amount);

        segBuy.setOnClickListener(x -> setMode(true));
        segSell.setOnClickListener(x -> setMode(false));
        setMode(true);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateEstimate();
            }
        });

        submit.setOnClickListener(x -> placeOrder());
        return v;
    }

    private void setMode(boolean buy) {
        buyMode = buy;
        style(segBuy, buy);
        style(segSell, !buy);
        submit.setText(buy ? "Buy BTC" : "Sell BTC");
    }

    private void style(TextView seg, boolean on) {
        seg.setBackgroundResource(on ? R.drawable.bg_button_primary : 0);
        seg.setTextColor(requireContext().getColor(on ? R.color.white : R.color.text_secondary));
        seg.setTypeface(Typeface.create("sans-serif-medium", on ? Typeface.BOLD : Typeface.NORMAL));
    }

    private double parseAmount() {
        try {
            return Double.parseDouble(amount.getText().toString().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void updateEstimate() {
        double btc = parseAmount() / BTC_PRICE;
        estimate.setText(String.format(Locale.US, "≈ %.6f BTC", btc));
    }

    private void placeOrder() {
        double value = parseAmount();
        if (value <= 0) {
            Toast.makeText(requireContext(), "Enter an amount first", Toast.LENGTH_SHORT).show();
            return;
        }
        String msg = String.format(Locale.US, "%s order for £%.2f placed (demo)",
                buyMode ? "Buy" : "Sell", value);
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
        amount.setText("");
    }
}
