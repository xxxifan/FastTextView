package com.wechat.testdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsjwzh.widget.text.ReadMoreTextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReadMoreFragment extends Fragment {

  private View mRootView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    if (mRootView != null) {
      return mRootView;
    }
    mRootView = inflater.inflate(R.layout.read_more_demo, container, false);
    SpannableStringBuilder spannableStringBuilder = getSpannable();
    ReadMoreTextView readMoreTextView = (ReadMoreTextView) mRootView.findViewById(R.id.readmore_tv);
    readMoreTextView.setText(spannableStringBuilder);
    readMoreTextView.setCustomEllipsisSpan(new ReadMoreTextView.EllipsisSpan("  Read More") {
      @Override
      public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int oldColor = paint.getColor();
        paint.setColor(Color.RED);
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        paint.setColor(oldColor);
      }
    });
    readMoreTextView.setCustomCollapseSpan(new ReadMoreTextView.EllipsisSpan("  Collapse") {
      @Override
      public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int oldColor = paint.getColor();
        paint.setColor(Color.RED);
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        paint.setColor(oldColor);
      }
    });
    return mRootView;
  }

  @NonNull
  private SpannableStringBuilder getSpannable() {
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getResources().getString(R.string.content_cn));
    return spannableStringBuilder;
  }
}
