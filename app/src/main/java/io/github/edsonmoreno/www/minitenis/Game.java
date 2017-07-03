package io.github.edsonmoreno.www.minitenis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Edson D.  on 02/07/2017.
 */

public class Game extends SurfaceView implements Runnable {
    public Game (Context context){
        super(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display pantalla = wm.getDefaultDisplay();
        Point point = new Point();
        pantalla.getSize(point);

        holder = getHolder();
        paint = new Paint();
        paint.setColor(Color.BLACK);

        ancho = point.x;
        alto = point.y;

        raqueta = new Raqueta(this);
        pelota = new Pelota(this);

        puntos = 0;
        jugando=true;
        pintor = new Thread(this);
        pintor.start();
    }

    public void Actualizar(){
        raqueta.Actualizar();
        pelota.Actualizar();
    }

    public void Pintar(){
        String score=getResources().getString(R.string.puntajes);
        paint.setTextSize(30);
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawRect(raqueta.getRaqueta(), paint);
            canvas.drawOval(pelota.getPelota(), paint);
            canvas.drawText(score+" : "+puntos,2,70,paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public int getAncho(){  return ancho;   }
    public int getAlto(){   return alto;    }
    public boolean getJugando(){    return jugando;  }

    public void setJugando(boolean jugando){ this.jugando = jugando;  }

    @Override
    public void run() {
        while(jugando){
            Actualizar();
            Pintar();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        raqueta.Mover(event);
        return super.onTouchEvent(event);
    }

    private int ancho, alto, puntos;
    private boolean jugando;
    private Thread pintor;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    Raqueta raqueta;
    Pelota pelota;
}
