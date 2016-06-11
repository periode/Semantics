package semantics;

import processing.core.PApplet;
import processing.core.PVector;

public class Word {
	
	PApplet p;
	PVector start_pos;
	
	
	String word;
	Letter[] letters;
	int color;
	int size;
	
	Word(){}
	
	Word(String _word, PVector _start_pos, int _color, int _size, PApplet _p){
		p = _p;
		word = _word;
		start_pos = _start_pos;
		if(p.random(1) > 0.9f)
			color = p.color(0, 360, 100);
		else
			color = 255;
		size = _size;
		
		letters = new Letter[word.length()];
		
		for(int i = 0; i < word.length(); i++){
			letters[i] = new Letter(word.charAt(i), word, new PVector(start_pos.x+(i)*Semantics.xStep, start_pos.y), color, size, i, p);
		}
	}
	
	void update(){
		for(int i = 0; i < letters.length; i++){
			letters[i].update();
		}
	}
	
	void display(){
		for(int i = 0; i < letters.length; i++){
			if(letters[i].shown)
				letters[i].display();
		}
	}

}