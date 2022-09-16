package LoremIpsum;

import mnkgame.MNKBoard;
import mnkgame.MNKCell;
import mnkgame.MNKCellState;
import mnkgame.MNKGameState;

/* Local Board
 * calculates of the zobrist key for each state of the board 
 * and the scores for each state
 */

public class Board  extends MNKBoard{
	//zobrist key for this state
	long key;

	//zobrist hasher
	ZobristHasher hash;
	
	//Player's symbol
	MNKCellState mySymbol;

	public Board(int M, int N, int K, boolean first){
		super(M, N, K);
		mySymbol = first ? MNKCellState.P1 : MNKCellState.P2;
		hash = new ZobristHasher(M, N);
		key = 0;

	}

	public long getKey(){
		return key;
	}

	//marks the cell and updates the key
	public  MNKGameState markCell(int i, int j){
		MNKGameState res = super.markCell(i, j);
		key = hash.computeKey(key, i, j, B[i][j]);
		return res;

	}
	
	//unmarks the cell and reverts the key
	public void unmarkCell(){
		// Recover the last move from MC
		MNKCell c = MC.getLast(); 

		//use last move to revert key
		key = hash.computeKey(key, c.i, c.j, B[c.i][c.j]);

		super.unmarkCell();
		

	}

	//computes the score of the board
	public int evaluateBoard(){
		int score = 0;

		//check rows, columns and diagonals
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				if(i <= M - K)
					score += checkDown(i, j);
				if(j <= N - K)
					score += checkRight(i, j);
				if(i <= M - K && j <= N - K)
					score += checkDownRight(i, j);
				if(i >= K-1 && j <= N - K)
					score += checkUpRight(i, j);
			}
		}

		return score;
	}

	private int checkDown(int i, int j){
		int taken = 0;
		MNKCellState state, e1 = B[i][j], e2 = null, player = null;

		//check cell and all cells below it
		for(int k = 0; k <= K - 1; k++){
			state = B[i+k][j];
			//if no player has taken cells on this line
			if(player == null)
				//check if a player has taken the cell and set the player
				player = state != MNKCellState.FREE ? state : null;

			if(state != MNKCellState.FREE){
				//if the cell has been taken by the same player as the previous ones
				if(player == state){
					//increase the number of taken cells
					taken++;
					//set next cell as last cell in the line if it exists or the cell itself if it's the last cell to explore
					e2 = k < K - 1 ? (i+k+1 < M ?  B[i+k+1][j] : null) :  B[i+k][j];
				}
				//if the cell has been taken by the other player
				else if(player != state){
					//set taken cells to 0
					taken = 0;
					//set current cell as last in the line and exit the loop
					e2 = state;
					break;
				}
			}
		}

		//if the threat lenght is K-1
		if(taken == K - 1){
			//open threat
			if(e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
				//player's threat
				if(player == mySymbol)
					return 250;
				//opponent's threat
				else 
					return -5020;
			}
			//half-open threat
			else{
				if(player == mySymbol)
					return 80;
				else
					return -2000;
			}
		}
		//open K-2 threat
		else if(taken == K - 2 && e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
			//player's threat
			if(player == mySymbol)
				return 100;
			//opponent's threat
			else
				return -1300;
		}
		//threat lenght is less than K-2 or threat is half-open K-2
		else if(taken != 0){
			if(player == mySymbol)
					return taken;
				else 
					return -taken;
		}
		//there is no threat
		else 
			return 0;
	}

	private int checkRight(int i, int j){
		int taken = 0;
		MNKCellState state, e1 = B[i][j], e2 = null, player = null;

		//check cell and all cells to the right of it
		for(int k = 0; k <= K - 1; k++){
			state = B[i][j+k];
			if(player == null)
				player = state != MNKCellState.FREE ? state : null;

			if(state != MNKCellState.FREE){
				if(player == state){
					taken++;
					e2 = k < K - 1 ? (j+k+1 < N ?  B[i][j+k+1] : null) :  B[i][j+k];
				}
				else if(player != state){
					taken = 0;
					e2 = state;
					break;
				}
			}
		}
		if(taken == K - 1){
			if(e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
				if(player == mySymbol)
					return 250;
				else 
					return -5020;
			}
			else{
				if(player == mySymbol)
					return 80;
				else
					return -2000;
			}
		}
		else if(taken == K - 2 && e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
			if(player == mySymbol)
				return 100;
			else
				return -1300;
		}
		else if(taken != 0){
			if(player == mySymbol)
					return taken;
				else 
					return -taken;
		}
		else 
			return 0;



	}
	private int checkUpRight(int i, int j){
		int taken = 0;
		MNKCellState state, e1 = B[i][j], e2 = null, player = null;

		//check cell and all cells in the upper right diagonal
		for(int k = 0; k <= K - 1; k++){
			state = B[i-k][j+k];
			if(player == null)
				player = state != MNKCellState.FREE ? state : null;
			if(state != MNKCellState.FREE){
				if(player == state){
					taken++;
					e2 = k < K - 1 ? (i-k-1 >= 0 && j+k+1 < N ? B[i-k-1][j+k+1] : null) : B[i-k][j+k];
				}
				else if(player != state){
					taken = 0;
					e2 = state;
					break;
				}
			}
		}
		if(taken == K - 1){
			if(e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
				if(player == mySymbol)
					return 250;
				else 
					return -5020;
			}
			else{
				if(player == mySymbol)
					return 80;
				else
					return -2000;
			}
		}
		else if(taken == K - 2 && e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
			if(player == mySymbol)
				return 100;
			else
				return -1300;
		}
		else if(taken != 0){
			if(player == mySymbol)
					return taken;
				else 
					return -taken;
		}
		else 
			return 0;



	}
	private int checkDownRight(int i, int j){
		int taken = 0;
		MNKCellState state, e1 = B[i][j], e2 = null, player = null;

		//check cell and all cells in the lower right diagonal
		for(int k = 0; k <= K - 1; k++){
			state = B[i+k][j+k];
			if(player == null)
				player = state != MNKCellState.FREE ? state : null;

			if(state != MNKCellState.FREE){
				if(player == state){
					taken++;
					e2 = k < K - 1 ? ( i+k+1 < M  &&  j+k+1 < N ? B[i+k+1][j+k+1] : null) : B[i+k][j+k];
				}
				else if(player != state){
					taken = 0;
					e2 = state;
					break;
				}
			}
		}
		if(taken == K - 1){
			if(e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
				if(player == mySymbol)
					return 250;
				else 
					return -5020;
			}
			else{
				if(player == mySymbol)
					return 80;
				else
					return -2000;
			}
		}
		else if(taken == K - 2 && e1 == MNKCellState.FREE && e2 == MNKCellState.FREE){
			if(player == mySymbol)
				return 100;
			else
				return -1300;
		}
		else if(taken != 0){
			if(player == mySymbol)
					return taken;
				else 
					return -taken;
		}
		else 
			return 0;

	}

}
	