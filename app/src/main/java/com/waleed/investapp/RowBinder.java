package com.waleed.investapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/** Small helper that fills the white list cards with rows. */
public final class RowBinder {

    private RowBinder() {
    }

    /**
     * Each item: symbol, name, ticker, value, change, tile colour, positive ("1" or "0").
     */
    public static void bindAssets(Context ctx, LinearLayout list, String[][] items) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        list.removeAllViews();

        for (int i = 0; i < items.length; i++) {
            final String[] h = items[i];
            View row = inflater.inflate(R.layout.item_holding, list, false);

            TextView tile = row.findViewById(R.id.tile);
            tile.setText(h[0]);
            GradientDrawable circle = new GradientDrawable();
            circle.setShape(GradientDrawable.OVAL);
            circle.setColor(Color.parseColor(h[5]));
            tile.setBackground(circle);

            ((TextView) row.findViewById(R.id.name)).setText(h[1]);
            ((TextView) row.findViewById(R.id.ticker)).setText(h[2]);
            ((TextView) row.findViewById(R.id.value)).setText(h[3]);

            TextView change = row.findViewById(R.id.change);
            change.setText(h[4]);
            change.setTextColor(ctx.getColor(h[6].equals("1") ? R.color.positive : R.color.negative));

            final Context c = ctx;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, h[1] + " (" + h[2] + ")", Toast.LENGTH_SHORT).show();
                }
            });

            list.addView(row);
            if (i < items.length - 1) {
                list.addView(divider(ctx));
            }
        }
    }

    /** Settings style rows, e.g. on the Profile screen. */
    public static void bindSettings(final Context ctx, LinearLayout list, String[] labels) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        list.removeAllViews();

        for (int i = 0; i < labels.length; i++) {
            final String label = labels[i];
            View row = inflater.inflate(R.layout.item_setting, list, false);

            TextView tv = row.findViewById(R.id.label);
            tv.setText(label);
            if (label.equals("Log out")) {
                tv.setTextColor(ctx.getColor(R.color.negative));
            }

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, label + " - coming soon", Toast.LENGTH_SHORT).show();
                }
            });

            list.addView(row);
            if (i < labels.length - 1) {
                list.addView(divider(ctx));
            }
        }
    }

    private static View divider(Context ctx) {
        float d = ctx.getResources().getDisplayMetrics().density;
        View divider = new View(ctx);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Math.max(1, (int) d));
        lp.setMarginStart((int) (16 * d));
        lp.setMarginEnd((int) (16 * d));
        divider.setLayoutParams(lp);
        divider.setBackgroundColor(ctx.getColor(R.color.divider));
        return divider;
    }
}
