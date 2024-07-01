package me.jumper251.replay.utils;

import java.util.List;

import java.util.Random;


public class MathUtils {

	public static int randInt(int min, int max) {
	    Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
	}
	
	public static double round(double number, double amount){				
		return Math.round(number * amount)/amount;
		
	}
	
	public static double getAverageDouble(List<Double> list){
		if(list == null || list.isEmpty()) return -1;
		double avg = list.stream().mapToDouble(val -> val).average().orElse(-1);
		return round(avg, 100);
	}
	
	public static int getAverageInt(List<Integer> list){
		if(list == null || list.isEmpty()) return -1;
        return list.stream().mapToInt(val -> val).sum() / list.size();
	}
	
	  public static boolean isInt(String string)  {
	    try
	    {
	      Integer.parseInt(string);
	      return true; } catch (Exception ex) {
	    }
	    return false;
	  }

	  public static boolean isDouble(String string) {
	    try
	    {
	      Double.parseDouble(string);
	      return true; } catch (Exception ex) {
	    }
	    return false;
	  }
	  
}
