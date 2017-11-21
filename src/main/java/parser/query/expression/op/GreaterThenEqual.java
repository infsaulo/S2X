package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class GreaterThenEqual extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = 2019324738511796047L;

  public GreaterThenEqual(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    if (compareExpressions(solution) == null) {
      return false;
    }
    int res = compareExpressions(solution);
    return (res > 0 || res == 0) ? true : false;
  }

}
