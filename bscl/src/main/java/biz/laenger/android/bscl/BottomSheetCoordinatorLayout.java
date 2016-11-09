package biz.laenger.android.bscl;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

public class BottomSheetCoordinatorLayout extends CoordinatorLayout {

    public BottomSheetCoordinatorLayout(Context context) {
        this(context, null);
    }

    public BottomSheetCoordinatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSheetCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
