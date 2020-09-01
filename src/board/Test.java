package board;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		ArrayList<Article> articles = new ArrayList<>();
		
		for(int i = 1; i <= 50; i++) {
			Article article1 = new Article(i, "제목" + i, "내용" + i, "작성자" + i, "20200817", 10);
			articles.add(article1);			
		}
		
		int startPage = 1; // 시작페이지
		int pageCountInBlock = 5; // 페이지 블럭당 페이지 개수
		int currentPage = 1; // 현재 페이지
		int itemsPerPage = 3; 
		int lastPage = articles.size() / itemsPerPage; // 마지막 페이지
		
		while(true) {
			int startPageInBlock = currentPage - (pageCountInBlock / 2); // 블럭 시작 페이지
			int endPageInBlock = currentPage + (pageCountInBlock / 2); // 블럭 마지막 페이지
			
			if(startPageInBlock < startPage) { // 시작페이지 보다 작으면 안된다.
				startPageInBlock = startPage;
			}
			
			if(endPageInBlock > lastPage) { // 마지막 페이지 보다 크면 안된다.
				endPageInBlock = lastPage;
			}
			
			for(int i = (currentPage - 1) * itemsPerPage; i < (currentPage - 1) * itemsPerPage + itemsPerPage; i++) {
				Article a = articles.get(i);
				System.out.println("번호 : " + a.getId());
				System.out.println("제목 : " + a.getTitle());
				System.out.println("내용 : " + a.getBody());
				System.out.println("=========================");
			}
			System.out.println("========== page =========");
			for(int i = startPageInBlock; i <= endPageInBlock; i++) {
				if(i == currentPage) {
					System.out.print("[" + i + "] ");
				} else {
					System.out.print(i + " ");
				}
				
			}
			
			
			System.out.println();
			System.out.println("페이징 기능을 선택해주세요. (prev : 이전, next : 다음, go : 선택, back : 뒤로가기)");
			String cmd = sc.nextLine();
			if(cmd.equals("prev")) {
				currentPage--;
			} else if(cmd.equals("next")) {
				currentPage++;
			} else if(cmd.equals("go")) {
				System.out.println("원하는 페이지를 선택해주세요.");
				int i = Integer.parseInt(sc.nextLine());
				currentPage = i;
			} else if(cmd.equals("back")) {
				System.out.println("페이지 기능 종료");
				break;
			}
		}
	}
}