package com.cloudstone.rcemessage.filter;

public class ImageRequest {
	private int width;
	private int height;
	private int numOfCharacters;
	public ImageRequest(int width, int height, int numOfCharacters) {
		this.width = width;
		this.height = height;
		this.numOfCharacters = numOfCharacters;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getNumOfCharacters() {
		return numOfCharacters;
	}
}
