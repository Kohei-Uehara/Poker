package se.t2025041.card.entity;

/**
 * ポーカーをプレイするCPUクラス
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 */
public class CPU extends Player {
	/* ---------------フィールド群---------------- */
	/** 気分（0.0~1.0 の値をとる．交換するカードの選択に影響） */
	private double mood;
	/** 人格（主に台詞を決める） */
	private CPUsCharacter character;

	/* ---------------コンストラクタ----------------- */
	/**
	 * CPUを作成する．
	 * 
	 * @param name     ：名前
	 * @param caracter ：人格
	 * @param mood     ：気分の初期状態
	 */
	public CPU(String name, CPUsCharacter caracter, double mood) {
		super(name);
		this.character = caracter;
		setMood(mood);
	}

	/* ---------------メソッド群----------------- */
	@Override
	public int select() {
		double evaluationValue;
		double maxEvaluationValue = 0;
		int maxId = 0;
		for (int i = 0; i < (int) Math.pow(2, hand.size()); i++) {
			evaluationValue = PokerHand.evaluate(hand, i, mood);
			if (evaluationValue > maxEvaluationValue) {
				maxEvaluationValue = evaluationValue;
				maxId = i;
			}
		}
		int selection = maxId;
		int count = 0;
		while (maxId != 0) {
			if ((maxId & 1) == 1) {
				count++;
			}
			maxId = maxId >>> 1;
		}
		speakWhenSelecting(count);
		return selection;
	}

	/**
	 * 勝つと積極的になる
	 */
	@Override
	public void win() {
		super.win();
		setMood(mood + character.getTemper());
	}

	/**
	 * 負けると慎重になる
	 */
	@Override
	public void lose() {
		super.lose();
		setMood(mood - character.getTemper());
	}

	/**
	 * 煽てるコマンドに対する反応．積極的になる．
	 */
	public void reactToFlattery() {
		createMessageBox(character.makeLineToFlattery());
		setMood(mood + 0.05);
	}

	/**
	 * 挑発コマンドに対する反応．慎重になる．
	 */
	public void reactToProvocation() {
		createMessageBox(character.makeLineToProvocation());
		setMood(mood - 0.05);
	}

	/**
	 * 観察コマンドに対する反応．手札の強さによって反応が変化する．
	 */
	public void reactToObservation() {
		createMessageBox(character.makeLineToObservation(getHandStrength()));
	}

	/**
	 * ベット時に発言する．
	 * 
	 * @param seat ：プレイヤ（席）の状態
	 */
	public void speakWhenBetting(PlayersSeat seat) {
		createMessageBox(character.makeLineWhenBetting(seat));
	}

	/**
	 * カード交換時に発言する．
	 * 
	 * @param n ：交換枚数
	 */
	public void speakWhenSelecting(int n) {
		createMessageBox(character.makeLineWhenSelecting(n));
	}

	/**
	 * レイズ・フォールド選択時に発言する．
	 * 
	 * @param seat ：プレイヤ（席）の状態
	 */
	public void speakWhenDetarmining(PlayersSeat seat) {
		createMessageBox(character.makeLineWhenDetarmining(seat));
	}

	/**
	 * 手札公開時に発言する．
	 */
	public void speakWhenOpeningHand() {
		createMessageBox(character.makeLineWhenOpeningHand());
	}

	/**
	 * （ディーラー時）ゲーム開始時に発言する．
	 */
	public void welcome() {
		createMessageBox(character.makeLineToWelcome());
	}

	/**
	 * （ディーラー時）ベットするチップの枚数を尋ねる．
	 * @param seat ：プレイヤ（席）の状態
	 */
	public void proceedBettingPhase(PlayersSeat seat) {
		createMessageBox(character.makeLineToProceedBettingPhase(seat));
	}

	/**
	 * （ディーラー時）ベットフェイズにて，誰もチップを持っていない時に発言する．
	 */
	public void proceedWhenEveryoneCannotBet() {
		createMessageBox(character.makeLineToProceedWhenEveryoneCannnotBet());
	}

	/**
	 * （ディーラー時）ディールフェイズ開始時に発言する．
	 */
	public void proceedDealingPhase() {
		createMessageBox(character.makeLineToProceedDeelingPhase());
	}

	/**
	 * （ディーラー時）交換フェイズ開始時に発言する．
	 */
	public void proceedSelectingPhase() {
		createMessageBox(character.makeLineToProceedSelectingPhase());
	}

	/**
	 * （ディーラー時）決断フェイズ開始時に発言する．
	 */
	public void proceedDeterminingPhase() {
		createMessageBox(character.makeLineToProceedDeterminingPhase());
	}

	/**
	 * （ディーラー時）勝負フェイズ開始時に発言する．
	 */
	public void proceedBattlePhase() {
		createMessageBox(character.makeLineToProceedBattlePhase());
	}

	/* ---------------アクセサ----------------- */
	/**
	 * 気分を設定する．
	 * 
	 * @param mood ：気分
	 */
	private void setMood(double mood) {
		this.mood = mood;
		if (this.mood > 1.0) {
			this.mood = 1.0;
		} else if (this.mood < 0) {
			this.mood = 0.0;
		}
	}
	
	/**
	 * 設定されたキャラクターを取得する．
	 * 
	 * @return キャラクター
	 */
	public CPUsCharacter getCharacter() {
		return character;
	}

	/**
	 * 気分を取得する．
	 * 
	 * @return 気分
	 */
	public double getMood() {
		return mood;
	}
}
