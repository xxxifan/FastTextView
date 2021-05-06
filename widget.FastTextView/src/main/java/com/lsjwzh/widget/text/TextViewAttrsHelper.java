package com.lsjwzh.widget.text;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import static androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL;

public class TextViewAttrsHelper {
  public int mSpacingAdd;
  public float mSpacingMultiplier = 1f;
  public int mMaxWidth = Integer.MAX_VALUE;
  public int mMaxLines = Integer.MAX_VALUE;
  public int mEllipsize = -1;
  public ColorStateList mTextColor = ColorStateList.valueOf(Color.BLACK);
  public int mTextSize = 15;
  public CharSequence mText;
  private int mGravity;

  public void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    final Resources.Theme theme = context.getTheme();
    TypedArray a = theme.obtainStyledAttributes(attrs,
        R.styleable.FastTextView, defStyleAttr, defStyleRes);
    int n = a.getIndexCount();
    for (int i = 0; i < n; i++) {
      int attr = a.getIndex(i);
      if (attr == R.styleable.FastTextView_android_gravity) {
        mGravity = a.getInt(attr, Gravity.TOP | Gravity.LEFT);
      } else if (attr == R.styleable.FastTextView_android_text) {
        mText = a.getText(attr);
      } else if (attr == R.styleable.FastTextView_android_ellipsize) {
        mEllipsize = a.getInt(attr, mEllipsize);
      } else if (attr == R.styleable.FastTextView_android_maxLines) {
        mMaxLines = a.getInt(attr, Integer.MAX_VALUE);
      } else if (attr == R.styleable.FastTextView_android_textColor) {// Do not support ColorState
        mTextColor = a.getColorStateList(attr);
      } else if (attr == R.styleable.FastTextView_android_textSize) {
        mTextSize = a.getDimensionPixelSize(attr, 15);
      } else if (attr == R.styleable.FastTextView_android_lineSpacingExtra) {
        mSpacingAdd = a.getDimensionPixelSize(attr, mSpacingAdd);
      } else if (attr == R.styleable.FastTextView_android_lineSpacingMultiplier) {
        mSpacingMultiplier = a.getFloat(attr, mSpacingMultiplier);
      }
    }
  }

  /**
   * Sets the horizontal alignment of the text and the
   * vertical gravity that will be used when there is extra space
   * in the TextView beyond what is required for the text itself.
   *
   * @see android.view.Gravity
   */
  public boolean setGravity(int gravity) {
    if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
      gravity |= Gravity.START;
    }
    if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
      gravity |= Gravity.TOP;
    }

    boolean newLayout = false;

    if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) !=
        (mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK)) {
      newLayout = true;
    }

    if (gravity != mGravity) {
      newLayout = true;
    }

    mGravity = gravity;
    return newLayout;
  }

  /**
   * Returns the horizontal and vertical alignment of this TextView.
   *
   * @see android.view.Gravity
   */
  public int getGravity() {
    return mGravity;
  }

  public static Layout.Alignment getLayoutAlignment(View view, int gravity) {
    Layout.Alignment alignment;
    switch (view.getTextAlignment()) {
      case android.view.View.TEXT_ALIGNMENT_GRAVITY:
        alignment = getAlignmentByGravity(gravity);
        break;
      case android.view.View.TEXT_ALIGNMENT_TEXT_START:
        alignment = Layout.Alignment.ALIGN_NORMAL;
        break;
      case android.view.View.TEXT_ALIGNMENT_TEXT_END:
        alignment = Layout.Alignment.ALIGN_OPPOSITE;
        break;
      case android.view.View.TEXT_ALIGNMENT_CENTER:
        alignment = Layout.Alignment.ALIGN_CENTER;
        break;
      case android.view.View.TEXT_ALIGNMENT_VIEW_START:
        alignment = (ViewCompat.getLayoutDirection(view) == LAYOUT_DIRECTION_RTL) ?
            Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
        break;
      case android.view.View.TEXT_ALIGNMENT_VIEW_END:
        alignment = (ViewCompat.getLayoutDirection(view) == LAYOUT_DIRECTION_RTL) ?
            Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
        break;
      case android.view.View.TEXT_ALIGNMENT_INHERIT:
        // This should never happen as we have already resolved the text alignment
        // but better safe than sorry so we just fall through
      default:
        alignment = Layout.Alignment.ALIGN_NORMAL;
        break;
    }
    return alignment;
  }

  private static Layout.Alignment getAlignmentByGravity(int gravity) {
    switch (gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
      case Gravity.START:
        return Layout.Alignment.ALIGN_NORMAL;
      case Gravity.END:
        return Layout.Alignment.ALIGN_OPPOSITE;
      case Gravity.LEFT:
        return Layout.Alignment.ALIGN_LEFT;
      case Gravity.RIGHT:
        return Layout.Alignment.ALIGN_RIGHT;
      case Gravity.CENTER_HORIZONTAL:
        return Layout.Alignment.ALIGN_CENTER;
      default:
        return Layout.Alignment.ALIGN_NORMAL;
    }
  }
}
