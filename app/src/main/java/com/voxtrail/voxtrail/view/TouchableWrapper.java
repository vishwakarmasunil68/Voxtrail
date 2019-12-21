package com.voxtrail.voxtrail.view;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.voxtrail.voxtrail.fragment.VehicleStatusReportFragment;

public class TouchableWrapper extends FrameLayout {

    private long lastTouched = 0;
    private static final long SCROLL_TIME = 200L; // 200 Milliseconds, but you can adjust that to your liking
    private UpdateMapAfterUserInterection updateMapAfterUserInterection;

    public TouchableWrapper(Fragment fragment) {
        super(fragment.getContext());
        // Force the host activity to implement the UpdateMapAfterUserInterection Interface
        try {
            updateMapAfterUserInterection = (VehicleStatusReportFragment) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.getActivity().toString() + " must implement UpdateMapAfterUserInterection");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouched = SystemClock.uptimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                final long now = SystemClock.uptimeMillis();
                if (now - lastTouched > SCROLL_TIME) {
                    // Update the map
                    updateMapAfterUserInterection.onUpdateMapAfterUserInterection();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    // Map Activity must implement this interface
    public interface UpdateMapAfterUserInterection {
        public void onUpdateMapAfterUserInterection();
    }
}