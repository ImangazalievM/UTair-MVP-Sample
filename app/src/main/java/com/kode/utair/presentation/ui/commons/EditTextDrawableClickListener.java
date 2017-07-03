package com.kode.utair.presentation.ui.commons;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public abstract class EditTextDrawableClickListener implements View.OnTouchListener {

    public final static int DRAWABLE_LEFT = 0;
    public final static int DRAWABLE_TOP = 1;
    public final static int DRAWABLE_RIGHT = 2;
    public final static int DRAWABLE_BOTTOM = 3;

    @Retention(SOURCE)
    @IntDef({DRAWABLE_LEFT, DRAWABLE_TOP, DRAWABLE_RIGHT, DRAWABLE_BOTTOM})
    public @interface DrawablePosition {
    }

    private int drawablePosition;

    public EditTextDrawableClickListener(@DrawablePosition int drawablePosition) {
        this.drawablePosition = drawablePosition;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        EditText editText = (EditText) v;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable targetDrawable = editText.getCompoundDrawables()[drawablePosition];
            if (targetDrawable != null) {
                if (event.getRawX() >= (editText.getRight() - targetDrawable.getBounds().width())) {
                    onDrawableClick();
                    return true;
                }
            }

        }
        return false;
    }

    public abstract void onDrawableClick();

}
