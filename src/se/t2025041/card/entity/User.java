package se.t2025041.card.entity;

import java.util.ArrayList;

/**
 * ポーカーをプレイするユーザクラス
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class User extends Player {
	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規ユーザーを作成する．
	 * 
	 * @param name ：名前
	 */
	public User(String name) {
		super(name);
	}

	/* ---------------メソッド群----------------- */
	@Override
	public int select() {
		System.out.println("交換するカードを入力してください．");
		showHand();
		ArrayList<Integer> log = new ArrayList<Integer>();
		int n;
		int select = 0;
		for (int i = 0; i < hand.size(); i++) {
			do {
				System.out.print((i + 1) + "枚目（0で選択終了）：");
				n = Keyboard.inputNumber();
				if (n == 0) {
					System.out.println("");
					return select;
				} else if (n < 0) {
					System.err.println("0以上の数字を入力してください．");
				} else if (n > 5) {
					System.err.println("5以下の数字を入力してください．");
				} else if (log.contains(n)) {
					System.err.println("そのカードは選択済みです．");
				} else {
					select += (int) Math.pow(2, n - 1);
				}
			} while (n < 0 || n > 5 || log.contains(n));
			log.add(n);
		}
		System.out.println("");
		return select;
	}
}
