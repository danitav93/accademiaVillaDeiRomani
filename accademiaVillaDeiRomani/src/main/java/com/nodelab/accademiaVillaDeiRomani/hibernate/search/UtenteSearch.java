package com.nodelab.accademiaVillaDeiRomani.hibernate.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

/**
 * Search methods for the entity Utente using Hibernate search.
 * The Transactional annotation ensure that transactions will be opened and
 * closed at the beginning and at the end of each method.
 */
@Repository
@Transactional
public class UtenteSearch {
	
	// Spring will inject here the entity manager object
	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  /**
	   * A basic search for the entity User. The search is done by exact match per
	   * keywords on fields name, city and email.
	   * 
	   * @param text The query text.
	   */
	  public List<Utente> search(String text) {
	    
	    // get the full text entity manager
	    FullTextEntityManager fullTextEntityManager =
	        org.hibernate.search.jpa.Search.
	        getFullTextEntityManager(entityManager);
	    
	    // create the query using Hibernate Search query DSL
	    QueryBuilder queryBuilder = 
	        fullTextEntityManager.getSearchFactory()
	        .buildQueryBuilder().forEntity(Utente.class).get();
	    
	    // a very basic query by keywords
	    org.apache.lucene.search.Query query =
	        queryBuilder
	          .keyword()
	          .onFields("nome", "cognome","matricola")
	          .matching(text)
	          .createQuery();

	    // wrap Lucene query in an Hibernate Query object
	    org.hibernate.search.jpa.FullTextQuery jpaQuery =
	        fullTextEntityManager.createFullTextQuery(query, Utente.class);
	  
	    // execute search and return results (sorted by relevance as default)
	    @SuppressWarnings("unchecked")
	    List<Utente> results = jpaQuery.getResultList();
	    
	    return results;
	  } // method search

}
