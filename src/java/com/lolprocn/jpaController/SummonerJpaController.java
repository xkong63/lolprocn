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
import com.lolprocn.dbentities.MatchrawStats;
import com.lolprocn.dbentities.Summoner;
import com.lolprocn.jpaController.exceptions.IllegalOrphanException;
import com.lolprocn.jpaController.exceptions.NonexistentEntityException;
import com.lolprocn.jpaController.exceptions.PreexistingEntityException;
import com.lolprocn.jpaController.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        if (summoner.getMatchrawStatsCollection() == null) {
            summoner.setMatchrawStatsCollection(new ArrayList<MatchrawStats>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<MatchrawStats> attachedMatchrawStatsCollection = new ArrayList<MatchrawStats>();
            for (MatchrawStats matchrawStatsCollectionMatchrawStatsToAttach : summoner.getMatchrawStatsCollection()) {
                matchrawStatsCollectionMatchrawStatsToAttach = em.getReference(matchrawStatsCollectionMatchrawStatsToAttach.getClass(), matchrawStatsCollectionMatchrawStatsToAttach.getMatchrawStatsPK());
                attachedMatchrawStatsCollection.add(matchrawStatsCollectionMatchrawStatsToAttach);
            }
            summoner.setMatchrawStatsCollection(attachedMatchrawStatsCollection);
            em.persist(summoner);
            for (MatchrawStats matchrawStatsCollectionMatchrawStats : summoner.getMatchrawStatsCollection()) {
                Summoner oldSummonerOfMatchrawStatsCollectionMatchrawStats = matchrawStatsCollectionMatchrawStats.getSummoner();
                matchrawStatsCollectionMatchrawStats.setSummoner(summoner);
                matchrawStatsCollectionMatchrawStats = em.merge(matchrawStatsCollectionMatchrawStats);
                if (oldSummonerOfMatchrawStatsCollectionMatchrawStats != null) {
                    oldSummonerOfMatchrawStatsCollectionMatchrawStats.getMatchrawStatsCollection().remove(matchrawStatsCollectionMatchrawStats);
                    oldSummonerOfMatchrawStatsCollectionMatchrawStats = em.merge(oldSummonerOfMatchrawStatsCollectionMatchrawStats);
                }
            }
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

    public void edit(Summoner summoner) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Summoner persistentSummoner = em.find(Summoner.class, summoner.getSummonerId());
            Collection<MatchrawStats> matchrawStatsCollectionOld = persistentSummoner.getMatchrawStatsCollection();
            Collection<MatchrawStats> matchrawStatsCollectionNew = summoner.getMatchrawStatsCollection();
            List<String> illegalOrphanMessages = null;
            for (MatchrawStats matchrawStatsCollectionOldMatchrawStats : matchrawStatsCollectionOld) {
                if (!matchrawStatsCollectionNew.contains(matchrawStatsCollectionOldMatchrawStats)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MatchrawStats " + matchrawStatsCollectionOldMatchrawStats + " since its summoner field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<MatchrawStats> attachedMatchrawStatsCollectionNew = new ArrayList<MatchrawStats>();
            for (MatchrawStats matchrawStatsCollectionNewMatchrawStatsToAttach : matchrawStatsCollectionNew) {
                matchrawStatsCollectionNewMatchrawStatsToAttach = em.getReference(matchrawStatsCollectionNewMatchrawStatsToAttach.getClass(), matchrawStatsCollectionNewMatchrawStatsToAttach.getMatchrawStatsPK());
                attachedMatchrawStatsCollectionNew.add(matchrawStatsCollectionNewMatchrawStatsToAttach);
            }
            matchrawStatsCollectionNew = attachedMatchrawStatsCollectionNew;
            summoner.setMatchrawStatsCollection(matchrawStatsCollectionNew);
            summoner = em.merge(summoner);
            for (MatchrawStats matchrawStatsCollectionNewMatchrawStats : matchrawStatsCollectionNew) {
                if (!matchrawStatsCollectionOld.contains(matchrawStatsCollectionNewMatchrawStats)) {
                    Summoner oldSummonerOfMatchrawStatsCollectionNewMatchrawStats = matchrawStatsCollectionNewMatchrawStats.getSummoner();
                    matchrawStatsCollectionNewMatchrawStats.setSummoner(summoner);
                    matchrawStatsCollectionNewMatchrawStats = em.merge(matchrawStatsCollectionNewMatchrawStats);
                    if (oldSummonerOfMatchrawStatsCollectionNewMatchrawStats != null && !oldSummonerOfMatchrawStatsCollectionNewMatchrawStats.equals(summoner)) {
                        oldSummonerOfMatchrawStatsCollectionNewMatchrawStats.getMatchrawStatsCollection().remove(matchrawStatsCollectionNewMatchrawStats);
                        oldSummonerOfMatchrawStatsCollectionNewMatchrawStats = em.merge(oldSummonerOfMatchrawStatsCollectionNewMatchrawStats);
                    }
                }
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

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            Collection<MatchrawStats> matchrawStatsCollectionOrphanCheck = summoner.getMatchrawStatsCollection();
            for (MatchrawStats matchrawStatsCollectionOrphanCheckMatchrawStats : matchrawStatsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Summoner (" + summoner + ") cannot be destroyed since the MatchrawStats " + matchrawStatsCollectionOrphanCheckMatchrawStats + " in its matchrawStatsCollection field has a non-nullable summoner field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
    
}
