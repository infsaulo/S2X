package parser.query.op;

import java.util.Set;

import org.apache.spark.rdd.RDD;

import com.hp.hpl.jena.sparql.algebra.op.OpSlice;

import model.rdf.executionresults.IntermediateResultsModel;
import sparql.SparkFacade;
import sparql.operator.result.util.SolutionMapping;

/**
 * @author Thorsten Berberich
 */
public class SparkSlice implements SparkOp {

    private static final String TAG = "Slice";
    private final OpSlice op;

    public SparkSlice(OpSlice op) {
	this.op = op;
    }

    @Override
    public void execute() {
	long limit = op.getLength();
	long offset = op.getStart();

	RDD<SolutionMapping> result = null;
	if (limit > 0 && offset > 0) {
	    // Limit and offset
	    result = SparkFacade.limitOffset(IntermediateResultsModel
		    .getInstance().getResultRDD(op.getSubOp().hashCode()),
		    (int) limit, (int) offset);
	} else if (limit > 0 && offset < 0) {
	    result = SparkFacade.limit(IntermediateResultsModel.getInstance()
		    .getResultRDD(op.getSubOp().hashCode()), (int) limit);
	} else if (limit < 0 && offset > 0) {
	    throw new UnsupportedOperationException(
		    "Offset only is not supported yet");
	}
	Set<String> resultVars = IntermediateResultsModel.getInstance()
		.getResultVariables(op.getSubOp().hashCode());
	IntermediateResultsModel.getInstance().removeResult(
		op.getSubOp().hashCode());

	IntermediateResultsModel.getInstance().putResult(op.hashCode(), result,
		resultVars);
    }

    @Override
    public String getTag() {
	return TAG;
    }

}
