package se.t2025041.card.entity;

/**
 * 手札評価用のダミーカードクラス
 * 
 * @author Kohei Uehara
 * @version 1.0, 202-07-22
 * @since JDK17
 *
 */
public class DummyCard extends Card {
	/**
	 * ダミーカードの生成
	 * 
	 * @param suit   絵柄（内部値）
	 * @param number 数字（内部値）
	 */
	public DummyCard(int suit, int number) {
		super(suit, number);
	}

	@Override
	public String toString() {
		return "ダミー";
	}

	/**
	 * ダミーの絵柄を返す．
	 * 
	 * @return ダミーの絵柄
	 */
	@Override
	public int getSuit() {
		return -2;
	}

	/**
	 * ダミーの数字を返す．
	 * 
	 * @return ダミーの数字
	 */
	@Override
	public int getNumber() {
		return 0;
	}
}
