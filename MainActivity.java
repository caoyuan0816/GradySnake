/**
 * @author Yuan25
 * @email caoyuan0816@gmail.com
 */

package com.example.gradysnake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener{

	private static final int ID_BTN1 = 1;
    private static final int ID_BTN2 = 2;
    private static final int ID_BTN3 = 3;

    private static final int ID_GAMESURFACE = 100;
    
    private int gamesize_width;
    private int gamesize_height;
	
	private GameSurface gamesurface = null;
	private ScoreSurface scoresurface = null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//get the window manager and display size
		WindowManager wm = getWindowManager();
		Display d = wm.getDefaultDisplay();
				
		gamesize_width = d.getWidth()*2/3;
		gamesize_height = d.getHeight();
		
		//Debug
		//----------------------------------------------
		String str = ""+d.getWidth()+"  "+d.getHeight();
		Log.d("Event", str);
		
		str = ""+gamesize_width+"  "+gamesize_height;
		Log.d("Event", str);
		//----------------------------------------------
		
			
		RelativeLayout layout = new RelativeLayout(this);
		
		//Game surface
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp3.width = gamesize_width;
        lp3.height = gamesize_height;
        
        this.gamesurface = new GameSurface(this);
        gamesurface.setId(ID_GAMESURFACE);
        gamesurface.setLayoutParams(lp3);
		layout.addView(gamesurface);
		
		//View of control buttons
		View view = new View(this);
		
		RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp4.width = d.getWidth()/3-6;
        lp4.height = d.getWidth()/3-6;
        
        view.setLayoutParams(lp4);
        view.setBackgroundResource(R.drawable.control);
        
        view.setOnTouchListener(this);
        
        layout.addView(view);
        
		
		//Button Menu
		Button btn1 = new Button(this);
		btn1.setText("Menu");
		btn1.setId(ID_BTN1);
		
		RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp1.addRule(RelativeLayout.RIGHT_OF,ID_GAMESURFACE);
		
        //Register button listener
        btn1.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				GameInfo.pauseGame();
				showDialog(0);
			}
        	
        });
        
        btn1.setLayoutParams(lp1);
        layout.addView(btn1);
		
        //Button Pause
		Button btn2 = new Button(this);
		btn2.setText("Pause");
		btn2.setId(ID_BTN2);
		
		RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp2.addRule(RelativeLayout.RIGHT_OF,ID_BTN1);
        
        //Register button listener
        btn2.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameInfo.pauseGame();
			}
        	
        });
        
        btn2.setLayoutParams(lp2);
        layout.addView(btn2);
		
        
        //Button Resume
      	Button btn3 = new Button(this);
      	btn3.setText("Resume");
      	btn3.setId(ID_BTN3);
      		
      	RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      	lp5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp5.addRule(RelativeLayout.RIGHT_OF,ID_BTN2);
              
        //Register button listener
        btn3.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameInfo.resumeGame();
			}
        	
        });
        
        btn3.setLayoutParams(lp5);
        layout.addView(btn3);
        
        
        //Score surface
        this.scoresurface = new ScoreSurface(this);
        
        RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp6.addRule(RelativeLayout.BELOW,ID_BTN3);
        lp6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp6.width = d.getWidth()/3-6;
        lp6.height = d.getHeight()/3-28;
  
        //scoresurface.setBackgroundColor(Color.RED);
        scoresurface.setLayoutParams(lp6);
        layout.addView(scoresurface);
		
		setContentView(layout);
		
		//Read best score
		SharedPreferences sharedataa = getSharedPreferences("score", 0);
		String data = sharedataa.getString("best", null);
		
		if(data == null){
			Editor sharedata = getSharedPreferences("score", 0).edit();
			sharedata.putString("best","0");
			sharedata.commit();
			GameInfo.value_best_score = 0;
		}else{
			GameInfo.value_best_score = Integer.parseInt(data);
		}
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
		float x = arg1.getX();
		float y = arg1.getY();
		
		if(x>y && x<314-y){
			
			if(gamesurface.snake.getDir() == 2){
				//just can not control, do not game over
			}else{
				gamesurface.snake.changeDir(0);
				Log.d("DirControl", "UP");
			}
		}else if(x<y && x<314-y){
	
			if(gamesurface.snake.getDir() == 3){
				
			}else{
				gamesurface.snake.changeDir(1);
				Log.d("DirControl", "LEFT");
			}
		}else if(x<y && x>314-y){
					
			if(gamesurface.snake.getDir() == 0){
				
			}else{
				gamesurface.snake.changeDir(2);
				Log.d("DirControl", "DOWN");
			}
		}else if(x>y && x>314-y){
			
			if(gamesurface.snake.getDir() == 1){
				
			}else{
				gamesurface.snake.changeDir(3);
				Log.d("DirControl", "RIGHT");
			}	
		}

		return false;
	}
	
	//restart the game
	private void restart(){
		
		//terminate game progress and wait it stop
		GameInfo.threadRunning = false;
		gamesurface.snake.setSpeed(1);
		
		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	//Dialog menu
	protected Dialog onCreateDialog(int id){
		
    	switch(id){
    	// 0 -> Game Menu
    	case 0:
    		return new AlertDialog.Builder(this)
    		.setIcon(R.drawable.doge)
    		.setTitle("Grady Snake ^___^")
    		.setNegativeButton("About Me", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					Toast.makeText(getBaseContext(), "CaoYuan 2012303298\nEmail:caoyuan0816@gmail.com", Toast.LENGTH_LONG).show();
					
				}
			})
			.setNeutralButton("Thanks", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					Toast.makeText(getBaseContext(), "Thank my girlfriend Eshan", Toast.LENGTH_LONG).show();
				}
			})
			.setPositiveButton("Restart Game", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					restart();
				}
			})
    		.show();
    	
    	}
    	return null;
    }
	
	public void onPause(){
		super.onPause();
		
		Editor sharedata = getSharedPreferences("score", 0).edit();
		sharedata.putString("best",""+GameInfo.value_best_score);
		sharedata.commit();
	}
   
}
