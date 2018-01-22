/**
 * @author Yuan25
 * @email caoyuan0816@gmail.com
 */

package com.example.gradysnake;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

//class snake
//use linear linked-list to implement
public class Snake {

	//class node
	//which is the every node of whole snake
	//also the node of linear linked-list
	public class Node{
		
		//node position
		public int pos_x,pos_y;
		//next node
		public Node next;
		
		//construct method
		//x , y is the position of node
		public Node(int x,int y){
			this.pos_x = x;
			this.pos_y = y;
			this.next = null;
		}
	}
	
	//the head node of snake
	private Node head = null;
	
	//the food which snake like to eat
	private Node food = null;
	
	//the time which produce next food
	public int nextfood;
	
	//the speed of snake
	private int speed ;
	
	//the current direction of snake
	//		integer range 0-3
	//						0----->up
	//						1----->left
	//						2----->down
	//						3----->right
	public int dir;
	
	//the construct method of class snake
	public Snake(int speed, int dir, int nextfood){
		
		head = new Node(400,200);
		this.speed = speed;
		this.dir = dir;
		this.nextfood = nextfood;
	}
	
	//method which add a new node to snake
	//x,y is the position of the new node
	public void addNode(){
		
		if(head == null){
			Log.d("Exception","head is null");
		}else{
			Node tmp = head;
			while(tmp.next!=null){
				tmp = tmp.next;
			}
			tmp.next = new Node(tmp.pos_x,tmp.pos_y);
		}
	}
	
	//move one step
	//every node move one step
	public void move(){
		
		//recursion moving nodes except head node
		move(head);
		//judge direction and move head node
		switch (dir) {
		case 0:
			head.pos_y -= 20;
			break;
		case 1:
			head.pos_x -= 20;
			break;
		case 2:
			head.pos_y += 20;
			break;
		case 3:
			head.pos_x += 20;
			break;
		default:
			Log.d("Exception","direction wrong");
			break;
		}
		
	}
	
	//private method to implement move step
	//use recursion way
	private void move(Node n){
		if(n.next == null){
			return ;
		}else{
			move(n.next);
			n.next.pos_x = n.pos_x;
			n.next.pos_y = n.pos_y;
			return ;
		}
	}
	
	//judge the snake is or not survive
	public boolean isSurvive(){
		
		if(head.pos_x<0 || head.pos_x>640 || head.pos_y<0 || head.pos_y>540){
			return false;
		}	

		return true;
	}
	
	//get positions
	//return a integer array-list which contains all positions of snake node
	public ArrayList<Integer> getPositions(){
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		Node tmp = head;
		while(tmp!=null){
			arr.add(tmp.pos_x);
			arr.add(tmp.pos_y);
			tmp = tmp.next;
		}
		
		return arr;

	}
	
	//method to change the move direction of snake
	public void changeDir(int d){
		dir = d;
	}
	
	//snake try to eat something
	public boolean tryEat(){
		
		if(food != null && food.pos_x == head.pos_x && food.pos_y == head.pos_y){
			//the snake become longer
			addNode();
			//food has been ate
			food = null;
			//calculate next food time
			nextfood =  new Random().nextInt(10000) + 1;
			
			//Debug
			//-------------------------
			String str = ""+nextfood;
			Log.d("Food", str);
			//-------------------------
			return true;
		}
		return false;
	}
	
	//produce next food
	public void produceFood(){
		
		int x,y;
		do{
			x = new Random().nextInt(32);
			y = new Random().nextInt(27);
			
			x *= 20;
			y *= 20;
		}while(checkFood(x, y) == false);
		
		food = new Node(x,y);
	}
	
	//check the food position is or not legal
	private boolean checkFood(int x, int y){
		
		Node tmp = head;
		while(tmp != null){
			if(tmp.pos_x == x && tmp.pos_y == y){
				return false;
			}
			tmp = tmp.next;
		}
		return true;
	}
	
	//get food position
	public ArrayList<Integer> getFoodPosition(){
		
		ArrayList<Integer> arr = new ArrayList();
		arr.add(food.pos_x);
		arr.add(food.pos_y);
		
		return arr;
	}
	
	//method to get speed of snake
	public int getSpeed(){
		
		return this.speed;
	}
	
	//check dose the grid has food
	public boolean hasFood(){
		
		return food!=null;
	}
	
	//get direction
	public int getDir(){
		return dir;
	}
	
	//set speed
	public void setSpeed(int s){
		speed = s;
	}
	
}

