package TurmSpiel;

import java.util.Scanner;

public class TurmSpielMain 
{
	static final int FREE_SPACE = 0;
	static final int TOWER_COUNT = 3;
	
	
	static int[][] towers;
	static Scanner generalInput = new Scanner(System.in);
	static boolean inGame = false;
	static boolean inMenu = true;
	static String status = "Ready to start";
	static int towerlimit = 0;
	static String[] towerSkin;
	
	public static void main(String[] args)
	{
		while(inMenu)
		{
			drawMenu();
			menuInput(generalInput.next());
			generalInput.reset();			
		}
	}
	
	private static void customGame(int towerHeight)
	{
		setupTowerSkin(towerHeight);
		towerlimit=towerHeight-1;
		towers = new int[TOWER_COUNT][towerHeight];
		for(int x = 0; x <TOWER_COUNT;x++)
		{
			for(int y = 0; y <towerHeight;y++)
			{
				towers[x][y]= FREE_SPACE;
			}		
		}		
		for(int y = 0; y <towerHeight;y++)
		{
			towers[0][y] = y+1;
		}
	}
	private static void setupTowerSkin(int skinHeight)
	{
		skinHeight++;
		towerSkin = new String[skinHeight];
		
		if(skinHeight > 0)		
		{
			int towerWidth;
			towerSkin = new String[skinHeight];
			
			if(skinHeight%2 == 0)
			{
				towerWidth=skinHeight+1;
			}
			else
			{
				towerWidth = skinHeight+2;
			}
			
			char unevenCornerPiceLeft = '▐';
			char middlePice = '|';
			char filling = '█';
			char unevenCornerPiceRight = '▌';
			
			for(int y = 0; y <skinHeight;y++)
			{
				towerSkin[y] = "";
				
				int widthCounter = 0;
				int widthOfFilling = y/2+y%2;
				int widthOfSpace = towerWidth/2-widthOfFilling;
				
				while(widthCounter < widthOfSpace)
				{
					towerSkin[y] += " ";
					widthCounter++;
				}				
				widthCounter = 0;
				
				if(y%2==1)
				{
					towerSkin[y] += unevenCornerPiceLeft;
					widthCounter++;
				}
				while(widthCounter < widthOfFilling)
				{
					towerSkin[y] += filling;
					widthCounter++;
				}
				widthCounter = 0;
				
				towerSkin[y] += middlePice;				
				
				if(y%2==1)
				{
					widthCounter++;
				}	
				while(widthCounter < widthOfFilling)
				{
					towerSkin[y] += filling;
					widthCounter++;
				}
				widthCounter = 0;				
				if(y%2==1)
				{
					towerSkin[y] += unevenCornerPiceRight;
				}				
				while(widthCounter < widthOfSpace)
				{
					towerSkin[y] += " ";
					widthCounter++;
				}	
			}
		}
	}	
	private static String getGameDrawing()
	{
		String statusMessage = "\n\nStatus: " + status + "\n\n";
		String gameBoard = "";		
		String inputRequest = "\n\nPlease enter command: ";
		
		String letterSpacer = "";
		for(int space = 0; space < (towerSkin[0].length()-2)/2;space++)
		{
			letterSpacer += " ";
		}
		
		for(int y = 0; y <= towerlimit;y++)
		{
			for(int x = 0; x <TOWER_COUNT;x++)
			{
				gameBoard += "\t"+ towerSkin[towers[x][y]];
			}		
			gameBoard += "\n";
		}	
		gameBoard += "\n\t"+letterSpacer+"[a]"+ letterSpacer+"\t" +letterSpacer + "[b]"+letterSpacer+"\t" + letterSpacer+ "[c]";
		return statusMessage + gameBoard + inputRequest;
	}	
	private static void solveGame()
	{
		
		if(towerlimit%2 == 1)
		{
			while(towers[TOWER_COUNT-1][0] == 0)
			{
				if(towers[TOWER_COUNT-1][0] == 0)
				{
					move(0,1);
					if(!status.equals("All clear")) {move(1,0);};
					System.out.print(getGameDrawing());
				}
				if(towers[TOWER_COUNT-1][0] == 0)
				{
					move(0,2);
					if(!status.equals("All clear")){move(2,0);};
					System.out.print(getGameDrawing());
				}
				if(towers[TOWER_COUNT-1][0] == 0)
				{
					move(1,2);
					if(!status.equals("All clear")) {move(2,1);};
					System.out.print(getGameDrawing());
				}
			}
		}
		else
		{
			while(towers[TOWER_COUNT-1][0] == 0)
			{
				if(towers[TOWER_COUNT-1][0] == 0)
				{
					move(0,2);
					if(!status.equals("All clear")) {move(2,0);};
					System.out.print(getGameDrawing());
				}
				if(towers[TOWER_COUNT-1][0] == 0)
				{
					move(0,1);
					if(!status.equals("All clear")) {move(1,0);};
					System.out.print(getGameDrawing());
				}
				if(towers[TOWER_COUNT-1][0] == 0)
				{
					move(1,2);
					if(!status.equals("All clear")) {move(2,1);};
					System.out.print(getGameDrawing());
				}
			}			
		}
	}
	private static void gameInput(String commandInput)
	{
		switch(commandInput)
		{
		case "ab":
			move(0,1);
			break;
		case "ac":
			move(0,2);
			break;
		case "ba":
			move(1,0);
			break;
		case "bc":
			move(1,2);
			break;
		case "ca":
			move(2,0);
			break;
		case "cb":
			move(2,1);
			break;
		case "solve":
			solveGame();
			break;
		case "q":
			inGame = false;	
			break;			
		default:
			status = "illigal command";
			break;
		}
	}
	
	private static void menuInput(String commandInput)
	{
		switch(commandInput)
		{
		case "game":
			try 
			{
				System.out.print("\tTower Height: ");
				customGame(generalInput.nextInt());
				inGame = true;
				while(inGame)
				{
					System.out.print(getGameDrawing());
					gameInput(generalInput.next());
					generalInput.reset();
				}
			}
			catch(Exception ex)	
			{ 
				inGame = false;
			}
			break;
		case "q":
			inMenu = false;	
			break;			
		default:
			status = "illigal command";
			break;
		}		
	}
	private static String drawMenu()
	{
		String statusMessage = "\n\nStatus: " + status + "\n\n";
		String menu = "\tgame - Starts a game of towers\n\tq - Exits application\n";	
		String inputRequest = "\n\nPlease enter command: ";
		
		String fullMenu = statusMessage + menu + inputRequest;
		
		System.out.print(fullMenu);
		return fullMenu;
	}
	private static void move(int fromTower , int toTower)
	{
		int yOfToMove = 0;
		int yOfTarget = 0;
		int toMoveValue;
		int targetValue;
		boolean illigalOperation = false;
		boolean sourceFound = false;
		boolean targetFound = false;
		
		while(yOfToMove<=towerlimit && !sourceFound)
		{
			if(towers[fromTower][yOfToMove] == FREE_SPACE) 
			{	
				yOfToMove++;
			}
			else 
			{
				sourceFound = true;
			}
		}
		
		if(yOfToMove<=towerlimit)	
		{
			toMoveValue = towers[fromTower][yOfToMove];
		}
		else
		{
			toMoveValue = FREE_SPACE;
			illigalOperation = true;
		}
		
		while(yOfTarget<=towerlimit && !targetFound)
		{
			if(towers[toTower][yOfTarget] == FREE_SPACE) 
			{	
				yOfTarget++;
			}
			else 
			{
				targetFound = true;
			}
		}
		
		if(yOfTarget<=towerlimit)
		{
			targetValue = towers[toTower][yOfTarget];
		}
		else
		{
			targetValue = FREE_SPACE;
		}
		yOfTarget--;
		
		if(targetValue != FREE_SPACE && targetValue < toMoveValue)
		{
			illigalOperation = true;
		}
		
		if(!illigalOperation)
		{
			towers[toTower][yOfTarget] = towers[fromTower][yOfToMove];
			towers[fromTower][yOfToMove] = FREE_SPACE;
			status = "All clear";
		}
		else
		{
			status = "Illigal operation";
		}
	}	
}
