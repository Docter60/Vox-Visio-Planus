package asset;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import math.Mathg;

public class ColorGradient {

	private List<Color> nodes;
	
	public ColorGradient(){
		this.nodes = new ArrayList<Color>();
	}
	
	public void addNode(Color color){
		nodes.add(color);
	}
	
	public void setNode(int index, Color color){
		if(index < nodes.size())
			nodes.set(index, color);
		else
			System.err.println("Node index out of range!");
	}
	
	public void configureRainbowGradient(){
		addNode(Color.RED);
		addNode(Color.ORANGE);
		addNode(Color.YELLOW);
		addNode(Color.GREEN);
		addNode(Color.CYAN);
		addNode(Color.BLUE);
		addNode(Color.MAGENTA);
	}
	
	public Color getColor(float lerp_t){
		if(lerp_t > 1){
			System.err.println("lerp_t cannot be more than 1!");
			return null;
		}
		
		float nodeSpread = 1f / (float) nodes.size();
		int index = (int) Math.floor((float) nodes.size() * lerp_t);
		float nodeLerp_t = (lerp_t - index * nodeSpread) * nodes.size();
		
		int r1 = nodes.get(index).getRed();
		int g1 = nodes.get(index).getGreen();
		int b1 = nodes.get(index).getBlue();
		int r2;
		int g2;
		int b2;
		
		if(index + 1 < nodes.size()){
			r2 = nodes.get(index + 1).getRed();
			g2 = nodes.get(index + 1).getGreen();
			b2 = nodes.get(index + 1).getBlue();
		} else{
			r2 = r1;
			g2 = g1;
			b2 = b1;
		}
		
		int r = (int) Mathg.lerp(r1, r2, nodeLerp_t);
		int g = (int) Mathg.lerp(g1, g2, nodeLerp_t);
		int b = (int) Mathg.lerp(b1, b2, nodeLerp_t);
		
		return new Color(r, g, b);
	}
	
}
