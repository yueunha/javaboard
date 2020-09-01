package board;

import java.util.ArrayList;
import java.util.Collections;

public class ArticleDao {

	ArrayList<Article> articles = new ArrayList<>();
	ArrayList<Reply> replies = new ArrayList<>();
	ArrayList<Like> likes = new ArrayList<>();

	int lastId = 3; // 가장 마지막에 추가된 게시물의 게시물 번호
	int lastReplyId = 0; // 가장 마지막에 추가된 댓글 번호

	public void makeTestData() {
		Article article1 = new Article(1, "안녕하세요", "안녕하세요", "홍길동", "20200817", 10);
		Article article2 = new Article(2, "JAVA 프로그래밍", "JAVA 프로그래밍", "익명", "20200817", 20);
		Article article3 = new Article(3, "어렵지 않아요", "어렵지 않아요", "익명", "20200817", 30);

		articles.add(article1);
		articles.add(article2);
		articles.add(article3);

		for (int i = 4; i <= 50; i++) {
			Article article = new Article(i, "제목" + i, "내용" + i, "작성자" + i, "20200817", 10);
			articles.add(article);
		}
	}

	public void addArticle(String title, String body, String userName) {
		lastId++; // 게시물 번호 자동 증가
		int id = lastId;
		
		String datetime = MyUtil.getCurrentDate();
		Article article = new Article(id, title, body, userName, datetime, 0);
		
		articles.add(article);
	}

	public ArrayList<Article> getArticles() {
		return this.articles;
	}

	public void updateArticle(int id, String title, String body) {
		int targetIndex = getArticleIndexById(id);
		articles.set(targetIndex, getArticleById(targetIndex));
	}
	
	public void deleteArticle(int id) {
		int targetIndex = getArticleIndexById(id);
		articles.remove(targetIndex);
	}
	
	ArrayList<Like> getLikesByAritlceId(int articleId) {
		
		ArrayList<Like> likeListByArticleId = new ArrayList<Like>();
		
		for(int i = 0; i < likes.size(); i++) {
			if(likes.get(i).getArticleId() == articleId) {
				likeListByArticleId.add(likes.get(i));
			}
		}
		
		return likeListByArticleId;
		
	}
	
	int getCountOfLikesByAritlceId(int articleId) {
		int count = 0;
		ArrayList<Like> likes = getLikesByAritlceId(articleId);
		for(int i = 0; i < likes.size(); i++) {
			if(likes.get(i).getFlag() == 0) {
				count++;
			}
		}
		
		return count;
	}
	
	int getCountOfHatesByAritlceId(int articleId) {
		int countOfLikes = getCountOfLikesByAritlceId(articleId);
		int countOfAll = getLikesByAritlceId(articleId).size();
		
		return countOfAll - countOfLikes; 
		
	}
	
	Like getLikeByArticleIdAndUserId(int articleId, String userId) {
		Like target = null;
		ArrayList<Like> likes = getLikesByAritlceId(articleId);
		for(int i = 0; i < likes.size(); i++) {
			if(likes.get(i).getUserId().equals(userId)) {
				target = likes.get(i); 
			}
		}
		
		return target;
	}
	
	int getArticleIndexById(int targetNo) {
		int targetIndex = -1; // 찾는게 없을 때 -1

		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getId() == targetNo) {
				targetIndex = i;
			}
		}

		return targetIndex;
	}
	
	Article getArticleById(int id) {
		int targetIndex = getArticleIndexById(id);
		if(targetIndex != -1) {
			return articles.get(targetIndex);
		}
		
		return null;
	}
	
	ArrayList<Article> getSearchedArticles(String keyword) {
		ArrayList<Article> searchedList = new ArrayList<Article>();
		for(int i = 0; i < articles.size(); i++) {
			String title = articles.get(i).getTitle();
			if(title.contains(keyword)) {
				searchedList.add(articles.get(i));
			}
		}
		
		return searchedList;
	}

	public void hitUp(int id) {
		int targetIndex = getArticleIndexById(id);
		int currentHit = articles.get(targetIndex).getHit();
		articles.get(targetIndex).setHit(currentHit + 1);
	}

	public ArrayList<Article> getSortedList(String target, String flag) {
		MyComparator com = new MyComparator();
		com.setTarget(target);
		com.setFlag(flag);
		Collections.sort(articles, com);
		return articles;
	}
	
	public void addReply(int parentId, String rbody) {
		lastReplyId++; // 댓글 번호 자동 증가
		int id = lastReplyId;
		
		Reply reply = new Reply(id, parentId, rbody, "익명", "20200817");
		replies.add(reply);

	}
}