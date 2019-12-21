package com.achoudhury.flightmanagement;

public class printpattern {
	public static void main(String[] args){
		int i,j;
		for(i=1;i<=5;i++) {
			for(j=1;j<i;j++) {
				System.out.print("  ");
			}
			for(j=6-i;j>=1;j--) {
				System.out.print(j + " ");
			}
			System.out.println(" ");
		}
	}
}
