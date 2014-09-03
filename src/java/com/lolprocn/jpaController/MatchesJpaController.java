/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lolprocn.jpaController;

import com.lolprocn.dbentities.Matches;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.lolprocn.dbentities.MatchrawStats;
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
public class MatchesJpaController implements Serializable {

    public MatchesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matches matches) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (matches.getMatchrawStatsCollection() == null) {
            matches.setMatchrawStatsCollection(new ArrayList<MatchrawStats>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<MatchrawStats> attachedMatchrawStatsCollection = new ArrayList<MatchrawStats>();
            for (MatchrawStats matchrawStatsCollectionMatchrawStatsToAttach : matches.getMatchrawStatsCollection()) {
                matchrawStatsCollectionMatchrawStatsToAttach = em.getReference(matchrawStatsCollectionMatchrawStatsToAttach.getClass(), matchrawStatsCollectionMatchrawStatsToAttach.getMatchrawStatsPK());
                attachedMatchrawStatsCollection.add(matchrawStatsCollectionMatchrawStatsToAttach);
            }
            matches.setMatchrawStatsCollection(attachedMatchrawStatsCollection);
            em.persist(matches);
            for (MatchrawStats matchrawStatsCollectionMatchrawStats : matches.getMatchrawStatsCollection()) {
                Matches oldMatchesOfMatchrawStatsCollectionMatchrawStats = matchrawStatsCollectionMatchrawStats.getMatches();
                matchrawStatsCollectionMatchrawStats.setMatches(matches);
                matchrawStatsCollectionMatchrawStats = em.merge(matchrawStatsCollectionMatchrawStats);
                if (oldMatchesOfMatchrawStatsCollectionMatchrawStats != null) {
                    oldMatchesOfMatchrawStatsCollectionMatchrawStats.getMatchrawStatsCollection().remove(matchrawStatsCollectionMatchrawStats);
                    oldMatchesOfMatchrawStatsCollectionMatchrawStats = em.merge(oldMatchesOfMatchrawStatsCollectionMatchrawStats);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMatches(matches.getGameId()) != null) {
                throw new PreexistingEntityException("Matches " + matches + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matches matches) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Matches persistentMatches = em.find(Matches.class, matches.getGameId());
            Collection<MatchrawStats> matchrawStatsCollectionOld = persistentMatches.getMatchrawStatsCollection();
            Collection<MatchrawStats> matchrawStatsCollectionNew = matches.getMatchrawStatsCollection();
            List<String> illegalOrphanMessages = null;
            for (MatchrawStats matchrawStatsCollectionOldMatchrawStats : matchrawStatsCollectionOld) {
                if (!matchrawStatsCollectionNew.contains(matchrawStatsCollectionOldMatchrawStats)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MatchrawStats " + matchrawStatsCollectionOldMatchrawStats + " since its matches field is not nullable.");
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
            matches.setMatchrawStatsCollection(matchrawStatsCollectionNew);
            matches = em.merge(matches);
            for (MatchrawStats matchrawStatsCollectionNewMatchrawStats : matchrawStatsCollectionNew) {
                if (!matchrawStatsCollectionOld.contains(matchrawStatsCollectionNewMatchrawStats)) {
                    Matches oldMatchesOfMatchrawStatsCollectionNewMatchrawStats = matchrawStatsCollectionNewMatchrawStats.getMatches();
                    matchrawStatsCollectionNewMatchrawStats.setMatches(matches);
                    matchrawStatsCollectionNewMatchrawStats = em.merge(matchrawStatsCollectionNewMatchrawStats);
                    if (oldMatchesOfMatchrawStatsCollectionNewMatchrawStats != null && !oldMatchesOfMatchrawStatsCollectionNewMatchrawStats.equals(matches)) {
                        oldMatchesOfMatchrawStatsCollectionNewMatchrawStats.getMatchrawStatsCollection().remove(matchrawStatsCollectionNewMatchrawStats);
                        oldMatchesOfMatchrawStatsCollectionNewMatchrawStats = em.merge(oldMatchesOfMatchrawStatsCollectionNewMatchrawStats);
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
                Long id = matches.getGameId();
                if (findMatches(id) == null) {
                    throw new NonexistentEntityException("The matches with id " + id + " no longer exists.");
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
            Matches matches;
            try {
                matches = em.getReference(Matches.class, id);
                matches.getGameId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matches with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MatchrawStats> matchrawStatsCollectionOrphanCheck = matches.getMatchrawStatsCollection();
            for (MatchrawStats matchrawStatsCollectionOrphanCheckMatchrawStats : matchrawStatsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Matches (" + matches + ") cannot be destroyed since the MatchrawStats " + matchrawStatsCollectionOrphanCheckMatchrawStats + " in its matchrawStatsCollection field has a non-nullable matches field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(matches);
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

    public List<Matches> findMatchesEntities() {
        return findMatchesEntities(true, -1, -1);
    }

    public List<Matches> findMatchesEntities(int maxResults, int firstResult) {
        return findMatchesEntities(false, maxResults, firstResult);
    }

    private List<Matches> findMatchesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matches.class));
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

    public Matches findMatches(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matches.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatchesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matches> rt = cq.from(Matches.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
