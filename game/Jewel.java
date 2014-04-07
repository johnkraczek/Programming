package game;

public class Jewel {
	
	public int type;
	public boolean selected;
	public boolean swap;
	public int moveTo[];
	
	Jewel(){
		//Move To has 3 parts [time Left to move], [X], [Y]. 
		moveTo = new int[3];
		moveTo[0]=0;
		moveTo[1]=0;
		moveTo[2]=0;
	}	
}
