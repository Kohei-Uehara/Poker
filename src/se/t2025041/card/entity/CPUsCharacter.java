package se.t2025041.card.entity;

/**
 * CPUの人格クラス
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public abstract class CPUsCharacter {
	/* ---------------フィールド群---------------- */
	/** 気分の変わりやすさ */
	protected double temper;

	/* ---------------コンストラクタ----------------- */
	/**
	 * 新しいキャラクターを作成
	 * 
	 * @param temper ：気分の変わりやすさ
	 */
	public CPUsCharacter(double temper) {
		setTemper(temper);
	}

	/* ---------------プレイヤとしての台詞----------------- */
	/**
	 * ベット時の台詞を作成
	 * 
	 * @param seat ：プレイヤ（席）の状態
	 * @return 台詞
	 */
	public abstract String makeLineWhenBetting(PlayersSeat player);

	/**
	 * カード選択時の台詞を作成
	 * 
	 * @param n ：交換する手札の数
	 * @return 台詞
	 */
	public abstract String makeLineWhenSelecting(int n);

	/**
	 * レイズ・フォールド選択時の台詞を作成
	 * 
	 * @param seat ：プレイヤ（席）の状態
	 * @return 台詞
	 */
	public abstract String makeLineWhenDetarmining(PlayersSeat seat);

	/**
	 * カード公開時の台詞
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineWhenOpeningHand();

	/**
	 * 煽てるコマンドに対応する台詞を作成
	 * 
	 * @return 台詞
	 */
	public abstract String makeLineToFlattery();

	/**
	 * 挑発コマンドに対応する台詞を作成
	 * 
	 * @return 台詞
	 */
	public abstract String makeLineToProvocation();

	/**
	 * 観察コマンドに対応する台詞を作成
	 * 
	 * @param hand ：そろっている役
	 * @return 台詞
	 */
	public abstract String makeLineToObservation(int hand);

	/* ---------------ディーラーとしての台詞----------------- */
	/**
	 * ゲーム開始時の台詞を作成
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineToWelcome();

	/**
	 * ベットフェイズ進行時の台詞を作成
	 * 
	 * @param seat ：プレイヤ（席）の状態
	 * @return ：台詞
	 */
	public abstract String makeLineToProceedBettingPhase(PlayersSeat player);

	/**
	 * ベットフェイズ進行時，全員がチップを持っていなかった場合の台詞を作成
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineToProceedWhenEveryoneCannnotBet();

	/**
	 * ディールフェイズ進行時の台詞を作成
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineToProceedDeelingPhase();

	/**
	 * 交換フェイズ進行時の台詞を作成
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineToProceedSelectingPhase();

	/**
	 * 決断フェイズ進行時の台詞を作成
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineToProceedDeterminingPhase();

	/**
	 * 勝負フェイズ進行時の台詞を作成
	 * 
	 * @return ：台詞
	 */
	public abstract String makeLineToProceedBattlePhase();

	/* ---------------アクセサ----------------- */
	/**
	 * 気分の変わりやすさを設定する．±0.25以内
	 * 
	 * @param temper
	 */
	public void setTemper(double temper) {
		if (temper > 0.25) {
			this.temper = 0.25;
		} else if (temper < -0.25) {
			this.temper = -0.25;
		} else {
			this.temper = temper;
		}
	}

	/**
	 * 気分の変わりやすさを取得する．
	 * 
	 * @return 気分の変わりやすさ
	 */
	public double getTemper() {
		return temper;
	}
}
