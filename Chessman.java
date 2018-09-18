
public abstract class Chessman {
	public Point pos;
	public boolean isBlack;
	public Chess type;
	public Chessman(int row,int col,boolean isBlack) {
		// TODO Auto-generated constructor stub
		pos=new Point(row, col);
		this.isBlack=isBlack;
	}
	abstract boolean canReach(Point pos, Board board);
	boolean canEat(Point pos, Board board) {
		return this.canReach(pos,board)&&isEnemyTo(pos, board);
	}
	boolean isEnemyTo(Point pos, Board board) {
		return (!pos.isBlank(board))&&(this.isBlack^pos.isBlack(board));
	}
	boolean canMoveTo(Point pos, Board board) {
		return (canReach(pos,board)&&pos.isBlank(board))||canEat(pos,board);
	}
	int nextRow()
	{
		return this.pos.row+(isBlack?1:-1);
	}
}
