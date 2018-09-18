import java.util.Scanner;

/*King
Queen
Rook
Bishop
Knight
Pawn*/
public class Main {

	public static void main(String[] args) {
		Logic logic=new Logic();
		logic.gameStart();
//		String input;
//		Scanner scanner=new Scanner(System.in);
//		int row,col;
//		while ((input=scanner.nextLine())!=null) {
//			String[]pStrings=input.split(" ");
//			if(pStrings.length!=2)
//				continue;
//			row=Integer.parseInt(pStrings[0]);
//			col=Integer.parseInt(pStrings[1]);
//			logic.click(row, col);
//			logic.printBoard();
//		}
		logic.board.moveChess(new Point(6, 4), new Point(4, 4));
		logic.board.moveChess(new Point(6, 3), new Point(6, 2));
		logic.board.moveChess(new Point(6, 2), new Point(6, 1));
		logic.printBoard();
		Chessman chessman=logic.board.getChessman(7, 3);
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(chessman.canReach(new Point(i,j), logic.board))
					System.out.print("1 ");
				else
					System.out.print("0 ");
					
			}
			System.out.println();
		}
	}

}
