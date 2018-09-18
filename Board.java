
public class Board {
	Chess [][] chessBoard;
	boolean [][] isBlack;
//	Chessman [][] chessmans;
	public Board(Chess[][] chessBoard, boolean [][]isBlack) {
		this.chessBoard=chessBoard;
		this.isBlack=isBlack;
	}
	public Board () {
		chessBoard=new Chess[8][8];
		isBlack=new boolean[8][8];
	}
	public void putOrReplaceChessman(Chess chess, Boolean isBlack, Point pos) {
		if(!pos.isInBoard())
			return;
		chessBoard[pos.row][pos.col]=chess;
		this.isBlack[pos.row][pos.col]=isBlack;
	}
	public void putOrReplaceChessman(Chessman chessman,Point pos) {
		putOrReplaceChessman(chessman.type,chessman.isBlack,pos);
		chessman.pos=pos;
	}
	public void putOrReplaceChessman(Chessman chessman) {
		putOrReplaceChessman(chessman.type,chessman.isBlack,chessman.pos);
//		chessmans[chessman.pos.row][chessman.pos.col]=chessman;
	}
	public boolean isBlank(int row,int col) {
		return this.chessBoard[row][col]==null||this.chessBoard[row][col]==Chess.NONE;
	}
	public boolean isBlank(Point pos) {
		return this.chessBoard[pos.row][pos.col]==null||this.chessBoard[pos.row][pos.col]==Chess.NONE;
	}
	public void reset() {
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(i<2)
				{
					isBlack[i][j]=true;
				}
				else
				{
					isBlack[i][j]=false;
				}
				if(i==1||i==6)
				{
					chessBoard[i][j]=Chess.BING;
//					chessmans[i][j]=new Pawn(i, j, isBlackIn(i,j));
				}
				else if(i==0||i==7)
				{
					if(j==0||j==7)
					{
						chessBoard[i][j]=Chess.CHE;
//						chessmans[i][j]=new Pawn(i, j, isBlackIn(i,j));
					}
					else if(j==1||j==6)
					{
						chessBoard[i][j]=Chess.MA;
//						chessmans[i][j]=new Knight(i, j, isBlackIn(i,j));
					}
					else if(j==2||j==5)
					{
						chessBoard[i][j]=Chess.XIANG;
//						chessmans[i][j]=new Bishop(i, j, isBlackIn(i,j));
					}
					else if(j==3)
					{
						chessBoard[i][j]=Chess.HOU;
//						chessmans[i][j]=new Queen(i, j, isBlackIn(i,j));
					}
					else
					{
						chessBoard[i][j]=Chess.WANG;
//						chessmans[i][j]=new King(i, j, isBlackIn(i,j));
					}
				}
				else {
					chessBoard[i][j]=Chess.NONE;
				}
			}
		}
	}
	public boolean isBlackIn(int row,int col) {
		if(!new Point(row, col).isInBoard())
			return false;
		return !isBlank(row, col)&&isBlack[row][col];
	}
	public boolean isBlackIn(Point pos) {
		if(!pos.isInBoard())
			return false;
		return !isBlank(pos)&&isBlack[pos.row][pos.col];
	}
	public boolean isWhiteIn(int row,int col) {
		if(!new Point(row, col).isInBoard())
			return false;
		return !isBlank(row, col)&&!isBlack[row][col];
	}
	public boolean isWhiteIn(Point pos) {
		if(!pos.isInBoard())
			return false;
		return !isBlank(pos)&&!isBlack[pos.row][pos.col];
	}
	public Chessman getChessman(int row,int col) {
		if(chessBoard[row][col]==null)
			return null;
		if(chessBoard[row][col]==Chess.CHE)
		{
			return new Rook(row, col, isBlack[row][col]);
		}
		else if(chessBoard[row][col]==Chess.MA)
		{
			return new Knight(row, col, isBlack[row][col]);
		}
		else if(chessBoard[row][col]==Chess.XIANG)
		{
			return new Bishop(row, col, isBlack[row][col]);
		}
		else if(chessBoard[row][col]==Chess.WANG)
		{
			return new King(row, col, isBlack[row][col]);
		}
		else if(chessBoard[row][col]==Chess.HOU)
		{
			return new Queen(row, col, isBlack[row][col]);
		}
		else if(chessBoard[row][col]==Chess.BING)
		{
			return new Pawn(row, col, isBlack[row][col]);
		}
		else
		{
			return null;
		}
	}
	public void moveChess(Point from, Point to) {
		if(!from.isInBoard()||!to.isInBoard())
			return;
		this.chessBoard[to.row][to.col]=this.chessBoard[from.row][from.col];
		this.isBlack[to.row][to.col]=this.isBlack[from.row][from.col];
		this.isBlack[from.row][from.col]=false;
		this.chessBoard[from.row][from.col]=Chess.NONE;
	}
	public void print() {
		System.out.println("Board:");
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.print(isBlackIn(i,j)?"B":"");
				System.out.print(isWhiteIn(i,j)?"W":"");
				System.out.print(toString(chessBoard[i][j])+"\t");
			}
			System.out.println();
		}
	}

	public static String toString(Chess chess)
	{
		if(chess==Chess.BING)
		{
			return "兵";
		}
		if(chess==Chess.HOU)
		{
			return "后";
		}
		if(chess==Chess.WANG)
		{
			return "王";
		}
		if(chess==Chess.CHE)
		{
			return "车";
		}
		if(chess==Chess.MA)
		{
			return "马";
		}
		if(chess==Chess.XIANG)
		{
			return "象";
		}
		return "无";
	}
}
