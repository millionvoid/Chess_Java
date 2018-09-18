
public class Pawn extends Chessman{

	public Pawn(int row, int col, boolean isBlack) {
		super(row, col, isBlack);
		type=Chess.BING;
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean canReach(Point pos, Board board) {
		if(!pos.isInBoard())
			return false;
		if(this.pos.row==(isBlack?1:6))
			if(pos.col==this.pos.col&&
			(pos.row==(nextRow())||
			pos.row==(this.pos.row+(isBlack?2:-2))&&
					new Point(nextRow(), this.pos.col).isBlank(board))
			)
				return true;
			else
				return false;
		else
			if(pos.col==this.pos.col&&pos.row==nextRow())
				return true;
			else
				return false;
	}
	
	@Override
	boolean canEat(Point pos, Board board) {
		if(!this.isEnemyTo(pos, board))
			return false;
		return (pos.col==this.pos.col+1||pos.col==this.pos.col-1)&&
				pos.row==nextRow();
	}
	

}
