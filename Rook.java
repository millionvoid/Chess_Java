
public class Rook extends Chessman{

	public Rook(int row, int col, boolean isBlack) {
		super(row, col, isBlack);
		type=Chess.CHE;
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean canReach(Point pos, Board board) {
		return this.pos.noBarrierSameLine(pos,board);
	}
	
}
