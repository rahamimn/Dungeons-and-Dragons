
public class Position {

	private int x;
	private int y;
	
	public Position(int x ,int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int n){
		this.x = n;
	}
	
	public void setY(int n){
		this.y = n;
	}
	
	public Position getUp(){
		return new Position(x-1, y);
	}
	
	public Position getDown(){
		return new Position(x+1, y);
	}
	
	public Position getLeft(){
		return new Position(x, y-1);
	}
	
	public Position getRight(){
		return new Position(x, y+1);
	}

	public boolean inBounds() {
		return (x >= 0 && y >= 0);
	}

//	public void printPosition(String message){
//		System.out.println("["+message+"] | ("+x+","+y+")");
//	}
	
	public boolean isEqual(Position toCompare){
		return (toCompare.getX() == this.getX() && toCompare.getY() == this.getY()); 
	}



}
