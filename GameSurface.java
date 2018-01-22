/**
 * @author Yuan25
 * @email caoyuan0816@gmail.com
 */

package com.example.gradysnake;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends GameInfo
							implements SurfaceHolder.Callback,Runnable{
	
	
	//define the surface holder
	private SurfaceHolder surfaceHolder = null;
	
	//define snake
	public Snake snake = null;
	
	//Construct function of class Game Surface
	public GameSurface(Context context){
		super(context);
		
		//get surface holder
		surfaceHolder = this.getHolder();
		
		//get snake
		snake = new Snake(200,0,0);
		
		//add call back
		surfaceHolder.addCallback(this);
		this.setFocusable(true);

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(threadRunning){
			try{
				Thread.sleep(snake.getSpeed());
			}catch(Exception e){
				
			}
			
			if(gameRunning){
				
				//reduce the next food coming time
				if(snake.nextfood > 0){
					snake.nextfood -= snake.getSpeed();
				}
				//produce next food
				if(snake.nextfood <= 0 && !snake.hasFood()){
					snake.produceFood();
				}
				//eat and move
				if(snake.tryEat()){
					value_score ++;
					Log.d("Score",""+value_score);
				}
				snake.move();
				
				//judge continue or game over
				if(snake.isSurvive()){
					synchronized( surfaceHolder ){
						Draw();
					}
				}else{
					GameInfo.gameOver();
				}	
			}
		}
	}
	
	//Method draw
	//draw grid, snake and foods
	public void Draw(){
		Canvas canvas = surfaceHolder.lockCanvas();
		
		if(surfaceHolder==null || canvas==null){
			return;
		}
		
		Paint p = new Paint();
		p.setAntiAlias(true);
		
		//draw background
		p.setColor(Color.BLACK);
		canvas.drawRect(0, 0,this.getRight(),this.getBottom(),p);
		
		p.setColor(Color.GRAY);
		
		//draw the game grid
		for(int i=0;i<=this.getBottom();i+=20){
			canvas.drawLine(0, i, 640, i, p);
		}
		for(int i=0;i<=this.getRight();i+=20){
			canvas.drawLine(i, 0, i, 540, p);
		}
		
		//draw snake
		ArrayList<Integer> arr = snake.getPositions();
		
		p.setColor(Color.RED);
		canvas.drawCircle(arr.get(0)+10, arr.get(1)+10, 10, p);
		
		if(snake.dir == 0){
			canvas.drawRect(arr.get(0), arr.get(1)+10, arr.get(0)+20, arr.get(1)+20, p);
		}else if(snake.dir == 1){
			canvas.drawRect(arr.get(0)+10, arr.get(1), arr.get(0)+20, arr.get(1)+20, p);
		}else if(snake.dir == 2){
			canvas.drawRect(arr.get(0), arr.get(1), arr.get(0)+20, arr.get(1)+10, p);
		}else if(snake.dir == 3){
			canvas.drawRect(arr.get(0), arr.get(1), arr.get(0)+10, arr.get(1)+20, p);
		}
		
		for(int i=2;i<arr.size();i+=2){
			canvas.drawRect(arr.get(i), arr.get(i+1), arr.get(i)+20, arr.get(i+1)+20, p);
		}
		
		//draw food
		if(snake.hasFood()){
			ArrayList<Integer> arr2 = snake.getFoodPosition();
			p.setColor(Color.LTGRAY);
			canvas.drawCircle(arr2.get(0)+10, arr2.get(1)+10, 10, p);
		}
		
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