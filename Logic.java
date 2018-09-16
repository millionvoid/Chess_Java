public class Logic {
	enum Chess {
		NONE,
		CHE,
		MA,
		XIANG,
		WANG,
		HOU,
		BING,
	}
	
	enum State {
		BLANK,
		GOING,
		INTER,
		BLACK,
		WHITE,
		DRAW,
	}
	
	Chess chessIn[][];
	boolean isBlackChess[][];
	boolean hasBorder[][];
	
	State gameState;
	State getGameState() {
		return gameState;
	}
	void gameStart() {
		////
	}
	void gameReset() {
		////
	}
	void gmaeTerminate() {
		////
	}
	void click(int row, int col) {
		////
	}
	
	public Logic() {
		////
	}
}