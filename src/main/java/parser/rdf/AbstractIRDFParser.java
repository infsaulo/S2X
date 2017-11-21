package parser.rdf;

import java.io.Serializable;

/**
 * Interface for a RDF parser
 *
 * @author Thorsten Berberich
 */
public abstract class AbstractIRDFParser implements Serializable {

  /**
   * Generated UID
   */
  private static final long serialVersionUID = -8199120036122670352L;

  /**
   * @param toParse {@link String} to parse
   * @return a {@link String} array with 3 entries, first the subject, second the predicate, third
   * the object
   * @throws LineMalformedException thrown if a line was not well-formed
   */
  public abstract String[] parse(String toParse)
      throws LineMalformedException;

}
