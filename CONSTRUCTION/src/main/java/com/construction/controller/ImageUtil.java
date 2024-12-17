package com.construction.controller;
import java.util.Base64;
public class ImageUtil {

	
	 // Encodes byte array into Base64 string
    public static String encodeImage(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
