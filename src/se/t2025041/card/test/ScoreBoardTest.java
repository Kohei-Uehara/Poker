package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.ScoreBoard;

/**
 * スコアボードのテスト
 */
public class ScoreBoardTest extends TestCase {
	ScoreBoard score;


	protected void setUp() throws Exception {
		super.setUp();
		score = new ScoreBoard("テスト");
		score.setChips(10);
		score.setNumberOfBattles(1);
		score.setNumberOfWins(1);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testUpdateChips() {
		score.updateChips(0);
		assertEquals(0, score.getChips());
		score.updateChips(-1);
		assertEquals(-1, score.getChips());
	}
	
	public void testUpdateScore() {
		score.updateScore(false);
		double s = score.getScore();
		score.updateScore(true);
		assertEquals(2*s, score.getScore());
	}
	
	public void testUpdateWinrRate() {
		score.updateWinRate(false);
		assertEquals(0.5, score.getWinRate());
		score.updateWinRate(true);
		assertEquals(2.0/3.0, score.getWinRate());
	}
	
	public void testUpdateStrongestHand() {
		score.updateStrongestHand(10);
		assertEquals(10, score.getStrongestHand());
		score.updateStrongestHand(10);
		assertEquals(10, score.getStrongestHand());
		score.updateStrongestHand(9);
		assertEquals(10, score.getStrongestHand());
	}

	public void testUpdate() {
		score.update(10, 0, false, false);
		assertEquals(0.5, score.getWinRate());
		assertEquals(10, score.getChips());
		
		score.update(20, 0, true, false);
		assertEquals(2.0/3.0, score.getWinRate());
		assertEquals(20, score.getChips());
		
		score.update(20, 10, true, false);
		assertEquals(10, score.getStrongestHand());
		
		score.update(20, 10, true, false);
		assertEquals(10, score.getStrongestHand());
		
		score.update(20, 9, true, false);
		assertEquals(10, score.getStrongestHand());
	}

	public void testToString() {
		assertTrue(score.toString().matches("....-..-..\\s..:..:..,.*,.*,.*,.*,.*"));
		
	}

}
