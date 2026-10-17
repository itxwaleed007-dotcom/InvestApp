package com.waleed.investapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {

    private static final int ACCENT = Color.parseColor("#5B4CF0");

    // Design space is 353 x 140
    private static final float[][] POINTS = {
            {0, 104}, {48, 96}, {96, 106}, {144, 82},
            {192, 88}, {240, 60}, {288, 66}, {341, 28}
    };

    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path linePath = new Path();
    private final Path fillPath = new Path();

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        float d = getResources().getDisplayMetrics().density;

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(ACCENT);
        linePaint.setStrokeWidth(2.6f * d);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        fillPaint.setStyle(Paint.Style.FILL);

        haloPaint.setColor(ACCENT);
        haloPaint.setAlpha(41);

        dotPaint.setColor(ACCENT);

        ringPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();
        float d = getResources().getDisplayMetrics().density;
        float sx = w / 353f;
        float sy = h / 140f;

        linePath.reset();
        linePath.moveTo(POINTS[0][0] * sx, POINTS[0][1] * sy);
        for (int i = 1; i < POINTS.length; i++) {
            float x0 = POINTS[i - 1][0] * sx;
            float y0 = POINTS[i - 1][1] * sy;
            float x1 = POINTS[i][0] * sx;
            float y1 = POINTS[i][1] * sy;
            float mid = (x1 - x0) / 2f;
            linePath.cubicTo(x0 + mid, y0, x1 - mid, y1, x1, y1);
        }

        float lastX = POINTS[POINTS.length - 1][0] * sx;
        float lastY = POINTS[POINTS.length - 1][1] * sy;

        fillPath.set(linePath);
        fillPath.lineTo(lastX, h);
        fillPath.lineTo(0, h);
        fillPath.close();

        fillPaint.setShader(new LinearGradient(0, 0, 0, h,
                Color.argb(56, 91, 76, 240), Color.argb(0, 91, 76, 240),
                Shader.TileMode.CLAMP));

        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(linePath, linePaint);

        canvas.drawCircle(lastX, lastY, 10 * d, haloPaint);
        canvas.drawCircle(lastX, lastY, 8 * d, ringPaint);
        canvas.drawCircle(lastX, lastY, 5.5f * d, dotPaint);
    }
}
