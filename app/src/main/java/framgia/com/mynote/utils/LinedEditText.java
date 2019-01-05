package framgia.com.mynote.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class LinedEditText extends android.support.v7.widget.AppCompatEditText {
    private static final float VERTICAL_OFFSET_SCALING_FACTOR = 0.15f;
    private static final float DASHED_LINE_ON_SCALE_FACTOR = 0.01f;
    private static final float DASHED_LINE_OFF_SCALE_FACTOR = 0.0125f;
    private static final int VALUE_ALPHA = 250;
    private static final int VALUE_RED = 0;
    private static final int VALUE_GREEN = 0;
    private static final int VALUE_BLUE = 0;
    private static final int DEFAULT_LINE = 0;
    private static final int PHASE = 0;
    private Paint mPaint;
    private Rect mRect;

    public LinedEditText(Context context) {
        super(context);
        init();
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setARGB(VALUE_ALPHA, VALUE_RED, VALUE_GREEN, VALUE_BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setPathEffect(
                new DashPathEffect(
                        new float[]{
                                getWidth() * DASHED_LINE_ON_SCALE_FACTOR,
                                getWidth() * DASHED_LINE_OFF_SCALE_FACTOR},
                        PHASE));
        int height = getHeight();
        int lineHeight = getLineHeight();
        int verticalOffset = (int) (lineHeight * VERTICAL_OFFSET_SCALING_FACTOR);
        int numberOfLines = height / lineHeight;
        if (getLineCount() > numberOfLines) {
            numberOfLines = getLineCount();
        }
        int baseline = getLineBounds(DEFAULT_LINE, mRect);
        for (int i = 0; i < numberOfLines; i++) {
            canvas.drawLine(
                    mRect.left,
                    baseline + verticalOffset,
                    mRect.right,
                    baseline + verticalOffset,
                    mPaint);

            baseline += lineHeight;
        }
        super.onDraw(canvas);
    }
}
