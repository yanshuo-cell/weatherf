package com.taimin.weatherforecast.utils;


import java.util.Comparator;


public class BKeyComparator implements Comparator<String> {


	@Override
	public int compare(String o1, String o2) {
		if(o1.equals("@") || o2.equals("#")) {
			return -1;
		} else if(o1.equals("#") || o2.equals("@")) {
			return 1;
		} else {
			return o1.compareTo(o2);
		}
	}
}
