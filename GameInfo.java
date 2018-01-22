/**
 * @author Yuan25
 * @email caoyuan0816@gmail.com
 */

package com.example.gradysnake;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;

public class GameInfo extends SurfaceView{
	
	//the score of game
	public static int value_score = 0;		

	//the best score of game
	public static int value_best_score = 0;
	
	//control game thread running
	//if game is over this will be false
	public static boolean threadRunning = true;
	
	//control pause and resume
	//game paused -> false
	//game running -> true
	public static boolean gameRunning = true;
	
	//construct method
	public GameInfo(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		value_score = 0;
		value_best_score = 0;
		threadRunning = true;
		gameRunning = true;
	}
	
	//method to pause game
	public static void pauseGame(){
		gameRunning = false;
		Log.d("GameInfo","Game paused");
	}
	
	//method to resume game
	public static void resumeGame(){
		gameRunning = true;
		Log.d("GameInfo","Game resumed");
	}
	
	//method to over game
	public static void gameOver(){
		if(value_score > value_best_score){
			value_best_score = value_score;
		}
		threadRunning = false;
		Log.d("GameInfo", "GameOver");	
	}
	
}
