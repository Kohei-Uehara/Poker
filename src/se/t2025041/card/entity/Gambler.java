package se.t2025041.card.entity;

/**
 * キャラクター：勝負師
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class Gambler extends CPUsCharacter {
	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規キャラクター：勝負師を作成
	 * 
	 * @param temper ：気分の変わりやすさ
	 */
	public Gambler(double temper) {
		super(temper);
	}

	/* ---------------メソッド群----------------- */
	@Override
	public String makeLineWhenBetting(PlayersSeat player) {
		int chips = player.getChips();
		int bet = player.getBet();
		int firstChips = PlayersSeat.getNUMBER_OF_FIRST_CHIPS();
		String message;
		if (chips + bet > firstChips) {
			message = String.format("たたみかける！%d枚かけるぜ！", bet);
		} else if (chips - bet == 0) {
			message = "ここで決める！全ベットだ！";
		} else if (chips > firstChips / 2) {
			message = String.format("ここは様子見だな．%d枚かけるぜ．", bet);
		} else {
			message = String.format("そろそろ本気を出すとするか．%d枚かけるぜ．", bet);
		}

		return message;
	}

	@Override
	public String makeLineWhenSelecting(int n) {
		String message;
		if (n == 0) {
			message = "ノーチェンジだ．";
		} else if (n < 3) {
			message = String.format("%d枚チェンジするぜ．", n);
		} else {
			message = String.format("...%d枚チェンジだ．", n);
		}
		return message;
	}

	@Override
	public String makeLineWhenDetarmining(PlayersSeat seat) {
		int chips = seat.getChips();
		boolean isRaise = seat.isRaise();
		String message;
		if (isRaise) {
			if (chips <= 0) {
				message = "レイズ！レイズだ！";
			} else {
				message = "レイズするぜ．";
			}
		} else {
			message = "焦る必要はない．おれは降りるぜ．";
		}
		return message;
	}

	@Override
	public String makeLineToFlattery() {
		return "おれはいつでも絶好調だぜ！";
	}

	@Override
	public String makeLineToProvocation() {
		return "けっ，今に見てな！";
	}

	@Override
	public String makeLineToObservation(int hand) {
		String message;
		if (hand > 26) {
			message = "おう，この勝負，もらったぜ．";
		} else {
			message = "へへっ，正々堂々戦おうぜ．";
		}
		return message;
	}

	/* ---------------ディーラーとしての台詞----------------- */
	@Override
	public String makeLineToWelcome() {
		return "ポーカーテーブルによくきたな．\n" + "　じゃあ，ゲームを始めようか．";
	}

	@Override
	public String makeLineToProceedBettingPhase(PlayersSeat seat) {
		String name = seat.getPlayer().getName();
		int chips = seat.getChips();
		String message;
		if (chips > 0) {
			message = String.format("%s のチップはいま%d枚だ．\n" + "　何枚のチップをかけるんだ？", name, chips);
		} else {
			message = String.format("なんだ，%s のチップはもうねえのか．\n" + "　他のやつらで続けよう．", name, chips);
		}
		return message;
	}

	@Override
	public String makeLineToProceedWhenEveryoneCannnotBet() {
		return "おいおい，みんなスっちまったのか？\n" + "　今回はおれの勝ちだ！じゃあな．";
	}

	@Override
	public String makeLineToProceedDeelingPhase() {
		return "カードを配るぞ．";
	}

	@Override
	public String makeLineToProceedSelectingPhase() {
		return "交換する札をえらびな．";
	}

	@Override
	public String makeLineToProceedDeterminingPhase() {
		return "さてと，皆様方．調子はどうだ？\n" + "　俺と勝負するか？";
	}

	@Override
	public String makeLineToProceedBattlePhase() {
		return "では勝負といこう．\n" + "　手札を公開するんだ！";
	}

	@Override
	public String makeLineWhenOpeningHand() {
		return "おれの手札は...";
	}
}
