package org.eulerdiagrams.euleR;

import java.util.List;
import java.util.Map;

/** 
 * This POJO captures input of the format
 * `{"area_specifications":[{"A":0.3},{"B":0.3},{"C":1.1}]}`
 * @author Aidan Delaney <aidan@ontologyengineering.org>
 *
 */
public class JSONArea {
    public List<Map<String,Double>> area_specifications;
}
