package parser.query.expression.op;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class Lang extends AbstractExpr1 implements IValueType {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = 208593033367133594L;

  public Lang(IExpression expr1) {
    super(expr1);
  }

  @Override
  public Boolean evaluate(SolutionMapping solution) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getValue(SolutionMapping solution) {
    String lang = "";

    if (expr instanceof NodeValue) {
      lang = ((NodeValue) expr).getValue(solution);
    }

    if (expr instanceof ExprVar) {
      lang = solution.getValueToField(((ExprVar) expr).getVar());
    }
    return lang;
  }
}
