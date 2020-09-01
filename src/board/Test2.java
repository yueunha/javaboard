package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Test2 {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ArrayList<Article> articles = new ArrayList<Article>();
		
		Article article1 = new Article(1, "안녕하세요", "안녕하세요", "홍길동", "20200817", 20);
		Article article2 = new Article(2, "JAVA 프로그래밍", "JAVA 프로그래밍", "익명", "20200817", 10);
		Article article3 = new Article(3, "어렵지 않아요", "어렵지 않아요", "익명", "20200817", 30);
		
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		
		for(Article a : articles) {
			System.out.println("번호 : " +  a.getId());
			System.out.println("제목 : " +  a.getTitle());
			System.out.println("조회수 : " +  a.getHit());
			System.out.println("==================");
		}
	}
}