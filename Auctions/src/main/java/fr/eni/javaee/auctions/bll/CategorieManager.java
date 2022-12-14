package fr.eni.javaee.auctions.bll;

import java.util.List;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.dal.DAOFactory;


public class CategorieManager {

/**
 * 
 * @author qswiderski2022
 * 
 */
	
		private static Categorie instance;
	
		public static Categorie getInstance() {
			if(instance == null) {
				instance = new Categorie();
			}
			return instance;
		}
	
	//Constructeur vide
		private CategorieManager () {}
	
		public List<Categorie> selectAll()  {
			return DAOFactory.getCategorieDAO().selectAll();
		}
		
	
}
