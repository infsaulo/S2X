package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * Interface for types with a value
 * 
 * @author Thorsten Berberich
 * 
 */
public interface IValueType {

    /**
     * Get the value of this type
     * 
     * @param solution
     *            All solution mappings
     * @return The evaluated result with regard to the solution mappings
     */
    public String getValue(SolutionMapping solution);

}
