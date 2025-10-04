package se.t2025041.card.entity;

/**
 * ポーカーテーブルの座席．ゲームの参加者をセットする．<br>
 * ディーラーにはできない行動をさせるためのアダプタ．
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class PlayersSeat {
	/* ---------------フィールド群---------------- */
	/** プレイヤ */
	private Player player;
	/** 所有チップ */
	private int chips;
	/** 賭けているチップ */
	private int bet;
	/** レイズしているかどうか */
	private boolean isRaise;
	/** 成績 */
	private ScoreBoard score;
	/** 最初に配られるチップの枚数 */
	private static int NUMBER_OF_FIRST_CHIPS = 10;

	/* ---------------コンストラクタ----------------- */
	/**
	 * 新たにポーカーの座席を用意する．
	 * 
	 * @param player ：プレイヤ
	 */
	public PlayersSeat(Player player) {
		this.player = player;
		chips = NUMBER_OF_FIRST_CHIPS;
		bet = 0;
		score = new ScoreBoard(player.getName());
	}

	/* ---------------メソッド群----------------- */
	/**
	 * 賭けるチップの枚数を決める．
	 */
	public void bet() {
		if (chips <= 0) {
			System.err.println("エラー：この行動は1枚以上のチップを持つプレイヤーのみが可能です．");
			return;
		}
		if (player instanceof User) {
			int n;
			do {
				System.out.print("賭ける枚数を入力：");
				n = Keyboard.inputNumber();
				if (n > chips) {
					System.out.println("所有するチップの枚数を超えて賭けることはできません！！");
				}
				if (n < 1) {
					System.out.println("1以上の数字を入力してください．");
				}

			} while (n > chips || n < 1);
			chips -= n;
			bet = n;
			System.out.println("");
		} else {
			CPU cpu = (CPU) player;
			if (chips > NUMBER_OF_FIRST_CHIPS) {
				chips -= 4;
				bet = 4;
			} else {
				if (chips >= 2) {
					chips -= 2;
					bet = 2;
				} else {
					chips -= 1;
					bet = 1;
				}
			}
			cpu.speakWhenBetting(this);
		}
	}
	
	/**
	 * レイズ状態をリセットする．
	 */
	public void resetIsRaise() {
		isRaise = false;
	}

	/**
	 * レイズ・フォールドを決める．
	 * 
	 * @return レイズ・フォールド
	 */
	public boolean determin() {
		isRaise = false;
		if (player instanceof User) {
			System.out.println("レイズ・フォールドを宣言してください．");
			System.out.println("--------------------------------");
			System.out.println("1.レイズ");
			System.out.println("2.フォールド");
			System.out.println("--------------------------------");
			int n;
			do {
				System.out.print("数字を入力：");
				n = Keyboard.inputNumber();
				if (n < 1 || n > 2) {
					System.err.println("無効なコマンド：再入力してください．");
				} else if (n == 1) {
					System.out.println("\nレイズ！！");
					chips -= bet;
					bet *= 2;
					isRaise = true;
				} else {
					System.out.println("\nフォールド．");
					bet = 0;
					score.update(chips, 0, false, false);
				}
			} while (n < 0);
			System.out.println("");

		} else {
			CPU cpu = (CPU) player;
			if (chips < 3) {
				chips -= bet;
				bet *= 2;
				isRaise = true;
			} else if (cpu.getHandStrength() > 1) {
				chips -= bet;
				bet *= 2;
				isRaise = true;
			} else {
				bet = 0;
				score.update(chips, 0, false, false);
			}
			cpu.speakWhenDetarmining(this);
		}
		return isRaise;
	}

	/**
	 * 勝つ
	 */
	public void win() {
		player.win();
		chips += 2 * bet;
		bet = 0;
		score.update(chips, player.getHandStrength(), true, false);
	}

	/**
	 * 負ける
	 */
	public void lose() {
		player.lose();
		bet = 0;
		score.update(chips, player.getHandStrength(), false, false);
	}

	/**
	 * 引き分ける
	 */
	public void draw() {
		player.draw();
		chips += bet;
		bet = 0;
		score.update(chips, player.getHandStrength(), false, false);
	}

	/* ---------------アクセサ----------------- */
	/**
	 * 成績を取得する．
	 * 
	 * @return 成績データ
	 */
	public ScoreBoard getScore() {
		return score;
	}

	/**
	 * チップをセットする．
	 * 
	 * @param chips ：チップの枚数
	 */
	public void setChips(int chips) {
		this.chips = chips;
	}

	/**
	 * 最初のチップの枚数をセットする．
	 * 
	 * @param n ：最初のチップの枚数
	 */
	public static void setNUMBER_OF_FIRST_CHIPS(int n) {
		if (n <= 0) {
			System.err.println("チップの初期値には0以上の値を設定してください．");
		}
		NUMBER_OF_FIRST_CHIPS = n;
	}

	/**
	 * セットされた最初のチップの枚数を取得する．<br>
	 * デフォルトで 10 に設定済み
	 * 
	 * @return セットされた最初のチップの枚数
	 */
	public static int getNUMBER_OF_FIRST_CHIPS() {
		return NUMBER_OF_FIRST_CHIPS;
	}

	/**
	 * 所有するチップの枚数を取得する．
	 * 
	 * @return
	 */
	public int getChips() {
		return chips;
	}

	/**
	 * プレイヤを取得する．
	 * 
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * かけているチップの枚数を取得する．
	 * 
	 * @return bet
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * レイズしているかどうかを取得する．
	 * 
	 * @return isRaise
	 */
	public boolean isRaise() {
		return isRaise;
	}
}
