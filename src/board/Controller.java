package board;

import java.util.Scanner;

abstract public class Controller {
	static Member loginedMember = null; // 공유자원
	Scanner sc;
	
	public Controller(Scanner sc) {
		this.sc = sc;
	}
	
	abstract void doCommand(String cmd);
}