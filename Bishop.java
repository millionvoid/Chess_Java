
public class Bishop extends Chessman{

	public Bishop(int row, int col, boolean isBlack) {
		super(row, col, isBlack);
		type=Chess.XIANG;
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean canReach(Point pos, Board board) {
		return this.pos.noBarrierTilted(pos, board);
	}

}
