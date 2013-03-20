/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jones.possessions;

/**
 * ConsumablePossession is a commodity that is used each week. e.g. food
 * @author dimid
 */
public class ConsumablePossession extends Possession {
    
    private double _consumptionRate;
    private double _consumed;
    private boolean _isAutoConsmuptionOn;
    
    public final double EPSILON = 0.001;
    
    public ConsumablePossession (int units, Commodity comm, double rate) {
        super(units, comm);
        _consumptionRate = rate;
        _consumed = 0;
        _isAutoConsmuptionOn = true;
    }

    public double getConsumptionRate() {
        return _consumptionRate;
    }

    public void setConsumptionRate(double _consumptionRate) {
        this._consumptionRate = _consumptionRate;
    }

    public double getConsumed() {
        return _consumed;
    }

    public void setConsumed(double _consumed) {
        this._consumed = _consumed;
    }

    public boolean isIsAutoConsmuptionOn() {
        return _isAutoConsmuptionOn;
    }

    public void setIsAutoConsmuptionOn(boolean _isAutoConsmuptionOn) {
        this._isAutoConsmuptionOn = _isAutoConsmuptionOn;
    }
    
    @Override
    public void consume() {
        if (!_isAutoConsmuptionOn) {
            return;
        }
        _consumed += _consumptionRate;
        if (Math.abs(_consumed - 1.00) < EPSILON){
            int depleted = (int) _consumed;
            _units -= depleted;
            if (_units < 0) {
                _units = 0;
            }
            _consumed = 0.0;            
        }
    }
    
    /**
     * Whether this possession will be exhausted next iteration
     * @return 
     */
    public boolean isGoingToExhaustIn1Turn() {
        return (_consumed + _consumptionRate > _units);
    }
    
     
    /**
     * Whether this possession will be exhausted in 2 iterations
     * @return 
     */
    public boolean isGoingToExhaustIn2Turns() {
        return (_consumed + 2*_consumptionRate > _units);
    }
   
}