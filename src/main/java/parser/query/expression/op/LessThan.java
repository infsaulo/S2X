package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class LessThan extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = 6745829505326435093L;

  public LessThan(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    if (compareExpressions(solution) == null) {
      return false;
    }
    return (compareExpressions(solution) < 0) ? true : false;
  }

}
