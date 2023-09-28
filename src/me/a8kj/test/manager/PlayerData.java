package me.a8kj.test.manager;

public class PlayerData {

	private int brokenBlocksCount;
	private int placedBlocksCount;

	public PlayerData() {
		setBrokenBlocksCount(0);
		setPlacedBlocksCount(0);

	}

	public PlayerData setBrokenBlocksCount(int brokenBocks) {
		this.brokenBlocksCount = brokenBocks;
		return this;
	}

	public PlayerData setPlacedBlocksCount(int placedBlocks) {
		this.placedBlocksCount = placedBlocks;
		return this;
	}

	public int getBrokenBlocksCount() {
		return brokenBlocksCount;
	}

	public int getPlacedBlocksCount() {
		return placedBlocksCount;
	}

	public void increaseBrokenBlocksCount() {
		brokenBlocksCount++;
	}

	public void increasePlacedBlocksCount() {
		placedBlocksCount++;
	}
	
	
}
