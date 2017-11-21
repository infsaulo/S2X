package parser.query.expression.op;

import java.io.Serializable;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public interface IExpression extends Serializable {

    public abstract Boolean evaluate(SolutionMapping solution);
}
