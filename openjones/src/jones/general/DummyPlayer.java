/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jones.general;


public class DummyPlayer extends AbstractPlayer {
    private final int _career;
    private final int _education;
    private final int _health;
    private final int _happiness;
    private final int _wealth;
    private final int _total;

    public DummyPlayer (int career, int education, int health, int happiness, int wealth, int total) {
        _career = career;
        _education = education;
        _health = health;
        _happiness = happiness;
        _wealth = wealth;
        _total = total;
    }
    
    @Override
    public double getEducationScore() {
        return _education;
    }

    @Override
    public double getHealthScore() {
        return _health;
    }

    @Override
    public double getCareerScore() {
        return _career;
    }

    @Override
    public double getHappinessScore() {
        return _happiness;
    }

    @Override
    public double getWealthscore() {
        return _wealth;
    }

    @Override
    public int getTotalScore() {
        return _total;
    }
    
}
