package board;

import java.util.Scanner;

public class App {
	
	Controller con;	
	
	void start() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("기능을 선택해주세요. (article : 게시물 기능,   member : 회원 기능,   exit : 종료)");
			String module = sc.nextLine();
			System.out.println("명령어를 입력해주세요");
			String s = sc.nextLine();
			
			if(module.equals("article")) {
				con = new ArticleController(sc);
			} else if(module.equals("member")){
				con = new MemberController(sc);
			} else if(s.equals("exit")) {
				System.out.println("프로그램 종료!");
				break;
			} else {
				System.out.println("올바른 명령어가 아닙니다.");
			}
			
			con.doCommand(s);
		} 
	}	
}
