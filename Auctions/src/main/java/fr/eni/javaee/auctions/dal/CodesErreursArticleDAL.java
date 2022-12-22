package fr.eni.javaee.auctions.dal;

public interface CodesErreursArticleDAL {

	int INSERT_OBJECT_NULL= 50000;
	int INSERT_OBJECT_ECHEC= 50001;
	int UPDATE_ARTICLE_NULL_ECHEC = 50002;
	int UPDATE_ARTICLE_ECHEC = 50003;
	int DELETE_ARTICLE_NULL = 50004;
	int DELETE_ARTICLE_ECHEC = 50005;
}
