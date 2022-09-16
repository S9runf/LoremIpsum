package LoremIpsum;

import mnkgame.MNKCell;

public class TranspositionTable {

	private int size;
	private Entry[] table;

	public TranspositionTable(int size){
		this.size = size;
		table = new Entry[size];
 
	}

	//calculates the index for the entry in the table and inserts it in the table
	public void insert(long key, int score, BoundType bound, int depth, MNKCell move){
		int index = computeIndex(key);
		Entry oldEntry = table[index];
		//if index is already occupied replace it only if the new entry has been calculated by a deeper search
		if(oldEntry == null || oldEntry.depth <= depth){
			Entry newEntry = new Entry(key, score, bound, depth, move);
			table[index] = newEntry;
		}
	}

	//returns the entry in the table with the given key
	public Entry getEntry(long key){
		return table[computeIndex(key)];
		
	}

	//computes the index for the key
	public int computeIndex(long key){
		return (int) Math.abs(key % size);
	}


}
