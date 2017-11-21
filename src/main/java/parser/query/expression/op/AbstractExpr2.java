package parser.query.expression.op;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.util.NodeFactoryExtra;

import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public abstract class AbstractExpr2 implements IExpression {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -3478857114950844403L;

  /**
   * Left expression
   */
  protected IExpression lExpr;

  /**
   * Right expression
   */
  protected IExpression rExpr;

  /**
   * Create a new expression with two sub expressions
   *
   * @param left  Left {@link IExpression}
   * @param right Right {@link IExpression}
   */
  public AbstractExpr2(IExpression left, IExpression right) {
    this.lExpr = left;
    this.rExpr = right;
  }

  /**
   * Compare the two stored expressions lexicographically
   *
   * @param solution {@link SolutionMapping} to get the values for the variables
   * @return {@link String#compareTo(String)}
   */
  protected Integer compareExpressions(SolutionMapping solution) {
    String left = "";
    String right = "";
    if (lExpr instanceof ExprVar) {
      left = solution.getValueToField(((ExprVar) lExpr).getVar());
    } else if (lExpr instanceof IValueType) {
      right = ((IValueType) lExpr).getValue(solution);
    }

    if (rExpr instanceof ExprVar) {
      right = solution.getValueToField(((ExprVar) rExpr).getVar());
    } else if (rExpr instanceof IValueType) {
      right = ((IValueType) rExpr).getValue(solution);
    }

    if (isInteger(left) && isInteger(right)) {
      return getInteger(left) - getInteger(right);
    }

    if (left == null || right == null) {
      return null;
    }

    return left.compareTo(right);
  }

  /**
   * Check if a String is an Integer
   *
   * @param toTest String to test
   * @return True if it is an integer, false otherwise
   */
  private Boolean isInteger(String toTest) {
    if (toTest == null) {
      return false;
    }

    Node testNode = NodeFactoryExtra.parseNode(toTest);
    return testNode.toString().endsWith(
        "^^http://www.w3.org/2001/XMLSchema#integer") ? true
                                                      : false;
  }

  /**
   * Get the integer out of the given String
   *
   * @param toGet String which contains the integer
   * @return Parsed integer
   */
  private Integer getInteger(String toGet) {
    Node testNode = NodeFactoryExtra.parseNode(toGet);
    return (Integer) testNode.getLiteral().getValue();
  }

}
