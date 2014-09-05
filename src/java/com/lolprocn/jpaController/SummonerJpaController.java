/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.jpaController;

import com.lolprocn.dbentities.Summoner;
import com.lolprocn.jpaController.exceptions.NonexistentEntityException;
import com.lolprocn.jpaController.exceptions.PreexistingEntityException;
import com.lolprocn.jpaController.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Apollowc
 */
public class SummonerJpaController implements Serializable {

    public SummonerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Summoner summoner) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(summoner);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSummoner(summoner.getSummonerId()) != null) {
                throw new PreexistingEntityException("Summoner " + summoner + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Summoner summoner) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            summoner = em.merge(summoner);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = summoner.getSummonerId();
                if (findSummoner(id) == null) {
                    throw new NonexistentEntityException("The summoner with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Summoner summoner;
            try {
                summoner = em.getReference(Summoner.class, id);
                summoner.getSummonerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The summoner with id " + id + " no longer exists.", enfe);
            }
            em.remove(summoner);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Summoner> findSummonerEntities() {
        return findSummonerEntities(true, -1, -1);
    }

    public List<Summoner> findSummonerEntities(int maxResults, int firstResult) {
        return findSummonerEntities(false, maxResults, firstResult);
    }

    private List<Summoner> findSummonerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Summoner.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Summoner findSummoner(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Summoner.class, id);
        } finally {
            em.close();
        }
    }

    public int getSummonerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Summoner> rt = cq.from(Summoner.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public List<Summoner> findSummonerByName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Summoner.findBySummonerName").setParameter("summonerName",name).getResultList();
        } finally {
            em.close();
        }
    }
    
}
