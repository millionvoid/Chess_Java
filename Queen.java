
public class Queen extends Chessman{

	public Queen(int row, int col, boolean isBlack) {
		super(row, col, isBlack);
		type=Chess.HOU;
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean canReach(Point pos, Board board) {
		return this.pos.noBarrierSameLine(pos, board)||this.pos.noBarrierTilted(pos,board);
	}
	
}
