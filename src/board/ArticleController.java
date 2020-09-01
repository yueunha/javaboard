package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ArticleController extends Controller {
	
	ArticleDao dao = new ArticleDao();
	int currentPage = 1;

	ArticleController(Scanner sc) { // 생성자 객체 필요한 값을 초기 세팅
		
		super(sc);
	}
	
	void doCommand(String cmd) {
		if (cmd.equals("help")) {

			System.out.println("add : 게시물 등록");
			System.out.println("list : 게시물 목록");
			System.out.println("update : 게시물 수정");
			System.out.println("delete : 게시물 삭제");
			System.out.println("sort : 게시물 정렬");
			System.out.println("read : 게시물 상세조회");
			System.out.println("page : 페이징");
			System.out.println("exit : 프로그램 종료");

		} else if (cmd.equals("add")) {
			
			if(loginedMember == null) {
				System.out.println("로그인이 필요한 기능입니다.");
			} else {

				System.out.println("제목을 입력해주세요");
				String title = sc.nextLine();
	
				System.out.println("내용을 입력해주세요");
				String body = sc.nextLine();
				
				dao.addArticle(title, body, loginedMember.getUserName());
			}
			
		} else if (cmd.equals("list")) {
			ArrayList<Article> articles = dao.getArticles();
			printArticles(articles, currentPage);
			
		} else if (cmd.equals("update")) {
			System.out.println("수정할 게시물 번호를 입력해주세요.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = dao.getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("없는 게시물입니다.");
			} else {
				System.out.println("수정할 제목을 입력해주세요.");
				String title = sc.nextLine();
				System.out.println("수정할 내용을 입력해주세요.");
				String body = sc.nextLine();

				dao.updateArticle(targetNo, title, body);

			}

		} else if (cmd.equals("delete")) {
			System.out.println("삭제할 게시물 번호를 입력해주세요.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = dao.getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("없는 게시물입니다.");
			} else {
				dao.deleteArticle(targetIndex);
			}
		} else if (cmd.equals("search")) {
			System.out.println("검색어를 입력해주세요.");
			String keyword = sc.nextLine();
			
			ArrayList<Article> searchedList = dao.getSearchedArticles(keyword);
			printArticles(searchedList, currentPage);
			
		} else if (cmd.equals("read")) {
			
			System.out.println("상세보기 할 게시물 번호를 입력해주세요.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = dao.getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("없는 게시물입니다.");
			} else {
				dao.hitUp(targetNo);
				printArticle(dao.getArticleById(targetIndex));
				detailMenuStart(targetNo);
				
			}
		} else if(cmd.equals("sort")) {//조회수 
			System.out.println("정렬대상을 선택해주세요. (hit : 조회수,  id : 번호)");
			String target = sc.nextLine();
			System.out.println("정렬방법을 선택해주세요. (asc : 오름차순,  desc : 내림차순)");
			String flag = sc.nextLine();
			
			ArrayList<Article> sortedArticles = dao.getSortedList(target, flag);
			printArticles(sortedArticles, currentPage);
			
		} else if(cmd.equals("page")) { 
			ArrayList<Article> articles = dao.getArticles();
			while(true) {
				System.out.println("페이징 기능을 선택해주세요. (prev : 이전, next : 다음, go : 선택, back : 뒤로가기)");
				String pcmd = sc.nextLine();
				if (pcmd.equals("prev")) {
					currentPage--;
					printArticles(articles, currentPage);
				} else if (pcmd.equals("next")) {
					currentPage++;
					printArticles(articles, currentPage);
				} else if (pcmd.equals("go")) {
					System.out.println("원하는 페이지를 선택해주세요.");
					int i = Integer.parseInt(sc.nextLine());
					currentPage = i;
					printArticles(articles, currentPage);
				} else if (pcmd.equals("back")) {
					System.out.println("페이지 기능 종료");
					break;
				}
			}
		}
	}
	
	void detailMenuStart(int targetNo) {
		Article article = dao.getArticleById(targetNo);
		// 상세보기 명령어 프로그램 - 댓글, 수정, 삭제, 좋아요
		while(true) {
			System.out.println("상세보기 명령어 입력:");
			String cmd2 = sc.nextLine();
			if(cmd2.equals("back")) {
				break;
			} else if(cmd2.equals("reply")) {
				System.out.println("댓글 내용을 입력해주세요.");
				String rbody = sc.nextLine();
				System.out.println("댓글이 등록되었습니다.");
				// 실제 댓글 데이터 등록
				dao.addReply(article.getId(), rbody);
				
				printArticle(article);
			} else if(cmd2.equals("update")) {
				
				if(loginedMember == null) {
					System.out.println("로그인 기능이 필요한 기능입니다.");
				} else {
					if(loginedMember.getUserName().equals(article.getWriter())) {
						
						System.out.println("수정할 제목을 입력해주세요.");
						String title = sc.nextLine();
						System.out.println("수정할 내용을 입력해주세요.");
						String body = sc.nextLine();

						article.setTitle(title);
						article.setBody(body);
						printArticle(article);
					} else {
						System.out.println("자신의 게시물만 수정 가능합니다.");
					}
				}
			} else if(cmd2.equals("delete")) {
				
				if(loginedMember == null) {
					System.out.println("로그인 기능이 필요한 기능입니다.");
				} else {
					if(loginedMember.getUserName().equals(article.getWriter())) {
						System.out.println("정말 삭제하시겠습니까?(Y/N)");
						String isDeleteYn = sc.nextLine();
						
						if(isDeleteYn.equals("Y")) {							
							dao.articles.remove(article);
							System.out.println(article.getId() + "번 게시물이 삭제되었습니다.");
						} 
						break;
					} else {
						System.out.println("자신의 게시물만 삭제 가능합니다.");
					}
				}
			} else if(cmd2.equals("like")) {
				if(loginedMember == null) {
					System.out.println("로그인 기능이 필요한 기능입니다.");
				} else {
					
					
					System.out.println("좋아요 또는 싫어요를 선택해주세요.( like : 좋아요,  hate : 싫어요)");
					String likeOrHate = sc.nextLine();
					
					Like target = dao.getLikeByArticleIdAndUserId(article.getId(), loginedMember.getLoginId());
					int flag = -1;
					
					if(likeOrHate.equals("like")) {
						flag = 0;
					} else {
						flag = 1;
					}
					
					if(target == null) {
						Like like = new Like();
						like.setArticleId(article.getId());
						like.setUserId(loginedMember.getLoginId());
						like.setFlag(flag);
						dao.likes.add(like);
					} else {
						if(target.getFlag() == flag) {
							dao.likes.remove(target);
						} else {
							target.setFlag(flag);
						}
					}
					printArticle(article);
				}
			} 
		}
	}
	

	void printArticles(ArrayList<Article> articles, int currentPage) {
		
		int startPage = 1; // 시작페이지
		int pageCountInBlock = 5; // 페이지 블럭당 페이지 개수
		//int currentPage = 1; // 현재 페이지
		int itemsPerPage = 3; 
		int lastPage = articles.size() / itemsPerPage; // 마지막 페이지
		
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
	}
	
	void printArticle(Article article) {
		System.out.println("======== " + article.getId() + "번 게시물 상세보기 =======");
		System.out.println("번호   : " + article.getId());
		System.out.println("제목   : " + article.getTitle());
		System.out.println("내용   : " + article.getBody());
		System.out.println("작성자 : " + article.getWriter());
		System.out.println("작성일 : " + article.getRegDate());
		System.out.println("조회수 : " + article.getHit());
		System.out.println("------- 좋아요/싫어요 -------");
		
		System.out.println("좋아요 : " + dao.getCountOfLikesByAritlceId(article.getId()));
		System.out.println("싫어요 : " + dao.getCountOfHatesByAritlceId(article.getId()));
		// 실제 댓글 데이터를 가져와서 출력
		System.out.println("------- 댓글 -------");
		for (int i = 0; i < dao.replies.size(); i++) {
			if(dao.replies.get(i).getParentId() == article.getId()) {
				System.out.println("내용   : " + dao.replies.get(i).getBody());
				System.out.println("작성자 : " + dao.replies.get(i).getWriter());
				System.out.println("작성일 : " + dao.replies.get(i).getRegDate());
				System.out.println("--------------------");
			}
		}		
	}
}