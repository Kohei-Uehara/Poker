package se.t2025041.card.test;

import java.io.File;

import junit.framework.TestCase;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.game.HomeCommand;

/**
 * ホームコマンドクラスのテストクラス
 *
 */
public class HomeCommandTest extends TestCase {
	private HomeCommand command;
	private String fileName = "TEMP_FILE_FOR_CARD_GAME_TEST.csv";

	protected void setUp() throws Exception {
		super.setUp();
		Keyboard.inputForTest(10);
		command = new HomeCommand(fileName);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	public void testShowMenu() {

		for (int i = 0; i < 10; i++) {
			command.showMenu();
		}
		File file = new File(fileName);
		assertTrue(file.exists());
	}

}
