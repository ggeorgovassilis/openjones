/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jones.agents;

import java.util.ArrayList;
import java.util.List;
import jones.general.Game;
import jones.general.Player;
import jones.general.PlayerState;
import jones.measures.ExperienceManager;
import jones.measures.Goals;

/**
 *
 * @author dimid <dimidd@gmail.com>
 */
class OrderedOnDemandPlanner extends OrderedPlanner {

    public OrderedOnDemandPlanner(Player p1, Game g) {
        super(p1, g);
    }


    @Override
    protected List<Plan> getOrderedPlans() {

        boolean hasAddedWork = false;
        ArrayList<Plan> result = new ArrayList<>();
        double eduScore = _player.getEducationScore();
        if (eduScore < Goals.MAX_MEASURE_SCORE) {
            result.add(new StudyAllWeekPlan(this));
        }

        double healthScore = _player.getHealthScore();
        if (healthScore < Goals.MAX_MEASURE_SCORE) {
            result.add(new RestAllWeekPlan(this));
        }

        double careerScore = _player.getCareerScore();
        if (careerScore < Goals.MAX_MEASURE_SCORE) {
            int rank = _player.getJob().getRank();
            if (rank > 0 && rank <= PlayerState.MAX_JOB_RANK) {
                int experienceLevel = _player.getCareer().getExperienceLevel(rank);
                int cap = _player.getCareer().getExp().getCapByRank(rank);
                if (experienceLevel >= cap) {
                    result.add(new GetABetterJobPlan(this));
                } else {
                    result.add(new WorkAllWeekPlan(this));
                    hasAddedWork = true;
                }
            }
            else if (0 == rank) {
                result.add(new GetABetterJobPlan(this));
            }
        }

//        _player.setJob(_game.getMap().getBuildingByName("Monolith Burgers").getJobs().get(0));

        double wealthScore = _player.getWealthscore();
        if (!hasAddedWork && wealthScore < Goals.MAX_MEASURE_SCORE) {
            result.add(new WorkAllWeekPlan(this));
        }

        return result;
    }
}
