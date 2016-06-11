package semantics;


import processing.core.PApplet;
import processing.core.PVector;

public class Letter {
	
	PApplet p;
	
	PVector pos;
	float theta;
	float target_theta;
	float lerp_val_theta;
	float lerp_inc_theta;
	
	char letter;
	char current_letter;
	int color;
	float size;
	
	int index;
	
	String current_word;
	
	boolean shuffle = false;
	boolean shown = true;
	boolean blown = false;
	
	Letter(){}
	
	Letter(char _letter, String _word, PVector _pos, int c, int _size, int _index, PApplet _p){
		p = _p;
		pos = _pos;
		letter = _letter;
		current_word = _word;
		current_letter = letter;
		color = c;
		size = _size;
		index = _index;
		theta = 0;
		target_theta = 0;
		lerp_val_theta = 0;
		lerp_inc_theta = 0.01f;
	}
	
	void update(){
		
		if(lerp_val_theta < 1)
			lerp_val_theta += lerp_inc_theta;
		
		theta = PApplet.lerp(theta, target_theta, lerp_val_theta);
		
		//---SHUFFLE
		if(shuffle && p.frameCount % 20 < 10)
			current_letter = pickRandomLetterCurrentWord();
		else if(!shuffle)
			current_letter = letter;
		
	}
	
	void display(){
//		if(p.noise(p.millis()*0.005f+index) < 0.6f)
//			p.fill(color);
//		else if(!shuffle)
			p.fill(color, 255);
		

		p.textSize(size);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.pushMatrix();
//		p.translate(pos.x, pos.y+p.tan(index+p.millis()*0.001f)*10);
//		p.translate(pos.x+p.cos(index+p.millis()*0.001f)*0.25f, pos.y);
		p.translate(pos.x, pos.y);
		if(blown)
			p.scale(-1);
		else
			p.scale(1);
		p.rotate(theta);

		p.text(current_letter, 0, 0);
		p.popMatrix();
	}
	
	char pickRandomLetter(){
		return Semantics.allLetters[(int)p.random(Semantics.allLetters.length)];
	}
	
	char pickRandomLetterCurrentWord(){
		return current_word.charAt((int)p.random(1, current_word.length()-1));
	}

}
