/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.jpaController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.lolprocn.dbentities.Matches;
import com.lolprocn.dbentities.MatchrawStats;
import com.lolprocn.dbentities.MatchrawStatsPK;
import com.lolprocn.jpaController.exceptions.NonexistentEntityException;
import com.lolprocn.jpaController.exceptions.PreexistingEntityException;
import com.lolprocn.jpaController.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Apollowc
 */
public class MatchrawStatsJpaController implements Serializable {

    public MatchrawStatsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MatchrawStats matchrawStats) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (matchrawStats.getMatchrawStatsPK() == null) {
            matchrawStats.setMatchrawStatsPK(new MatchrawStatsPK());
        }
        matchrawStats.getMatchrawStatsPK().setGameId(matchrawStats.getMatches().getGameId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Matches matches = matchrawStats.getMatches();
            if (matches != null) {
                matches = em.getReference(matches.getClass(), matches.getGameId());
                matchrawStats.setMatches(matches);
            }
            em.persist(matchrawStats);
            if (matches != null) {
                matches.getMatchrawStatsCollection().add(matchrawStats);
                matches = em.merge(matches);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMatchrawStats(matchrawStats.getMatchrawStatsPK()) != null) {
                throw new PreexistingEntityException("MatchrawStats " + matchrawStats + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MatchrawStats matchrawStats) throws NonexistentEntityException, RollbackFailureException, Exception {
        matchrawStats.getMatchrawStatsPK().setGameId(matchrawStats.getMatches().getGameId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MatchrawStats persistentMatchrawStats = em.find(MatchrawStats.class, matchrawStats.getMatchrawStatsPK());
            Matches matchesOld = persistentMatchrawStats.getMatches();
            Matches matchesNew = matchrawStats.getMatches();
            if (matchesNew != null) {
                matchesNew = em.getReference(matchesNew.getClass(), matchesNew.getGameId());
                matchrawStats.setMatches(matchesNew);
            }
            matchrawStats = em.merge(matchrawStats);
            if (matchesOld != null && !matchesOld.equals(matchesNew)) {
                matchesOld.getMatchrawStatsCollection().remove(matchrawStats);
                matchesOld = em.merge(matchesOld);
            }
            if (matchesNew != null && !matchesNew.equals(matchesOld)) {
                matchesNew.getMatchrawStatsCollection().add(matchrawStats);
                matchesNew = em.merge(matchesNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MatchrawStatsPK id = matchrawStats.getMatchrawStatsPK();
                if (findMatchrawStats(id) == null) {
                    throw new NonexistentEntityException("The matchrawStats with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MatchrawStatsPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MatchrawStats matchrawStats;
            try {
                matchrawStats = em.getReference(MatchrawStats.class, id);
                matchrawStats.getMatchrawStatsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matchrawStats with id " + id + " no longer exists.", enfe);
            }
            Matches matches = matchrawStats.getMatches();
            if (matches != null) {
                matches.getMatchrawStatsCollection().remove(matchrawStats);
                matches = em.merge(matches);
            }
            em.remove(matchrawStats);
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

    public List<MatchrawStats> findMatchrawStatsEntities() {
        return findMatchrawStatsEntities(true, -1, -1);
    }

    public List<MatchrawStats> findMatchrawStatsEntities(int maxResults, int firstResult) {
        return findMatchrawStatsEntities(false, maxResults, firstResult);
    }

    private List<MatchrawStats> findMatchrawStatsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MatchrawStats.class));
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

    public MatchrawStats findMatchrawStats(MatchrawStatsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MatchrawStats.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatchrawStatsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MatchrawStats> rt = cq.from(MatchrawStats.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public List<MatchrawStats> findMatchrawStatsBySummonerName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("MatchrawStats.findBySummonerName",MatchrawStats.class).setParameter("summonerName", name).getResultList();
        } finally {
            em.close();
        }}
               public List<MatchrawStats> findMatchrawStatsByMatchId(long matchId) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("MatchrawStats.findByGameId",MatchrawStats.class).setParameter("gameId", matchId).getResultList();
        } finally {
            em.close();
        }
        
    }
    
}
