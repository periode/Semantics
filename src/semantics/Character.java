package semantics;

import processing.core.PApplet;
import processing.core.PVector;

public class Character {
	
	PApplet p;
	PVector pos;
	char ch;
	int index;
	
	int color;
	
	boolean shown = true;
	boolean shuffle = true;
	
	Character(){}
	
	Character(PVector _pos, char _ch, int _color, int _index, PApplet _p){
		p = _p;
		ch = _ch;
		pos = _pos;
		index = _index;
		color = 255;
	}
	
	void update(){
//		this.pos.x += 0.5f*(1 + (index % Semantics.cols-1));
		
		if(pos.x > p.width)
			this.pos.x = 0;
		
		this.pos.y += 0.5f*(1+ (index % Semantics.rows-1));
		
		if(pos.y > p.height)
			pos.y = 0;
	}
	
	void display(){
		p.pushMatrix();
		p.translate(pos.x, pos.y);
		if(shown == true){
			p.noFill();
			p.stroke(color);
//			p.rect(0, 0, Semantics.fontSize, Semantics.fontSize);
			p.fill(color);
		}else
			p.fill(color, 20+(10*PApplet.cos(p.millis()*0.1f * index % Semantics.rows)));
		
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
	
		if(shuffle)
			p.text(pickRandomLetter(), 0, 0);
		else
			p.text(ch, 0, 0);
		p.popMatrix();
	}
	
	char pickRandomLetter(){
		return Semantics.allLetters[(int)p.random(Semantics.allLetters.length)];
	}

}
