/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lolprocn.inf;

import com.lolprocn.dbentities.Matches;
import com.lolprocn.dbentities.MatchrawStats;
import com.lolprocn.dbentities.Summoner;
import com.lolprocn.entity.SummonerDto;
import com.lolprocn.jpaController.MatchesJpaController;
import com.lolprocn.jpaController.MatchrawStatsJpaController;
import com.lolprocn.jpaController.SummonerJpaController;
import com.lolprocn.jpaController.exceptions.PreexistingEntityException;
import com.lolprocn.jpaController.exceptions.RollbackFailureException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author Apollowc
 */
@ManagedBean(name = "database", eager = true)
@ApplicationScoped
public class Database {

    @PersistenceUnit(unitName = "LOLProCNPU") //inject from your application server 
    private EntityManagerFactory emf;
    @Resource //inject from your application server 
    private UserTransaction utx;

    static SummonerJpaController summonerJpaController;
    static MatchesJpaController matchesJpaController;
    static MatchrawStatsJpaController matchrawStatsJpaController;

    /**
     * Creates a new instance of Database
     */
    public Database() {

        System.out.println("database");
        //new dbThread().start();
    }

    @PostConstruct
    public void init() {
        matchesJpaController = new MatchesJpaController(utx, emf);
        summonerJpaController = new SummonerJpaController(utx, emf);
        matchrawStatsJpaController = new MatchrawStatsJpaController(utx, emf);
        Logger.getLogger(Database.class.getName()).log(Level.FINE, "PostConstruct:Database\n"+"utx{0}\nemf{1}");
    }

    public synchronized List<Matches> dbMatchesFind() {
        List<Matches> matches = matchesJpaController.findMatchesEntities();
         Logger.getLogger(Database.class.getName()).log(Level.FINE, "Database:dbMatchesFind():" + matches);
        return matches;
    }

    public synchronized void dbSummonerDtoInsert(SummonerDto summonerDto) throws RollbackFailureException, PreexistingEntityException, Exception {
        Summoner summoner = new Summoner(summonerDto.getId());
        summoner.setLevel((short) summonerDto.getSummonerLevel());
        summoner.setProfileIcon(summonerDto.getProfileIconId());
        summoner.setSummonerName(summonerDto.getName());
        summonerJpaController.create(summoner);

    }

    public synchronized void dbMatchRawStatsInsert(MatchrawStats stats) throws RollbackFailureException, PreexistingEntityException,Exception {
        matchrawStatsJpaController.create(stats);

    }
    //find summoner from summoner table using summonerId
    public synchronized Summoner dbFindSummoner(long id) {
        return summonerJpaController.findSummoner(id);
    }
    
    //find matchRawStats from matchRawStats table using summoner name
    public synchronized  List<MatchrawStats> dbFindMatchBySummonerName(String name){
        List<MatchrawStats> results=matchrawStatsJpaController.findMatchrawStatsBySummonerName(name);
        if(!results.isEmpty())
            return results;
        else
            return null;
    }
    
        //find matchRawStats from matchRawStats table using matchId
    public synchronized  List<MatchrawStats> dbFindMatchRawStatsByMatchId(long matchId){
        List<MatchrawStats> results=matchrawStatsJpaController.findMatchrawStatsByMatchId(matchId);
        if(!results.isEmpty())
            return results;
        else
            return null;
    }
    
    //find matches from matches table using matchId
    public synchronized Matches dbFindMatches(long id) {
        return matchesJpaController.findMatches(id);
    }

    public synchronized void dbMatchInsert(Matches match) throws RollbackFailureException, PreexistingEntityException,Exception {
        matchesJpaController.create(match);
    }
    

    public class dbThread extends Thread {

        @Override
        public void run() {

            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BeanInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("hello!");
            }
        }

    }

}
