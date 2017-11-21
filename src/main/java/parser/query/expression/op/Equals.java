package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class Equals extends AbstractExpr2 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -2209633308798965886L;

  public Equals(IExpression left, IExpression right) {
    super(left, right);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    if (compareExpressions(solution) == null) {
      return false;
    }
    return (compareExpressions(solution) == 0) ? true : false;
  }

}
