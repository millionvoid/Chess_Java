
public class Point{
	public int row;
	public int col;
	public Point(int r,int c) {
		row=r;col=c;
	}
	public boolean isInBoard() {
		return row>=0&&row<=7&&col>=0&&col<=7;
	}
	public boolean isSame33Square(Point other) {
		return Math.abs(row-other.row)<=1&&Math.abs(col-other.col)<=1;
	}
	public boolean isBlank(Board board) {
		return isInBoard()&&(board.chessBoard[row][col]==null||board.chessBoard[row][col]==Chess.NONE);
	}
	public boolean isBlack(Board board) {
		return !isBlank(board)&&board.isBlack[row][col];
	}
	public boolean isWhite(Board board) {
		return !isBlank(board)&&!board.isBlack[row][col];
	}
	public boolean noBarrierSameLine(Point other,Board board) {
		if(!other.isInBoard())
			return false;
		int row_dif=other.row-this.row;
		int col_dif=other.col-this.col;
		if(row_dif==0&&col_dif==0)
			return true;
		if(row_dif==0)
		{
			if(col_dif>0)
			{
				for(int i=this.col+1;i<other.col;i++)
				{
					if(!board.isBlank(this.row,i))
						return false;
				}
				return true;
			}
			else
			{
				for(int i=this.col-1;i>other.col;i--)
				{
					if(!board.isBlank(this.row,i))
						return false;
				}
				return true;
			}
		}
		else if(col_dif==0)
		{
			if(row_dif>0)
			{
				for(int i=this.row+1;i<other.row;i++)
				{
					if(!board.isBlank(i, this.col))
						return false;
				}
				return true;
			}
			else
			{
				for(int i=this.row-1;i>other.row;i--)
				{
					if(!board.isBlank(i, this.col))
						return false;
				}
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	public boolean noBarrierTilted(Point other,Board board) {
		if(!other.isInBoard())
			return false;
		int row_dif=other.row-this.row;
		int col_dif=other.col-this.col;
		if(row_dif==0&&col_dif==0)
			return true;
		if(row_dif==0||col_dif==0)
			return false;
		if(Math.abs(row_dif)!=Math.abs(col_dif))
			return false;
		if(row_dif<0)
		{
			if(col_dif<0)
			{
				for(int i=this.row-1;i>other.row;i--)
					if(!board.isBlank(i, this.col-(this.row-i)))
						return false;
				return true;
			}
			else
			{
				for(int i=this.row-1;i>other.row;i--)
					if(!board.isBlank(i, this.col+(this.row-i)))
						return false;
				return true;
			}
		}
		else
		{
			if(col_dif<0)
			{
				for(int i=this.row+1;i<other.row;i++)
					if(!board.isBlank(i, this.col-(i-this.row)))
						return false;
				return true;
			}
			else
			{
				for(int i=this.row+1;i<other.row;i++)
					if(!board.isBlank(i, this.col+(i-this.row)))
						return false;
				return true;
			}
		}
	}
}