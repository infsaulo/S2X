package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class GreaterThan extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = 53099429024307787L;

  public GreaterThan(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    if (compareExpressions(solution) == null) {
      return false;
    }
    return (compareExpressions(solution) > 0) ? true : false;
  }

}
