package parser.query.op;

import com.hp.hpl.jena.sparql.algebra.op.OpUnion;

import org.apache.spark.rdd.RDD;

import java.util.Set;

import model.rdf.executionresults.IntermediateResultsModel;
import sparql.SparkFacade;
import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class SparkUnion implements SparkOp {

  private static final String TAG = "Union";
  private final OpUnion op;

  public SparkUnion(OpUnion op) {
    this.op = op;
  }

  @Override
  public void execute() {
    if (op.getLeft() != null && op.getRight() != null) {
      RDD<SolutionMapping> leftResult = IntermediateResultsModel
          .getInstance().getResultRDD(op.getLeft().hashCode());
      Set<String> leftVars = IntermediateResultsModel.getInstance()
          .getResultVariables(op.getLeft().hashCode());

      RDD<SolutionMapping> rightResult = IntermediateResultsModel
          .getInstance().getResultRDD(op.getRight().hashCode());
      Set<String> rightVars = IntermediateResultsModel.getInstance()
          .getResultVariables(op.getRight().hashCode());

      RDD<SolutionMapping> result = SparkFacade.union(leftResult,
                                                      rightResult);
      leftVars.addAll(rightVars);
      IntermediateResultsModel.getInstance().putResult(op.hashCode(),
                                                       result, leftVars);

      IntermediateResultsModel.getInstance().removeResult(
          op.getLeft().hashCode());
      IntermediateResultsModel.getInstance().removeResult(
          op.getRight().hashCode());
    }
  }

  @Override
  public String getTag() {
    return TAG;
  }

}
