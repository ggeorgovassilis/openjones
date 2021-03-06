/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jones.actions;

import jones.measures.Career;
import jones.jobs.Job;
import jones.general.Player;
import jones.general.PlayerState;

/**
 * A general work action.
 * @author dimid
 */
public class WorkAction extends Action {

    protected Job _job;
    
    
    /**
     * The period of the work action, if player has enough time
     */
    public static final int WORK_PERIOD_IN_TIME_UNITS = 60;    
    
    /**
     * How much percent are garnished, to cover the rent debt
     */  
    public static final int GARNISH_PERCENTAGE = 30;    
    private int _garnishedWage;

    
    public WorkAction(Job job) {
        _job = job;
    }

    @Override
    protected void doAction(PlayerState player) {
        player.affectCash(cashEffect(player)); //updates _garnishedWage
        player.setRentDebt(player.getRentDebt() - _garnishedWage);
        
        int timeEffect = timeEffect(player);
        player.affectTime(timeEffect);
        
        int addditionalEXPUs = (int) Math.round(timeEffect(player) * _job.EXPERIENCE_UNITS_PER_1_TIME_UNIT_OF_WORK);
        player.getCareer().gain(_job.getRank(), addditionalEXPUs, player);
        
        player.affectHealth(healthEffect(player));
        player.affectHappiness(happinessEffect(player));
        
    }

    @Override
    public int healthEffect(PlayerState player) {
        return _job.healthEffect();
    }

    @Override
    public int happinessEffect(PlayerState player) {
        return _job.happinessEffect();       
    }

    @Override
    public int careerEffect(PlayerState player) {
        Career preChange = player.getCareer();
        Career postChange = new Career(preChange);
        int addditionalEXPUs = (int) Math.round(timeEffect(player) * _job.EXPERIENCE_UNITS_PER_1_TIME_UNIT_OF_WORK);
        postChange.gain(_job.getRank(), addditionalEXPUs, player);
        return postChange.getScore() - preChange.getScore();
    }

    @Override
    public int cashEffect(PlayerState player) {
        int baseWage = (int) (timeEffect(player) * _job.getWagePerTimeUnit());
        int debt = player.getRentDebt();
        
        if ( debt > 0) {
            int nettoWage = garnish(baseWage, debt);
            _garnishedWage = baseWage - nettoWage;
            
            return nettoWage;
        }
        else {
             _garnishedWage = 0;
            return baseWage;
        }
    }

    @Override
    public int timeEffect(PlayerState player) {
        if (null == _timeEffect)            
            _timeEffect = getAvailiableTimeUpto(WORK_PERIOD_IN_TIME_UNITS, player);
        
        return _timeEffect;
        
    }

    @Override
    public String toString() {
        return "Work";
    }

    @Override
    protected ActionResponse checkConditions(PlayerState player) {
//        if (player.getClothesLevel() < _job.MIN_CLOTHES_LEVEL) {
//            return new ActionResponse(false, "You are'nt dressed properly");
//        }
//        else {
            return checkTime(player);
//        }
    }

    @Override
    protected ActionResponse getPositiveResponse(PlayerState player) {
        if (_garnishedWage > 0) {
            return new ActionResponse(true, "I had to garnish "+_garnishedWage+"$");
        }
        else {
            return new ActionResponse(true, null);
        }
    }

    @Override
    public boolean isSubmenu() {
        return false;
    }

    /**
     * Garnish money from the player's wage. 
     * If the debt is large enough garnish GARNISH_PERCENTAGE percent
     * Otherwise,subtract the debt 
     * @param baseWage
     * @return 
     */
    public int garnish(int baseWage, int debt) {                 
        return  Math.max(baseWage - debt, baseWage * (100 - GARNISH_PERCENTAGE)/100);
    }

    @Override
    public void clearCachedValues() {
        _timeEffect = null;
    }
    
}
