/**
 * @author Yuan25
 * @email caoyuan0816@gmail.com
 */

package com.example.gradysnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ScoreSurface extends GameInfo
			implements SurfaceHolder.Callback,Runnable {

	//defines
	private SurfaceHolder surfaceHolder = null;
	
	public ScoreSurface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.surfaceHolder = this.getHolder();
		
		//add call back
		surfaceHolder.addCallback(this);
		this.setFocusable(true);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(threadRunning){
			try{
				Thread.sleep(200);
			}catch(Exception e){
				
			}
			
			if(gameRunning){
				synchronized( surfaceHolder ){
					Draw();
				}
			}	
		}
	}

	private void Draw() {
		// TODO Auto-generated method stub
		
		Canvas canvas = surfaceHolder.lockCanvas();
		
		if(surfaceHolder==null || canvas==null){
			return;
		}
		
		Paint p = new Paint();
		p.setAntiAlias(true);
		
		//draw background
		p.setColor(Color.BLACK);
		canvas.drawRect(0, 0,this.getRight(),this.getBottom(),p);
		
		//draw score texts and game information
		p.setTextSize(35);
		p.setColor(Color.WHITE);
		
		String score =      "Score:                "+value_score;
		String best_score = "Best Score:       "+value_best_score;
		
		String game_info;
		if(GameInfo.gameRunning){
			game_info =  "    Game Running";
		}else{
			game_info =  "    Game Paused";
		}
		if(!GameInfo.threadRunning){
			game_info =  "       Game Over"; 
			p.setColor(Color.RED);
		}
		
		canvas.drawText(score, 0, 30, p);
		canvas.drawText(best_score, 0, 70, p);
		canvas.drawText(game_info, 0, 110, p);
		
		surfaceHolder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
		//start thread
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

}
