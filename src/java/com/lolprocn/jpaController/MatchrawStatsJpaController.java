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
import com.lolprocn.dbentities.Summoner;
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
        matchrawStats.getMatchrawStatsPK().setSummonerId(matchrawStats.getSummoner().getSummonerId());
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
            Summoner summoner = matchrawStats.getSummoner();
            if (summoner != null) {
                summoner = em.getReference(summoner.getClass(), summoner.getSummonerId());
                matchrawStats.setSummoner(summoner);
            }
            em.persist(matchrawStats);
            if (matches != null) {
                matches.getMatchrawStatsCollection().add(matchrawStats);
                matches = em.merge(matches);
            }
            if (summoner != null) {
                summoner.getMatchrawStatsCollection().add(matchrawStats);
                summoner = em.merge(summoner);
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
        matchrawStats.getMatchrawStatsPK().setSummonerId(matchrawStats.getSummoner().getSummonerId());
        matchrawStats.getMatchrawStatsPK().setGameId(matchrawStats.getMatches().getGameId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            MatchrawStats persistentMatchrawStats = em.find(MatchrawStats.class, matchrawStats.getMatchrawStatsPK());
            Matches matchesOld = persistentMatchrawStats.getMatches();
            Matches matchesNew = matchrawStats.getMatches();
            Summoner summonerOld = persistentMatchrawStats.getSummoner();
            Summoner summonerNew = matchrawStats.getSummoner();
            if (matchesNew != null) {
                matchesNew = em.getReference(matchesNew.getClass(), matchesNew.getGameId());
                matchrawStats.setMatches(matchesNew);
            }
            if (summonerNew != null) {
                summonerNew = em.getReference(summonerNew.getClass(), summonerNew.getSummonerId());
                matchrawStats.setSummoner(summonerNew);
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
            if (summonerOld != null && !summonerOld.equals(summonerNew)) {
                summonerOld.getMatchrawStatsCollection().remove(matchrawStats);
                summonerOld = em.merge(summonerOld);
            }
            if (summonerNew != null && !summonerNew.equals(summonerOld)) {
                summonerNew.getMatchrawStatsCollection().add(matchrawStats);
                summonerNew = em.merge(summonerNew);
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
            Summoner summoner = matchrawStats.getSummoner();
            if (summoner != null) {
                summoner.getMatchrawStatsCollection().remove(matchrawStats);
                summoner = em.merge(summoner);
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
    
}
