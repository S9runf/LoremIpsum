package LoremIpsum;

import mnkgame.MNKCell;

//entry in the transposition table representing a state of the board

public class Entry {

	//key of the state
	public long key;

	//score of the state
	public int score;

	//type of score savede in the entry
	public BoundType bound;

	//depth the score was computed at
	public int depth;

	//best move for this state from last iteration
	public MNKCell bestMove;

	public Entry(long k, int points, BoundType b, int d, MNKCell move){
		key = k;
		score = points;
		bound = b;
		depth = d;
		bestMove = move;
	}

	
}
