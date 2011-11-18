package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class level {

	// enemy array key
	// 1. Chasers
	// 2. 
	Image background;
	Mover[] NMEs;
	
	public level(File goruu, int[] p){
		try {
			background = ImageIO.read(goruu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("oh shitake mushrooms image failed to load");
		}
	}
	public void draw(){
		
	}
	
}
