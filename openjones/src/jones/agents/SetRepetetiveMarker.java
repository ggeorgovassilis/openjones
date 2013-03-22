/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jones.agents;

import jones.actions.Action;
import jones.actions.Movement;

/**
 *
 * @author dimid <dimidd@gmail.com>
 */
class SetRepetetiveMarker extends PlanMarker {
    private final boolean _repetValue;

    public SetRepetetiveMarker(Plan result, Action get, boolean b) {
        super (result, get);
        _repetValue = b;
    }

    @Override
    public void changeState() {
        _plan.setIsRepetetive(_repetValue);
    }
    
}