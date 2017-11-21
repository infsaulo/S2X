package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class ExprVar implements IExpression {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -8024982677564675665L;
    private final String var;

    public ExprVar(String var) {
	this.var = "?" + var;
    }

    public String getVar() {
	return var;
    }

    @Override
    public Boolean evaluate(SolutionMapping solution) {
	throw new UnsupportedOperationException();
    }

}
