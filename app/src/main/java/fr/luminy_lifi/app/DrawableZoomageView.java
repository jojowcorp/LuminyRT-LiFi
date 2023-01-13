package fr.luminy_lifi.app;
//fr.luminy_lifi.app.DrawableZoomageView


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.luminy_lifi.app.zoom.ZoomageView;

public class DrawableZoomageView extends ZoomageView {

    private static Paint line, circleIn, circleOut, circleOuter, pointer;
    public static float scaleX = 1.85F, scaleY = 1.85F;
    static {
        line = new Paint();
        line.setStrokeWidth(6F);
        line.setColor(Color.rgb(238,134,43));
        line.setStrokeCap(Paint.Cap.ROUND);

        circleOut = new Paint();
        circleOut.setStrokeWidth(6F);
        circleOut.setColor(Color.rgb(255,255,255));
        circleOut.setStyle(Paint.Style.FILL);
        circleOut.setStrokeCap(Paint.Cap.ROUND);

        circleIn = new Paint();
        circleIn.setStrokeWidth(6F);
        circleIn.setColor(Color.rgb(238,134,43));
        circleIn.setStrokeCap(Paint.Cap.ROUND);
        circleIn.setStyle(Paint.Style.FILL);

        circleOuter = new Paint();
        circleOuter.setStrokeWidth(6F);
        circleOuter.setColor(Color.rgb(0,0,0));
        circleOuter.setStrokeCap(Paint.Cap.ROUND);
        circleOuter.setStyle(Paint.Style.FILL);

        pointer = new Paint();
        pointer.setStrokeWidth(6F);
        pointer.setColor(Color.rgb(231,76,60));
        pointer.setStrokeCap(Paint.Cap.ROUND);
        pointer.setStyle(Paint.Style.FILL);


    }

    private Matrix DrawableMatrix;

    public DrawableZoomageView(Context context) {
        super(context);
    }

    public DrawableZoomageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableZoomageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageMatrix(Matrix m) {
        super.setImageMatrix(m);
        DrawableMatrix = m;
    }

    private Matrix imageMatrix;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Save the current canvas matrix
        canvas.save();
        // Apply the image matrix to the canvas
        matrix.set(getImageMatrix());
        matrix.getValues(matrixValues);
       // canvas.concat(this.DrawableMatrix);
        canvas.concat(matrix);

        if(MainActivity.instance.data != null) {
            if (!MainActivity.instance.data.getPoints().isEmpty()) {
                for (dataManager.Point p : MainActivity.instance.data.getPoints()) {
                    if (p instanceof dataManager.LocationPoint) {
                        canvas.drawCircle(p.X * scaleX, p.Y * scaleY, 27, circleOuter);
                        canvas.drawCircle(p.X * scaleX, p.Y * scaleY, 25, circleOut);
                        canvas.drawCircle(p.X * scaleX, p.Y * scaleY, 15, circleIn);
                    } else {
                        canvas.drawCircle(p.X * scaleX, p.Y * scaleY, 10, circleIn);
                    }
                    if (p.connectedPoint != null && p.connectedPoint.length > 0) {
                        for (int id : p.connectedPoint) {
                            dataManager.Point co = MainActivity.instance.data.getPointById(id);
                            if (co != null) {
                                canvas.drawLine(co.X * scaleX, co.Y * scaleY, p.X * scaleX, p.Y * scaleY, line);
                            } else {

                            }
                        }
                    }
                }
                if (MainActivity.instance.UserAtId != 0) {
                    dataManager.Point co = MainActivity.instance.data.getPointById(MainActivity.instance.UserAtId);
                    if (co != null) {
                        for(int i = -16; i <= 16; i++) {
                            canvas.drawLine(co.X * scaleX, co.Y * scaleY, (co.X + i)* scaleX, (co.Y - 45)* scaleY, pointer);
                        }
                        canvas.drawCircle(co.X * scaleX,(co.Y - 45)* scaleY, 30, pointer);
                        canvas.drawCircle(co.X * scaleX,(co.Y - 45)* scaleY, 12, circleOut);

                    }
                }
            }
        }

        canvas.restore();
    }
}
