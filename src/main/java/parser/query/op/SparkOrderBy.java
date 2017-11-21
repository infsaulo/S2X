package parser.query.op;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.SortCondition;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.sparql.algebra.op.OpOrder;
import com.hp.hpl.jena.sparql.expr.Expr;

import org.apache.spark.rdd.RDD;

import model.rdf.executionresults.IntermediateResultsModel;
import parser.query.expression.ExprCompiler;
import parser.query.expression.op.ExprVar;
import sparql.SparkFacade;
import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class SparkOrderBy implements SparkOp {

  private static final String TAG = "Order By";
  private final OpOrder op;
  private final PrefixMapping prefixes;

  public SparkOrderBy(OpOrder op, PrefixMapping prefixes) {
    this.op = op;
    this.prefixes = prefixes;
  }

  @Override
  public void execute() {
    SortCondition condition = op.getConditions().iterator().next();
    Expr next = condition.getExpression();
    ExprCompiler translator = new ExprCompiler(prefixes);
    try {
      String var = ((ExprVar) translator.translate(next)).getVar();
      Boolean asc = false;
      switch (condition.getDirection()) {
        case Query.ORDER_ASCENDING: {
          asc = true;
          break;
        }
        case Query.ORDER_DESCENDING: {
          asc = false;
          break;
        }
        default: {
          asc = true;
          break;
        }
      }
      RDD<SolutionMapping> toOrder = IntermediateResultsModel
          .getInstance().getResultRDD(op.getSubOp().hashCode());

      RDD<SolutionMapping> ordered = SparkFacade.order(toOrder, var, asc);
      IntermediateResultsModel.getInstance().putResult(
          op.hashCode(),
          ordered,
          IntermediateResultsModel.getInstance().getResultVariables(
              op.getSubOp().hashCode()));

      IntermediateResultsModel.getInstance().removeResult(
          op.getSubOp().hashCode());
    } catch (ClassCastException e) {
    }
  }

  @Override
  public String getTag() {
    return TAG;
  }

}
