package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class LogOr extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -4440095423842139887L;

  public LogOr(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    if (compareExpressions(solution) == null) {
      return false;
    }
    return lExpr.evaluate(solution) || rExpr.evaluate(solution);
  }

}
