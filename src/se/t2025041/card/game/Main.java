package se.t2025041.card.game;

/**
 * ポーカー（簡易版）のメインクラス
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-25
 * @since JDK17
 *
 */
public class Main {
	/**
	 * メイン関数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HomeCommand command = new HomeCommand("result.csv");
		command.showMenu();
	}
}
