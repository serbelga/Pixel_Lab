package com.example.sergiobelda.photoeditor.editableimageview;

import android.util.Log;
import android.view.MotionEvent;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Polygon;
import com.example.sergiobelda.photoeditor.editableimageview.figures.Square;

public class StrategyRotation extends StrategyTool {
    @Override
    void onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Polygon p = imageView.getTouchedPolygon(event.getX(), event.getY());
                if (!imageView.scaleDetector.isInProgress()) {
                    if (p != null && p instanceof Square) {
                        Square s = (Square) p;
                        double delta_x = (s.getX() - event.getX());
                        double delta_y = (s.getY() - event.getY());
                        double radians = Math.atan2(delta_y, delta_x);
                        Log.d("Rotation", delta_x+" ## "+delta_y+" ## "+radians+" ## "
                                +Math.toDegrees(radians));
                        s.setRotation((float) Math.toDegrees(radians));
                        /*
                        if (event.getPointerCount() == 2) {
                            double delta_x = (event.getX(0) - event.getX(1));
                            double delta_y = (event.getY(0) - event.getY(1));
                            double radians = Math.atan2(delta_y, delta_x);
                            Log.d("Rotation", delta_x+" ## "+delta_y+" ## "+radians+" ## "
                                    +Math.toDegrees(radians));
                            s.setRotation((float) Math.toDegrees(radians));
                        }*/
                    }
                }
                break;
            }
        }
        invalidate();
    }
}
