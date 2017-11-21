package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class LogAnd extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -6443040167748002707L;

  public LogAnd(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    if (compareExpressions(solution) == null) {
      return false;
    }
    return lExpr.evaluate(solution) && rExpr.evaluate(solution);
  }

}
