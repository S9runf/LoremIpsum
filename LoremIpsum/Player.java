package LoremIpsum;

import mnkgame.*;

public class Player implements MNKPlayer {

	//local board calculates zobrist key for each state of the board
	private Board B;

	//Win state
	private MNKGameState myWin;

	//Loss state
	private MNKGameState yourWin;

	//Transposition table
	private TranspositionTable tTable;

	//Time limit
	private int TIMEOUT;

	public Player()
	{}

	public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		B       = new Board(M, N, K, first);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2;
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		tTable = new TranspositionTable(100000007);
		TIMEOUT = timeout_in_secs;
	}
	
	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC){
		//keep track of starting time
		long start = System.currentTimeMillis();

		if(MC.length > 0) {
			MNKCell c = MC[MC.length-1]; // Recover the last move from MC
			B.markCell(c.i,c.j);         // Save the last move in the local MNKBoard
		}

		//if there is only one free cell, return it
		if(FC.length == 1){
			return FC[0];
		}

		//best move for this state
		MNKCell bestMove = null;
		//best score for this state
		int bestScore = Integer.MIN_VALUE;

		//alpha and beta values for alpha-beta pruning
		int alpha = -1000000;
		int beta = 1000000;

		//value of last analyzed move
		Integer val;
		
		//iterative deepening loop
		//iterates until time limit is over or until max depth is reached
		for(int depth = 0; depth <= FC.length-1; depth++){

			//check best move found in last iteration first
			if(bestMove != null){
				B.markCell(bestMove.i, bestMove.j);
				//recalculate legal moves
				FC = B.getFreeCells();
				//calculate score
				val  = alphaBeta(FC, false, alpha, beta, depth, start);

				//if score isn't null, there is still time, update best score
				if (val != null){
					bestScore = val;
					//cutoff if score is greater than beta
					if(bestScore > beta)
						return bestMove;
				}
				//if time is over, return last best move
				else 
					return bestMove;

				B.unmarkCell();

					
			}
			//check all other legal moves
			for(MNKCell c : FC){
				//if time is up exit loop
				if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(97.0/100.0))
					break;
				//mark cell on local board and update key
				B.markCell(c.i, c.j);
				
				//calculate score of this move
				val = alphaBeta(B.getFreeCells(), false, alpha, beta, depth, start);
				//restore key and unmark cell
				B.unmarkCell();

				//if time is up exit loop
				if(val == null)
					break;

				//if this move is better than the best move found so far, update best move
				else if(val > bestScore){
					bestScore = val;
					bestMove = c;
				}
			}
			//if time is up exit loop
			if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(97.0/100.0))
				break;	
		}
		//mark best move on local board and return it
		B.markCell(bestMove.i, bestMove.j);
		return bestMove;
	
	}

	public Integer alphaBeta(MNKCell[] FC, boolean myTurn, int alpha, int beta, int depth, long start){
		//if time is almost up, return null
		if((System.currentTimeMillis()-start)/1000.0 > TIMEOUT*(97.0/100.0)){
			return null;
		}
		//move to be tried first in this iteration
		MNKCell move = null;

		//get contents of the position in  the transposition table corresponding to the current state
		Entry entry = tTable.getEntry(B.getKey());

		//check if the current state is present in the table
		if(entry != null && entry.key == B.getKey()){
			//if the current depth is lower or equal to the depth stored in the table the score can be used
			if(entry.depth >= depth){
				//check type of score stored in the table
				switch(entry.bound){
					//if the score saved is a lower bound, update alpha
					case LOWER_BOUND: 
						alpha = Math.max(alpha, entry.score);
						break;
					//if the score saved is an upper bound, update beta
					case UPPER_BOUND:
						beta = Math.min(beta, entry.score);
						break;
					//if the score saved is an exact score, return it
					case EXACT:
						return entry.score;
				}
			}
			//best move found in last iteration for this state
			move = entry.bestMove;
		}

		//best score for this state
		Integer eval;
		//type of score saved in the table
		BoundType bound;
		//if game is over ot max depth was reached for this iteration, check current state of the board
		if(B.gameState() != MNKGameState.OPEN || depth == 0){
			eval =  evaluate(depth);
			//if score is greater than beta it's a lower bond for this subtree
			if(eval >= beta)
			bound = BoundType.LOWER_BOUND;
			//if sccore is between alpha and beta it's an exact score for this subtree
			else if(eval >= alpha)
				bound = BoundType.EXACT;
			//if score is less than alpha then it's an upper bound for this subtree
			else 
				bound = BoundType.UPPER_BOUND;

			//insert state in the table and return the score
			tTable.insert(B.getKey(), eval, bound, depth, move);
			return eval;
		}
		else{
			if(myTurn){
				//if alpha is never updated the score is an upper bound for this subtree
				bound = BoundType.UPPER_BOUND;
				eval = Integer.MIN_VALUE;
				//explore best move first if there is one
				if(move != null){
					B.markCell(move.i, move.j);
					FC = B.getFreeCells();
					//best score = best move's score
					eval = alphaBeta(FC, false, alpha, beta, depth - 1, start);
					B.unmarkCell();

					//if time is up, return null
					if(eval == null)
						return null;

					//cutoff if score is greater  or equal to beta
					if(eval >= beta){
						//save state as lower bound in the table and return the score
						tTable.insert(B.getKey(), eval, BoundType.LOWER_BOUND, depth, move);
						return eval;
					}
					//update alpha and set score as exact
					if(eval > alpha){
						bound = BoundType.EXACT;
						alpha = eval;
					}
					
				}
				//explore all other moves
				for(MNKCell c : FC){
					B.markCell(c.i, c.j);
					Integer val = alphaBeta(B.getFreeCells(), false, alpha, beta, depth-1, start);
					B.unmarkCell();

					//if time is up, return null
					if (val == null)
						return val;
					
					//update best score and move
					if(val > eval){
						eval = val;
						move = c;
					}
					if(eval > alpha){
						bound = BoundType.EXACT;
						alpha = eval;
					}
					//cutoff
					if(beta <= alpha){
						tTable.insert(B.getKey(), eval, BoundType.LOWER_BOUND, depth, move);
						return eval;
					}
				}
				
			}
			else{
				//if beta is never updated the score is a lower bound for this subtree
				bound = BoundType.LOWER_BOUND;
				eval = Integer.MAX_VALUE;
				//explore best move first if there is one
				if(move != null){
					B.markCell(move.i, move.j);
					FC = B.getFreeCells();
					eval = alphaBeta(FC, true, alpha, beta, depth - 1, start);
					B.unmarkCell();

					if(eval == null)
						return null;
					//cutoff if score is less or equal to alpha
					if(eval <= alpha){
						//save state as upper bound in the table and return the score
						tTable.insert(B.getKey(), eval, BoundType.UPPER_BOUND, depth, move);
						return eval;
					}
					//update alpha and set score as exact
					if(eval < beta){
						bound = BoundType.EXACT;
						beta = eval;
					}
				}
				//explore all other moves
				for(MNKCell c : FC){
					B.markCell(c.i, c.j);
					Integer val = alphaBeta(B.getFreeCells(), true, alpha, beta, depth-1, start);
					B.unmarkCell();

					//if time is up, return null
					if (val == null)
						return val;

					//update best score and move
					if(val < eval){
						eval = val;
						move = c;
					}

					if(eval < beta){
						bound = BoundType.EXACT;
						beta = eval;
					}
					//cutoff
					if(beta <= alpha){
						tTable.insert(B.getKey(), eval, BoundType.UPPER_BOUND, depth, move);
						return eval;
					}
				}
			}
			tTable.insert(B.getKey(), eval, bound, depth, move);
			return eval;
		}
	}

	public int evaluate(int depth){
		//if player won return winning evaluation
		if(B.gameState() == myWin)
			//better evaluation if win is closer to root node
			return 1000000 + depth;
		//if opponent won return losing evaluation
		else if(B.gameState() == yourWin)
			//worse evaluation if loss is closer to root node
			return -1000000 - depth;
		//if game is drawn return 0
		else if (B.gameState() != MNKGameState.OPEN)
			return 0;
		//if maximum depth for this iteration was reached evaluate the board
		else 
			return B.evaluateBoard();

	}
	
	public String playerName() {
		return "LoremIpsum";
	}
}

	
