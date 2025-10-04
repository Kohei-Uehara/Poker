package se.t2025041.card.game;

import java.util.ArrayList;

import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Player;
import se.t2025041.card.entity.PlayersSeat;
import se.t2025041.card.entity.User;

/**
 * 決断フェイズの処理を分担するサブクラス．
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-24
 * @since JDK17
 */
public class DeterminationCommand {
	/** ベットしたプレイヤ（0:ユーザ） */
	private ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * レイズ・フォールドを決めさせる．
	 * 
	 * @param seats 席
	 */
	public void determin(ArrayList<PlayersSeat> seats) {
		for (PlayersSeat seat : seats) {
			if (seat.getBet() > 0) {
				seat.determin();
			}
		}
	}

	/**
	 * 観察コマンドの内容を実装
	 */
	private void observe() {
		System.out.println("相手を選んでください．");
		System.out.println("----------------");
		for (int i = 1; i < players.size(); i++) {
			System.out.println(i + ". " + players.get(i).getName());
		}
		System.out.println("----------------");

		int n;
		do {
			System.out.print("数字を入力：");
			n = Keyboard.inputNumber();
			if (n < 1 || n >= players.size()) {
				System.err.println("無効なコマンド：再入力してください．");
			}
		} while (n < 1 || n >= players.size());
		System.out.println("");
		players.get(0).createMessageBox("じーっ");
		CPU cpu = (CPU) players.get(n);
		cpu.reactToObservation();
	}

	private void showGlossary() {
		System.out.println("レイズ：かけるチップを倍にして，ディーラーと勝負します．");
		System.out.println("フォールド：ゲームを降ります．かけたチップは失います．");
		Keyboard.sleep();
	}

	public void showMenu(ArrayList<PlayersSeat> seats) {
		int command;
		boolean continueFrag = true;
		do {
			System.out.println("レイズ・フォールドを宣言しましょう．コマンドを入力してください．");
			System.out.print("[1:用語解説, 2:観察, 0:宣言]：");
			command = Keyboard.inputNumber();
			System.out.println("");
			switch (command) {
			case 0:
				System.out.println("レイズ・フォールドを宣言すると，次のフェイズに進みます．");
				System.out.println("宣言しますか？");
				System.out.println("----------------");
				System.out.println("1.宣言する");
				System.out.println("2.考え直す");
				System.out.println("----------------");

				int c;
				do {
					System.out.print("数字を入力：");
					c = Keyboard.inputNumber();
					if (c < 1 || c > 2) {
						System.err.println("無効なコマンド：再入力してください．");
					}
				} while (c < 1 || c > 2);
				System.out.println("");
				if (c == 1) {
					determin(seats);
					continueFrag = false;
				} else {
					System.out.println("よく考えて選んでください．");
				}
				break;
			case 1:
				showGlossary();
				break;
			case 2:
				observe();
				break;
			default:
				System.out.println("無効なコマンド：再入力してください．");
				break;
			}
			System.out.println("");
		} while (continueFrag);
		players.clear();
	}

	/**
	 * プレイヤを追加する．配列の最初に必ずユーザが入るように，アクセス制限を行う．
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		if (players.isEmpty()) {
			if (player instanceof User) {
				players.add(player);
			} else {
				System.out.println("最初にユーザを設定してください．");
			}
		} else {
			if (player instanceof User) {
				System.out.println("ユーザは設定済みです．");
			} else {
				players.add(player);
			}
		}
	}
}
