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
	
		private static CategorieManager instance;
	
		public static CategorieManager getInstance() {
			if(instance == null) {
				instance = new CategorieManager();
			}
			return instance;
		}
	
	//Constructeur vide
		private CategorieManager () {}
	
		public List<Categorie> selectAll()  {
			return DAOFactory.getCategorieDAO().selectAll();
		}
		
	
}
