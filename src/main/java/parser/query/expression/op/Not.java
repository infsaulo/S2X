package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class Not extends AbstractExpr1 {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -5560134478604586247L;

  public Not(IExpression expr1) {
    super(expr1);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    return !expr.evaluate(solution);
  }

}
