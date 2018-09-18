
public class Knight extends Chessman{

	public Knight(int row, int col, boolean isBlack) {
		super(row, col, isBlack);
		type=Chess.MA;
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean canReach(Point pos, Board board) {
		if(!pos.isInBoard())
			return false;
		int row_dif=Math.abs(this.pos.row-pos.row);
		int col_dif=Math.abs(this.pos.col-pos.col);
		return (row_dif==2)&&(col_dif==1)||(row_dif==1)&&(col_dif==2);
	}
	
}
