package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class Bound extends AbstractExpr1 {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -4111412284098612993L;

    public Bound(IExpression expr1) {
	super(expr1);
    }

    @Override
    public Boolean evaluate(SolutionMapping solution) {
	String varName = ((ExprVar) expr).getVar();
	String mapping = solution.getValueToField(varName);
	return (mapping != null) ? true : false;
    }
}
