package se.t2025041.card.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.CardDeck;

/**
 * トランプゲームの山札クラスのテストクラス
 */
public class CardDeckTest extends TestCase {
	private CardDeck deck;
	private Card spadeA, diamond10, heartQ, clubK, joker;

	protected void setUp() throws Exception {
		super.setUp();
		deck = new CardDeck();
		spadeA = new Card(0, 1); // スペードA
		diamond10 = new Card(1, 10); // ダイヤ10
		heartQ = new Card(2, 12); // ハートQ
		clubK = new Card(3, 13); // クラブK
		joker = new Card(-1, 0); // ジョーカー
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateFullDeck() {
		deck.createFullDeck();
		assertEquals(deck.getAllCards().size(), 52);
		int spade = 0, heart = 0, club = 0, diamond = 0;
		for (Card card : deck.getAllCards()) {
			switch (card.getSuit()) {
			case 0:
				spade++;
				break;
			case 1:
				diamond++;
				break;
			case 2:
				heart++;
				break;
			case 3:
				club++;
				break;
			}
		}
		assertTrue(spade == 13);
		assertTrue(diamond == 13);
		assertTrue(heart == 13);
		assertTrue(club == 13);
	}

	public void testClear() {
		deck.createFullDeck();
		deck.clear();
		assertEquals(0, deck.getAllCards().size());
	}

	public void testShuffle() {
		for (int i = 0; i < 3; i++) {
			deck.createFullDeck();
			ArrayList<Card> cards = new ArrayList<Card>(deck.getAllCards());
			deck.shuffle();
			int count = 0;
			for (int j = 0; j < deck.size(); j++) {
				if (cards.get(j).getNumber() == deck.getAllCards().get(j).getNumber()) {
					count++;
				}
			}
			assertTrue(count < 52);
		}
	}

	public void testAddCardCard() {
		deck.addCard(spadeA);
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(1, deck.size());
		
		deck.addCard(diamond10);
		assertSame(diamond10, deck.seeCard(2));
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(2, deck.size());
		
		deck.addCard(heartQ);
		deck.addCard(clubK);
		deck.addCard(joker);
		assertSame(joker, deck.seeCard(5));
		assertSame(clubK, deck.seeCard(4));
		assertSame(heartQ, deck.seeCard(3));
		assertSame(diamond10, deck.seeCard(2));
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(5, deck.size());
	}

	public void testAddCardIntCard() {
		deck.addCard(1, spadeA);
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(1, deck.size());
		
		deck.addCard(2, diamond10);
		assertSame(diamond10, deck.seeCard(2));
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(2, deck.size());
		
		deck.addCard(2, heartQ);
		assertSame(diamond10, deck.seeCard(3));
		assertSame(heartQ, deck.seeCard(2));
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(3, deck.size());
		
		deck.addCard(5, clubK);
		assertSame(diamond10, deck.seeCard(3));
		assertSame(heartQ, deck.seeCard(2));
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(3, deck.size());
		assertEquals(0, deck.searchCard(3, 13));
		
		deck.addCard(0, clubK);
		assertSame(diamond10, deck.seeCard(3));
		assertSame(heartQ, deck.seeCard(2));
		assertSame(spadeA, deck.seeCard(1));
		assertEquals(3, deck.size());
		assertEquals(0, deck.searchCard(3, 13));
	}

	public void testTakeCard() {
		deck.addCard(spadeA);
		deck.addCard(diamond10);
		deck.addCard(heartQ);
		deck.addCard(clubK);
		deck.addCard(joker);
		assertEquals(5, deck.size());
		
		assertSame(spadeA, deck.takeCard());
		assertEquals(4, deck.size());
		
		assertSame(diamond10, deck.takeCard());
		assertEquals(3, deck.size());
		
		assertSame(heartQ, deck.takeCard());
		assertEquals(2, deck.size());
		
		assertSame(clubK, deck.takeCard());
		assertEquals(1, deck.size());
		
		assertSame(joker, deck.takeCard());
		assertEquals(0, deck.size());
		
		assertNull(deck.takeCard());
	}

	public void testTakeCardInt() {
		deck.addCard(spadeA);
		deck.addCard(diamond10);
		deck.addCard(heartQ);
		deck.addCard(clubK);
		deck.addCard(joker);
		
		assertSame(spadeA, deck.takeCard(1));
		assertEquals(4, deck.size());
		
		assertSame(joker, deck.takeCard(4));
		assertEquals(3, deck.size());
		
		assertSame(heartQ, deck.takeCard(2));
		assertEquals(2, deck.size());
		
		assertSame(clubK, deck.takeCard(2));
		assertEquals(1, deck.size());
		
		assertNull(deck.takeCard(2));
		assertNull(deck.takeCard(0));
		
		assertSame(diamond10, deck.takeCard(1));
		assertEquals(0, deck.size());
	}

	public void testSeeCard() {
		deck.addCard(spadeA);
		deck.addCard(diamond10);
		deck.addCard(heartQ);
		deck.addCard(clubK);
		deck.addCard(joker);
		
		assertSame(spadeA, deck.seeCard(1));
		assertSame(diamond10, deck.seeCard(2));
		assertSame(heartQ, deck.seeCard(3));
		assertSame(clubK, deck.seeCard(4));
		assertSame(joker, deck.seeCard(5));
	}

	public void testSearchCard() {
		deck.addCard(spadeA);
		deck.addCard(diamond10);
		deck.addCard(heartQ);
		deck.addCard(clubK);
		deck.addCard(joker);
		
		assertEquals(1, deck.searchCard(0, 1));
		assertEquals(2, deck.searchCard(1, 10));
		assertEquals(3, deck.searchCard(2, 12));
		assertEquals(4, deck.searchCard(3, 13));
		assertEquals(5, deck.searchCard(-1, 0));
		assertEquals(0, deck.searchCard(3, 10));
		assertEquals(0, deck.searchCard(-1, 10));
	}

	public void testIsEmpty() {
		deck.createFullDeck();
		assertFalse(deck.isEmpty());
		deck.clear();
		assertTrue(deck.isEmpty());
	}

	public void testSize() {
		deck.clear();
		assertEquals(0, deck.size());
		deck.addCard(spadeA);
		deck.addCard(diamond10);
		deck.addCard(heartQ);
		deck.addCard(clubK);
		deck.addCard(joker);
		assertEquals(5, deck.size());
	}

	public void testGetAllCards() {
		assertNotNull(deck.getAllCards());
	}

}
