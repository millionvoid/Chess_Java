
public class King extends Chessman{

	public King(int row, int col, boolean isBlack) {
		super(row, col, isBlack);
		type=Chess.WANG;
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean canReach(Point pos, Board board) {
		return pos.isInBoard()&&pos.isSame33Square(this.pos);
	}
	
}
