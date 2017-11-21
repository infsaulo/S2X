package parser.query.expression.op;

/**
 * @author Thorsten Berberich
 */
public abstract class AbstractExpr1 implements IExpression {

    /**
     * Generated ID
     */
    private static final long serialVersionUID = 6131309222914257986L;

    /**
     * Subexpression
     */
    protected final IExpression expr;

    /**
     * Store the subexpression
     * 
     * @param expr
     *            Subexpression
     */
    public AbstractExpr1(IExpression expr) {
	this.expr = expr;
    }

}
