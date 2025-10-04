package se.t2025041.card.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Dealer;
import se.t2025041.card.entity.Gambler;
import se.t2025041.card.entity.Gentleman;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Lady;
import se.t2025041.card.entity.PlayersSeat;
import se.t2025041.card.entity.PokerHand;
import se.t2025041.card.entity.User;

/**
 * ゲームの設定，開始等を行うUI
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-25
 * @since JDK17
 *
 */
public class HomeCommand {
	/** セーブファイルの名前 */
	String fileName = "default.csv";
	/** ポーカーテーブル */
	PokerTable table;
	/** ディーラー */
	CPU dealer;
	/** ユーザ */
	PlayersSeat user;
	/** CPU1 */
	PlayersSeat cpu1;
	/** CPU2 */
	PlayersSeat cpu2;
	/** CPU3 */
	PlayersSeat cpu3;

	/**
	 * 新規ホームコマンドを作成
	 * 
	 * @param fileName ：セーブファイル名
	 */
	public HomeCommand(String fileName) {
		table = new PokerTable();
		setFileName(fileName);
	}

	/**
	 * ゲームの初期設定を行う
	 */
	private void setGame() {
		System.out.println("□プレイ設定");
		System.out.print("名前を入力してください： ");
		String name = Keyboard.inputString();
		if (name.matches("\\s*") || name == null) {
			System.out.println("無効な名前：\"名無し\"で進めます．");
			name = "名無し";
		}
		user = new PlayersSeat(new User(name));
		System.out.print("ゲームを初期化しています...");

		PokerTable.setNUMBER_OF_TURNS(5);
		PlayersSeat.setNUMBER_OF_FIRST_CHIPS(10);

		dealer = new CPU("ディーラー", new Dealer(0.05), 0.25);
		cpu1 = new PlayersSeat(new CPU("CPU1", new Gentleman(0.08), 0.30));
		cpu2 = new PlayersSeat(new CPU("CPU2", new Lady(0.1), 0.50));
		cpu3 = new PlayersSeat(new CPU("CPU3", new Gambler(0.1), 0.70));

		table.addSeats(user);
		table.addSeats(cpu1);
		table.addSeats(cpu2);
		table.addSeats(cpu3);
		table.setDealer(dealer);

		System.out.println("完了．");
	}

	/**
	 * ゲームを開始する．
	 */
	private void startGame() {
		table.startGame();
	}

	/**
	 * 成績を記録する．
	 */
	private void recordScore() {
		System.out.println("□" + user.getPlayer().getName() + "さんの対戦成績をファイルに追記します．");
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			pw.println(user.getScore());
			System.out.println("完了しました．");
			pw.close();
		} catch (IOException e) {
			System.err.println("入出力例外です．");
		}
	}

	/**
	 * 過去の成績を見る
	 */
	private void showPreviousScore() {
		System.out.println("□過去の成績を表示します．");
		File saveFile = new File(fileName);
		if (saveFile.exists()) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			System.out.println("\n日付                :名前\t\t:スコア\t\t\t:一番強い役  ");
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				String hand = PokerHand.getString(Integer.parseInt(data[5]));
				System.out.printf("%-20s:%s\t\t:%s\t\t:%s\n", data[0], data[1], data[2], hand);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("ファイルが見つかりません．");
		} catch (Exception e) {
			System.err.println("入出力例外です．");
		}}else {
			System.out.println("\n成績情報はありません．\n");
		}
	}

	/**
	 * メニューを表示する．
	 */
	public void showMenu() {
		System.out.println("◎ポーカーゲームアプリを起動します．");
		int command;
		boolean continueFrag = true;
		do {
			System.out.println("コマンドを入力してください．");
			System.out.print("[1:ゲームを始める, 2:過去の成績を見る, 0:終了する]：");
			command = Keyboard.inputNumber();
			System.out.println("");
			switch (command) {
			case 0:
				System.out.println("終了します．");
				continueFrag = false;
				break;
			case 1:
				setGame();
				System.out.println("");
				startGame();
				System.out.println("");
				recordScore();
				break;
			case 2:
				showPreviousScore();
				break;
			default:
				System.out.println("無効なコマンド：再入力してください．");
				break;
			}
			System.out.println("");
		} while (continueFrag);
	}

	/**
	 * セーブファイル名をセットする．
	 * 
	 * @param fileName ：ファイル名
	 */
	public void setFileName(String fileName) {
		if (fileName.matches(".*[/\\\\?*:|\"<>].*")) {
			System.err.println("ファイル名に使えない文字が含まれています．");
		} else {
			this.fileName = fileName;
		}
	}
}
