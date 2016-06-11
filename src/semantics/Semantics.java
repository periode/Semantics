package semantics;

import java.util.ArrayList;

import semantics.Word;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import themidibus.MidiBus;


public class Semantics extends PApplet {

	//----MIDI
	MidiBus midi_beatstep;
	MidiBus midi_kontrol;
	int channel = 0;
	int pitch = 0;
	int velocity = 0;
	int note;
	int value = 0;

	boolean showDebug = false;
	boolean curtain = true;

	//----COLORS
	int background_color;
	float bg_alpha;
	int foreground_color;
	static int[] colors;

	//----GRID
	ArrayList<PVector> grid;
	static int cols;
	static int rows;
	static float xStep;
	static float yStep;
	ArrayList<Integer> grid_color;

	//----WORDS
	public static int fontSize = 48;
	ArrayList<Word> words;
	static char[] allLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	String[] florilege;
	String[] florilege_house = {"house", "music", "all", "night", "long"};
	String[] florilege_verlaine = {"et", "puis", "voici", "mon", "coeur"};
	String[] florilege_tech = {"the", "weight", "of", "a", "system"};
	String[] florilege_girl = {"the", "light", "of", "the", "sun"};
	int index_florilege = 0;

	int current_word = 0;
	
	//----CHARACTER
	ArrayList<Character> characters;
	

	PFont font;

	float[] orientations = {0, PI*0.5f, PI, PI*1.5f}; 

	public void setup() {
		colorMode(HSB, 360, 100, 100);

		//----MIDI
		MidiBus.list();
		midi_beatstep = new MidiBus(this, "Arturia BeatStep", 1);
		midi_beatstep.setBusName("beatstep");

		midi_kontrol = new MidiBus(this, "SLIDER/KNOB", 2);
		midi_kontrol.setBusName("kontrol");

		//----COLORS
		background_color = 0;
		bg_alpha = 180;
		foreground_color = 255;
		colors = new int[5];
		//RGB
		colors[0] = color(242, 235, 144);
		colors[1] = color(241, 208, 225);
		colors[2] = color(141, 195, 223);
		colors[3] = color(163, 209, 181);
		colors[4] = color(245, 192, 140);

		//HSB
		colors[0] = color(56, 41, 95);
		colors[1] = color(329, 14, 95);
		colors[2] = color(200, 37, 87);
		colors[3] = color(143, 22, 82);
		colors[4] = color(30, 43, 96);

		//---GRID
		cols = 9;
		rows = 6;

		xStep = width/cols;
		yStep = height/rows;
		grid_color = new ArrayList<Integer>();
		grid = new ArrayList<PVector>();
		for(float x = xStep*0.5f; x < width; x += xStep){
			for(float y = yStep*0.5f; y < height; y += yStep){
				grid.add(new PVector(x, y));
			}
		}

		//----WORDS
		words = new ArrayList<Word>();
		font = loadFont("Tim-48.vlw");
		textFont(font);
		florilege = florilege_house;
		
		//----CHARACTERS
		characters = new ArrayList<Character>();
		
		setupCharacters();
		
		rectMode(CENTER);

	}

	public void settings(){
		fullScreen();
	}

	public void update(){
		for(int i = 0; i < words.size(); i++){
			words.get(i).update();
		}
		
		for(int i = 0; i < characters.size(); i++){
			characters.get(i).update();
		}
	}

	public void draw() {
		update();
		noCursor();
		colorMode(HSB);
		noCursor();
		noStroke();
		fill(0, bg_alpha);
		rect(width*0.5f, height*0.5f, width, height);
		
//		for(int i = 0; i < grid.size(); i++){
//			fill(255);
//			ellipse(grid.get(i).x, grid.get(i).y, 2, 2);
//		}
		
		for(int i = 0; i < characters.size(); i++){
			characters.get(i).display();
		}

		for(int i = 0; i < words.size(); i++){
			words.get(i).display();
		}
		

		if(showDebug)
			debug();

		if(curtain){
			fill(0);
			stroke(0);
			rect(width*0.5f, height*0.5f, width, height);
		}
	}

	public void debug(){
		textAlign(LEFT);
		textSize(10);
		fill(100, 255, 100);
		text("framerate: "+frameRate, 10, 10);
		text("channel: "+channel, 10, 20);
		text("pitch: "+pitch, 10, 30);
		text("vel: "+velocity, 10, 40);
		text("note: "+note, 10, 50);
		text("value: "+value, 10, 60);
	}
	
	public void addWord(){
		String f = florilege[current_word].toUpperCase();
		PVector g = grid.get((int)random(rows-1) + rows*(int)random(max(0, cols-f.length()-1)));

		Word w = new Word(f, g, colors[(int)random(colors.length)], 48, this);
		words.add(w);
	}
	
	public void setupCharacters(){
		characters.clear();
		
		ArrayList<PVector> list = new ArrayList<PVector>(grid);
		for(int i = 0; i < grid.size(); i = i + 1){
			PVector p = list.get((int)random(list.size()));
			Character c = new Character(p, allLetters[i % allLetters.length], colors[(int)random(colors.length)], i, this);
			characters.add(c);
			list.remove(p);
		}
		list.clear();
	}

	public void noteOn(int c, int p, int v, long t, String s){
		channel = c;
		pitch = p;
		velocity = v;

		if(s == "kontrol"){
			switch(p){
			case 44:
				break;
			case 45:
				break;
			case 36:
				break;
			case 50:
				break;
			case 51:
				break;
			case 42:
				break;
			case 43:
				break;
			default:
				break;
			}
		}

		if(s == "beatstep"){
			if(c == 0){
				switch(p){
				case 44:
					break;
				case 45:
					break;
				case 46:
					break;
				case 47:
					break;
				case 48:
					break;
				case 49:
					break;
				case 50:
					break;
				case 51:
					break;
				case 36://----SECOND ROW
					break;
				case 37:
					break;
				case 38:
					break;
				case 39:
					break;
				case 40:
					break;
				case 41:
					break;
				case 42:
					break;
				case 43:
					break;
				default:
					break;
				}
			}else if(c == 1){
				switch(p){
				case 44:
					break;
				case 45:
					break;
				case 46:
					break;
				case 47:
					break;
				case 48:
					break;
				case 49:
					break;
				case 50:
					break;
				case 51:
					break;
				case 36://----SECOND ROW
					break;
				case 37:
					break;
				case 38:
					break;
				case 39:
					break;
				case 40:
					break;
				case 41:
					break;
				case 42:
					break;
				case 43:

					break;
				default:
					break;
				}
			}
		}
	}

	public void controllerChange(int c, int n, int v, long t, String s){
		channel = c;
		note = n;
		value = v;

		if(s == "kontrol"){
			switch(n){
			case 0://--------------------------------FADERS

				break;
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:

				break;
			case 6:

				break;
			case 7:

				break;
			case 8://--------------------------------KNOBS

				break;
			case 9:

				break;
			case 10:

				break;
			case 11:

				break;
			case 12:

				break;
			case 13:

				break;
			case 14:

				break;
			case 15:

				break;
			default:
				break;
			}
		}else if(s == "beatstep"){
			v = v - 64;//normalize

			if(n == 7){//big knob

			}

			if(c == 0){
				switch(n){
				case 10:
					break;
				case 11:
					break;
				case 12:
					break;
				case 13:
					break;
				case 14:
					break;
				case 15:
					break;
				case 16:
					break;
				case 17:

					break;
				case 18://--------------------------------SECOND ROW
					break;
				case 19:
					break;
				case 20:
					break;
				case 21:
					break;
				case 22:
					break;
				case 23:
					break;
				case 24:
					break;
				case 25:
					break;
				default:
					break;
				}
			}else if(c == 1){ // ------------------ LINKS
				switch(n){
				case 10:
					break;
				case 11:
					break;
				case 12:
					break;
				case 13:
					break;
				case 14:
					break;
				case 15:

					break;
				case 16:

					break;
				case 17:

					break;
				case 18://--------------------------------SECOND ROW

					break;
				case 19:

					break;
				case 20:

					break;
				case 21:

					break;
				case 22:

					break;
				case 23:

					break;
				case 24:

					break;
				case 25:

					break;
				default:
					break;
				}
			}
		}
	}

	public void keyPressed(){
		for(int i = 0; i < characters.size(); i++){
			characters.get(i).shown = false;
			characters.get(i).shuffle = true;
			
			if((char)characters.get(i).ch == (char)key){
				characters.get(i).shown = true;
				characters.get(i).shuffle = false;
			}	
		}
		
		if(key == ' ')
			curtain = !curtain;
		
		if(key == '1'){
			words.clear();
			
			addWord();
		}else if(key == '2'){//shuffle
			for(int i = 1; i < words.get(0).letters.length-1; i++){
				words.get(0).letters[i].shuffle = !words.get(0).letters[i].shuffle; 
			}
		}else if(key == '3'){//display only one letter
			int index = (int)random(words.get(0).letters.length);
			for(int i = 0; i < words.get(0).letters.length; i++){
				words.get(0).letters[i].shown = false;
			}
			words.get(0).letters[index].shown = true;
		}else if(key == '4'){//display all
			for(int i = 0; i < words.get(0).letters.length; i++){
				words.get(0).letters[i].shown = true;
			}
		}else if(key == '5'){
			for(int i = 0; i < words.get(0).letters.length; i++){
				words.get(0).letters[i].blown = !words.get(0).letters[i].blown;
			}
		}else if(key == '6'){
			for(int i = 0; i < words.get(0).letters.length; i++){
				words.get(0).letters[i].target_theta = randomOrientation();
				words.get(0).letters[i].lerp_val_theta = 0;
			}
		}else if(key == '7'){
			for(int i = 0; i < words.get(0).letters.length; i++){
				words.get(0).letters[i].theta = 0;
				words.get(0).letters[i].lerp_val_theta = 0;
			}
		}else if(key == '8'){
			
		}else if(key == '9'){
			
		}else if(key == '0'){
			words.clear();
		}else if(keyCode == UP){
			current_word = (int)random(florilege.length);
			
			words.clear();
			addWord();
		}else if(keyCode == DOWN){
			current_word = 0;
			
			words.clear();
			addWord();
		}else if(keyCode == LEFT){
			current_word--;
			if(current_word < 0)
				current_word = florilege.length-1;
			
			words.clear();
			addWord();
		}else if(keyCode == RIGHT){
			current_word++;
			if(current_word >= florilege.length)
				current_word = 0;
			
			words.clear();
			addWord();
		}
		
		//TODO have a way to cycle through vertical positions for one given word
		
		//TODO superimpose words?
		//TODO grid has letters on it > and then I can type them away > it needs different "modes of apparition"
		//TODO reveal one letter at a time
		//TODO have 'doppelganger' for words?
		//TODO find a way to display all words at once
		//TODO it will all be solved when i'll have a bigger grid
	}
	
	public float randomOrientation(){
		float _theta = orientations[(int)random(orientations.length)];
		return _theta;
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { semantics.Semantics.class.getName() });
	}
}
