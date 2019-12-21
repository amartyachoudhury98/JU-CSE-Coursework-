package com.achoudhury;

public class Item {
	public String description;
	public String imageUrl;
	public int price;
	public Boolean isDiscounted;
	public int discount_perc;
	Item(String desc,String url,int price,Boolean isDiscounted,int discount_perc){
		this.description=desc;
		this.imageUrl=url;
		this.price = price;
		this.isDiscounted = isDiscounted;
		this.discount_perc=discount_perc;
	}
}
