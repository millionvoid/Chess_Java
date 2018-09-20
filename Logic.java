import java.io.BufferedWriter;

public class Logic {

	enum State {
		BLANK,
		GOING,
		INTER,
		BLACK,
		WHITE,
		DRAW
	}
	
	enum StepInOneTurn {
		NOFOCUS_BLACKTURN,
		FOCUSED_BLACKTURN,
		NOFOCUS_WHITETURN,
		FOCUSED_WHITETURN,
	}
	
	Chess chessIn[][];
	boolean isBlackChess[][];
	boolean hasBorder[][];
	Board board;
	Point focused;
	
	public Chess getChessAt(int i, int j) {
		return chessIn[i][j];
	}
	
	public boolean getIsBlackAt(int i, int j) {
		return isBlackChess[i][j];
	}
	
	public boolean getHasBorderAt(int i, int j) {
		return hasBorder[i][j];
	}
	
	State gameState;
	StepInOneTurn stepState;
	State getGameState() {
		return gameState;
	}
	void gameStart() {
		if(gameState==State.BLANK)
			gameState=State.GOING;
		stepState=StepInOneTurn.NOFOCUS_WHITETURN;
	}
	void gameReset() {
		if(gameState==State.BLANK||gameState==State.BLACK||gameState==State.WHITE||gameState==State.DRAW||gameState==State.INTER)
			board.reset();
		gameState=State.BLANK;
	}
	void gameTerminate() {
		if(gameState==State.GOING)
			gameState=State.BLANK;
	}
	void click(int row, int col) {
		if(gameState!=State.GOING)
			return;
		else
		{
			Point clickPoint=new Point(row,col);
			if(!clickPoint.isInBoard())
				return;
			Chessman clickChessman=board.getChessman(row, col);
			if(stepState==StepInOneTurn.NOFOCUS_WHITETURN)
			{
				if(board.isWhiteIn(clickPoint))
				{
					focused=clickPoint;
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(clickChessman.canMoveTo(new Point(i, j), board))
							{
								hasBorder[i][j]=true;
							}
							else
							{
								hasBorder[i][j]=false;
							}
						}
					}
					hasBorder[row][col]=true;
					stepState=StepInOneTurn.FOCUSED_WHITETURN;
				}
			}
			else if(stepState==StepInOneTurn.FOCUSED_WHITETURN)
			{
				if(hasBorder[row][col]&&!board.isWhiteIn(row, col))
				{
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(i==focused.row&&j==focused.col || i==row&&j==col)
							{
								hasBorder[i][j]=true;
							}
							else
							{
								hasBorder[i][j]=false;
							}
						}
					}
					board.moveChess(new Point(focused.row,focused.col), new Point(row, col));
					switch (endingJudged()){
					case 1:
						gameState=State.WHITE;
						break;
					case -1:
						gameState=State.BLACK;
						break;
					case 0:
						gameState=State.DRAW;
						break;
					default:
						break;
					}
					stepState=StepInOneTurn.NOFOCUS_BLACKTURN;
				}
				else if(board.isWhiteIn(row, col))
				{
					focused=clickPoint;
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(clickChessman.canMoveTo(new Point(i, j), board))
							{
								hasBorder[i][j]=true;
							}
							else
							{
								hasBorder[i][j]=false;
							}
						}
					}
					hasBorder[row][col]=true;
					stepState=StepInOneTurn.FOCUSED_WHITETURN;
				}
			}
			else if(stepState==StepInOneTurn.NOFOCUS_BLACKTURN)
			{
				if(board.isBlackIn(clickPoint))
				{
					focused=clickPoint;
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(clickChessman.canMoveTo(new Point(i, j), board))
							{
								hasBorder[i][j]=true;
							}
							else
							{
								hasBorder[i][j]=false;
							}
						}
					}
					hasBorder[row][col]=true;
					stepState=StepInOneTurn.FOCUSED_BLACKTURN;
				}
			}
			else if(stepState==StepInOneTurn.FOCUSED_BLACKTURN)
			{
				if(hasBorder[row][col]&&!board.isBlackIn(row, col))
				{
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(i==focused.row&&j==focused.col || i==row&&j==col)
							{
								hasBorder[i][j]=true;
							}
							else
							{
								hasBorder[i][j]=false;
							}
						}
					}
					board.moveChess(new Point(focused.row,focused.col), new Point(row, col));
					switch (endingJudged()){
					case 1:
						gameState=State.WHITE;
						break;
					case -1:
						gameState=State.BLACK;
						break;
					case 0:
						gameState=State.DRAW;
						break;
					default:
						break;
					}
					stepState=StepInOneTurn.NOFOCUS_WHITETURN;
				}
				else if(board.isBlackIn(row, col))
				{
					focused=clickPoint;
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(clickChessman.canMoveTo(new Point(i, j), board))
							{
								hasBorder[i][j]=true;
							}
							else
							{
								hasBorder[i][j]=false;
							}
						}
					}
					hasBorder[row][col]=true;
					stepState=StepInOneTurn.FOCUSED_BLACKTURN;
				}
			}
		}
	}
	int check()//0 双方都未被将军 1 只有白方被将军 -1 黑方被将军 -2 双方都被将军
	{
		if(whiteChecked())
		{
			if(blackChecked())
				return -2;
			else
				return 1;
		}
		else
		{
			if(blackChecked())
				return -1;
			else
				return 0;
		}
	}
	boolean whiteChecked() {
		Point whiteKingPosition=getWhiteKingPosition();
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(isBlackChess[i][j])
				{
					if(board.getChessman(i, j).canEat(whiteKingPosition, board))
						return true;
				}
			}
		}
		return false;
	}
	boolean blackChecked() {
		Point blackKingPosition=getBlackKingPosition();
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(board.isWhiteIn(i,j))
				{
					if(board.getChessman(i, j).canEat(blackKingPosition, board))
						return true;
				}
			}
		}
		return false;
	}
	
	int checkmate() {return 0;}//0 未将死 1 白胜 -1 黑胜
	
	boolean stalemate() {return false;}
	
	int endingJudged() {
		if(check()!=0)
		{
			switch (checkmate()) {
			case 0:
				return -2;
			case 1:
				return 1;
			case -1:
				return -1;
			default:
				return -2;
			}
		}
		else if(stalemate())
			return 0;
		else {
			return -2;
		}
	}//0 平局 1 白胜 -1 黑胜 -2 游戏尚未结束
	
	public Point getWhiteKingPosition() {
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(!isBlackChess[i][j]&&chessIn[i][j]==Chess.WANG)
					return new Point(i,j);
			}
		}
		return null;
	}
	
	public Point getBlackKingPosition() {
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(isBlackChess[i][j]&&chessIn[i][j]==Chess.WANG)
					return new Point(i,j);
			}
		}
		return null;
	}
	
	public Logic() {
		chessIn=new Chess[8][8];
		isBlackChess=new boolean[8][8];
		hasBorder=new boolean[8][8];
		board=new Board(chessIn,isBlackChess);
		gameState=State.BLANK;
		board.reset();
	}
	public void printBoard() {
		System.out.println("Board:");
		for(int i=-1;i<8;i++)
		{
			System.out.print(i+"\t");
			for(int j=0;j<8;j++)
			{
				if(i==-1)
				{
					System.out.print(j+"\t");
					continue;
				}
				System.out.print(hasBorder[i][j]?"[":"");
				System.out.print(board.isBlackIn(i,j)?"B":"");
				System.out.print(board.isWhiteIn(i,j)?"W":"");
				System.out.print(Board.toString(chessIn[i][j]));
				System.out.print(hasBorder[i][j]?"]":"");
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("White checked?"+(whiteChecked()?"yes":"no"));
		System.out.println("Black checked?"+(blackChecked()?"yes":"no"));
	}
}