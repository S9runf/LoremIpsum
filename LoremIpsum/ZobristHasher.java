package LoremIpsum;

import java.util.Random;
import mnkgame.MNKCellState;

/* Zobrist Hasher
 * generates a random number for each cell of the board
 * and computes the zobrist key for each state of the board
 */

public class ZobristHasher {

	//game board dimensions
	int M;
	int N;

	//table containing random numbers for every cell in the board in every possible occupied state
	long table[][][];

	public ZobristHasher(int m, int n){
		M = m;
		N = n;
		table = new long [m][n][2];
		Random rand = new Random();
		//fills table with random 64 bit numbers
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				table[i][j][0] = rand.nextLong(); 
				table[i][j][1] = rand.nextLong(); 
			}

		}
	}

	//uses XOR operator on old key with last move made on board to calculate new key
	public long computeKey(long key, int i, int j, MNKCellState state){
	
		if(state == MNKCellState.FREE)
			throw new IllegalArgumentException("Cannot compute key for free cell");	
		int player = state == MNKCellState.P1 ? 0 : 1;
		key ^= table[i][j][player];
		return key;
	}
}