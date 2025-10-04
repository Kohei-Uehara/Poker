package se.t2025041.card.entity;

import java.util.ArrayList;

/**
 * プレイヤーを表す抽象クラス．
 * 
 * @author Juzo
 *
 */
public abstract class Player {
	/* ---------------フィールド群---------------- */
	/** 名前 */
	protected String name;
	/** 手札 */
	protected ArrayList<Card> hand;

	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規プレイヤを作成する．
	 * 
	 * @param name ：名前
	 */
	public Player(String name) {
		setName(name);
		hand = new ArrayList<Card>();
	}

	/* ---------------メソッド群----------------- */
	/**
	 * 手札をみて，どのカードを交換するかを返す．<br>
	 * 返り値は二進数になっており，例えば<br>
	 * <br>
	 * 00011<br>
	 * <br>
	 * なら，1枚目と2枚目を交換する．
	 * 
	 * @return 交換するカードの組み合わせ
	 */
	public abstract int select();

	/**
	 * 勝ったときの後処理をする．
	 */
	public void win() {
		System.out.println(name + "は勝利！");
	}

	/**
	 * 負けときの後処理をする．
	 */
	public void lose() {
		System.out.println(name + "は敗北．");
	}

	/**
	 * 引き分けたときの後処理をする．
	 */
	public void draw() {
		System.out.println(name + "は引き分け．");
	}

	/**
	 * 手札を表示する．
	 */
	public void showHand() {
		System.out.println("--------------------------------");
		System.out.println(name + " の手札");
		System.out.println("--------------------------------");
		for (int i = 1; i <= hand.size(); i++) {
			System.out.printf("%d. ", i);
			System.out.println(hand.get(i - 1));
		}
		System.out.println("--------------------------------");
		Keyboard.sleep();
	}

	/**
	 * 手札を加える．
	 * 
	 * @param card ：カード
	 */
	public void addCard(Card card) {
		hand.add(card);
	}

	/**
	 * 手札の i 番目を交換する．
	 * 
	 * @param i    ：交換する手札
	 * @param card ：交換するカード
	 */
	public void changeCard(int i, Card card) {
		if (i >= hand.size() || i < 0) {
			System.err.println("そのカードはありません．");
		} else {
			hand.set(i, card);
		}
	}

	/**
	 * 手札を空にする．
	 */
	public void clearHand() {
		hand.clear();
	}

	/**
	 * メッセージボックスを作成<br>
	 * <br>
	 * 書式：<br>
	 * 名前「<br>
	 * メッセージの内容<br>
	 * 
	 * @param message ：メッセージの内容
	 */
	public void createMessageBox(String message) {
		System.out.println(name + "「");
		System.out.println("　" + message);
		Keyboard.sleep();
	}

	/* ---------------アクセサ----------------- */
	/**
	 * 名前を取得する．
	 * 
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名前をセットする．
	 * 
	 * @param name ：名前
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 役を取得する．
	 * 
	 * @return 役
	 */
	public int getHandStrength() {
		return PokerHand.match(hand);
	}

	/**
	 * 手札を取得する．
	 * 
	 * @return 手札
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}
}
