package com.waleed.investapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MarketsFragment extends Fragment {

    // Sample data for the demo
    private static final String[][] ASSETS = {
            {"₿", "Bitcoin", "BTC", "£62,418.10", "+2.14%", "#F7931A", "1"},
            {"Ξ", "Ethereum", "ETH", "£2,310.55", "−0.82%", "#627EEA", "0"},
            {"S", "Solana", "SOL", "£142.30", "+3.40%", "#7C5CE6", "1"},
            {"A", "Apple", "AAPL", "£176.40", "+1.20%", "#111111", "1"},
            {"T", "Tesla", "TSLA", "£189.75", "−1.65%", "#D6454B", "0"},
            {"M", "Microsoft", "MSFT", "£312.20", "+0.45%", "#2F7FD8", "1"}
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_markets, container, false);
        RowBinder.bindAssets(requireContext(), (LinearLayout) v.findViewById(R.id.markets_list), ASSETS);
        return v;
    }
}
