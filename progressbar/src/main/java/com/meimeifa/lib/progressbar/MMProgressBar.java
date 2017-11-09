package com.meimeifa.lib.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * ProgressBar
 *
 * Created by Donglua on 17/11/9.
 */

public class MMProgressBar extends View {

  private float progress = 0.5f;
  private Paint mPaint;
  private RectF mContentRect = new RectF();
  private int minSize = 10;
  private float rectRadius;
  private float borderWidth = 2;
  private float borderPadding = 2f;

  public MMProgressBar(Context context) {
    this(context, null, 0);
  }

  public MMProgressBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MMProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MMProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr);
  }

  protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

    TypedArray a = getContext().getTheme().obtainStyledAttributes(
        attrs, R.styleable.MMProgressBar, defStyleAttr, defStyleAttr);
    try {

      this.rectRadius = a.getDimension(R.styleable.MMProgressBar_rectRadius, 20);
      this.borderWidth = a.getDimension(R.styleable.MMProgressBar_borderWidth, 4);
      this.borderPadding = a.getDimension(R.styleable.MMProgressBar_borderPadding, 3);

    } finally {
      a.recycle();
    }

    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.WHITE);
    mPaint.setStrokeWidth(borderWidth);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mContentRect.set(getPaddingLeft(),
                     getPaddingTop(),
                    getWidth() - getPaddingRight(),
                    getHeight() - getPaddingBottom());
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(
        Math.max(getSuggestedMinimumWidth(),
            resolveSize(minSize + getPaddingLeft() + getPaddingRight(),
                widthMeasureSpec)),
        Math.max(getSuggestedMinimumHeight(),
            resolveSize(minSize + getPaddingTop() + getPaddingBottom(),
                heightMeasureSpec)));
  }

  private void drawProgress(Canvas canvas) {
    float width = mContentRect.right - mContentRect.left - (borderWidth + borderPadding) * 2;
    //float height = mContentRect.bottom - mContentRect.top - (borderWidth + borderPadding) * 2;

    RectF rectF = new RectF(mContentRect.left + borderWidth + borderPadding,
                            mContentRect.top + borderWidth + borderPadding,
                            mContentRect.left + borderWidth + borderPadding + width * progress,
                            mContentRect.bottom - borderWidth - borderPadding);
    canvas.drawRoundRect(rectF, rectRadius, rectRadius, mPaint);
  }

  private void drawBorder(Canvas canvas) {
    canvas.drawRoundRect(mContentRect,
        rectRadius, rectRadius, mPaint);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mPaint.setStyle(Paint.Style.STROKE);
    drawBorder(canvas);

    mPaint.setStyle(Paint.Style.FILL);
    drawProgress(canvas);
  }

  public void setProgress(float progress) {
    this.progress = progress;
    invalidate();
  }

}

