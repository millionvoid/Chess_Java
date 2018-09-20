
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sql.rowset.CachedRowSet;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.DefaultEditorKit.CutAction;



public class Chessboard extends JFrame {
	
	// data
	Logic m_logic;
	final int WIDTH = 850;
	final int HEIGHT = 700;
	final int BORDER_WIDTH = 50;
	final int BORDER_HEIGHT = 50;
	final int BUTTON_WIDTH = 75;
	final int BUTTON_HEIGHT = 30;
	
	private int cellStartH[][] = new int[8][8];
	private int cellStartW[][] = new int[8][8];
	
	int cellHeight;
	int cellWidth;
	
	private JPanel uiPanel;
	private JButton button; 
	
	public Chessboard() {
		super("JML is so awesome");
		
		// data
		m_logic = new Logic();

		
		// ui
		// window size
		setVisible(true);
		setSize(WIDTH, HEIGHT);

		// window location
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = dimension.height;
		int screenWidth  = dimension.width;
		int windowStartW=(screenWidth-WIDTH)/2;
		int windowStartH=(screenHeight-HEIGHT)/2;
		setLocation(windowStartW,windowStartH);

		// getting cell shape
		cellHeight = (HEIGHT - 2*BORDER_HEIGHT)/8;
		cellWidth = (WIDTH - 2*BORDER_WIDTH)/8;
		if(cellHeight > cellWidth) cellHeight = cellWidth;
		else 					   cellWidth  = cellHeight;

		// cell locations
		for(int i = 0; i < 8; i ++) {
			for(int j = 0; j < 8; j ++) {
				cellStartH[i][j] = BORDER_HEIGHT + i*cellHeight;
				cellStartW[i][j] = BORDER_WIDTH + j*cellWidth;
			}
		}
		
		
		// methods @window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			}
		);
		
		
		//// panels
		// UI panel
		uiPanel = new JPanel(new BorderLayout());
		uiPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int xpos = e.getX();
				int ypos = e.getY();
				int i = (xpos - BORDER_WIDTH) / cellWidth;
				int j = (ypos - BORDER_HEIGHT) / cellHeight;
				
				System.out.println(String.valueOf(i)+String.valueOf(j));
				m_logic.click(i, j);
			}
		});
		
		// button panel
		button = new JButton("Start");
		button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setVisible(true);
		
		//methods @button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch (m_logic.getGameState()) {
				case BLANK:
					m_logic.gameStart();
					break;
				case GOING:
					m_logic.gameTerminate();
					break;
				case WHITE:
					//
				case DRAW:
					//
				case BLACK:
					m_logic.gameReset();
					break;
				default:
					break;
				}
			}
		});
		
//		buttonPanel = new JPanel(new BoxLayout(this.getContentPane(), windowEndH));
//		buttonPanel.add(button);
		JPanel buttonPanel = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.add(button);
		
		this.getContentPane().add(uiPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.EAST);
		
		// paint
		repaint();
	}
	
	private static BufferedImage getIcon(boolean isBlack, Chess role) throws IOException {
		BufferedImage ans = null;
		System.out.println(role);
		if(!isBlack) {
			switch (role) {
				case CHE:
					ans = ImageIO.read(new File("images/white_che.png"));
					break;
				case MA:
					ans = ImageIO.read(new File("images/white_ma.png"));
					break;
				case XIANG:
					ans = ImageIO.read(new File("images/white_xiang.png"));
					break;
				case WANG:
					ans = ImageIO.read(new File("images/white_wang.png"));
					break;
				case HOU:
					ans = ImageIO.read(new File("images/white_hou.png"));
					break;
				default:
					ans = ImageIO.read(new File("images/white_bing.png"));
					break;	
			}
		} else {
			switch (role) {
				case CHE:
					ans = ImageIO.read(new File("images/black_che.png"));
					break;
				case MA:
					ans = ImageIO.read(new File("images/black_ma.png"));
					break;
				case XIANG:
					ans = ImageIO.read(new File("images/black_xiang.png"));
					break;
				case WANG:
					ans = ImageIO.read(new File("images/black_wang.png"));
					break;
				case HOU:
					ans = ImageIO.read(new File("images/black_hou.png"));
					break;
				default:
					ans = ImageIO.read(new File("images/black_bing.png"));	
					break;
			}
		}
		
		BufferedImage bi2 = new BufferedImage(ans.getWidth(), ans.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, ans.getWidth(), ans.getHeight());
		Graphics2D g2 = bi2.createGraphics();
		g2.setClip(shape);
		// 使用 setRenderingHint 设置抗锯齿
		g2.drawImage(ans, 0, 0, null);
		// 设置颜色
		g2.setBackground(Color.green);
		g2.dispose();
		
		return bi2;
	}

	
	public void paint(Graphics graphics) {
		// painting tokens
		
		super.paint(graphics);

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(uiPanel==null);
				Graphics panelGraphics = uiPanel.getGraphics();
				
				int windowHeight = getSize().height;
				int windowWidth  = getSize().width;
				
				cellHeight = (HEIGHT - 2*BORDER_HEIGHT)/8;
				cellWidth = (WIDTH - 2*BORDER_WIDTH)/8;
				if(cellHeight > cellWidth) cellHeight = cellWidth;
				else 					   cellWidth  = cellHeight;

				// cell locations
				for(int i = 0; i < 8; i ++) {
					for(int j = 0; j < 8; j ++) {
						cellStartH[i][j] = BORDER_HEIGHT + i*cellHeight;
						cellStartW[i][j] = BORDER_WIDTH + j*cellWidth;
					}
				}
				
				for(int i = 0; i < 8; i ++) {
					for(int j = 0; j < 8; j ++) {
						if( (i+j)%2==0 ) {
							panelGraphics.setColor(new Color(200, 210, 210));
						} else {
							panelGraphics.setColor(Color.DARK_GRAY);
						}
						panelGraphics.fillRect(cellStartW[i][j], cellStartH[i][j], cellWidth, cellHeight);
						
						if(m_logic.getChessAt(i, j)!=Chess.NONE) {
							try {
								panelGraphics.drawImage(getIcon(m_logic.getIsBlackAt(i, j), m_logic.getChessAt(i, j)), cellStartW[i][j] + cellWidth / 4, cellStartH[i][j] + cellHeight / 6, cellWidth / 2, cellHeight * 2 / 3, null);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						if(m_logic.getHasBorderAt(i, j)==true) {
							panelGraphics.setColor(Color.RED);
							panelGraphics.drawLine(cellStartW[i][j], cellStartH[i][j], cellStartW[i][j], cellStartH[i][j]+cellHeight);
							panelGraphics.drawLine(cellStartW[i][j], cellStartH[i][j], cellStartW[i][j]+cellWidth, cellStartH[i][j]);
							panelGraphics.drawLine(cellStartW[i][j]+cellWidth, cellStartH[i][j]+cellHeight, cellStartW[i][j], cellStartH[i][j]+cellHeight);
							panelGraphics.drawLine(cellStartW[i][j]+cellWidth, cellStartH[i][j]+cellHeight, cellStartW[i][j]+cellWidth, cellStartH[i][j]);
						}
					}
				}
				
			}
		}).start();
	}
}
