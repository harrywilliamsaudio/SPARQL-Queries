// Harry Williams - 201374501
// Advanced Web Technologies - COMP319
// Assignment I
// Prof. Valentina Tamma

import org.apache.jena.query.Dataset;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.query.ResultSetFormatter;

public class StoreAndQuery 
{
	

	public static void printResults(String query, ResultSet result)
	{
		System.out.println(query);
		System.out.println(ResultSetFormatter.asText(result));
	}
	
	public static void main(String[] args)
	{
		
		
		
/*
 * 
 *  
 *  Data Load & Setup 
 *  
 *  
 */ 
		
		String directory = "/Users/macbook/eclipse-workspace/StoreAndQuery/NobelDirectory"; // Import file
		Dataset dataset = TDBFactory.createDataset(directory); // Create dataset 
		Model nobel = dataset.getDefaultModel(); // Create model of the dataset
		FileManager.get().readModel(nobel, "nobelprize.nt", "N-TRIPLES"); // Read model
		
/*
 * 
 *  
 *  String Queries 
 *  
 *  
 */
		
		String questionEx  = "\nPrint the first 20 elements of the triple store:\n";
		
		String questionOne = "Q1: Find all the female Nobel laureate who received the award after 2000, "
				+ "list their names  together with the category for which they received the award:\n"; 
		
		String questionTwo = "Q2: List the names of the Nobel laureates awarded the peace Nobel price in 1911:\n";
		
		String questionThree = "Q3: List the names and the ages of the winners"
				+ " of the Nobel in chemistry who won after 2010:\n"; 
		
		String questionFour = "Q4: Find the names and the disciplines of all the Nobel Laureates "
				+ "born in the US and whose names (first and middle) include Ernest:\n";
		
				
/*
 * 
 *  
 *  SPARQL Queries 
 *  
 *  
 */
		
// Test Query - print first 20 elements of the triple store 
		
		String queryExample = "SELECT * WHERE {?s ?p ?o} LIMIT 20"; 
		
	 
// Query I - Names of all female Nobel Laureates after 2000 & prize category
		
		String queryOne = "PREFIX nobel: <http://data.nobelprize.org/terms/>\n" + 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + 
				"SELECT DISTINCT ?name ?category\n" + 
				"WHERE \n" + 
				"{\n" + 
				"  ?s nobel:category ?category.\n" + 
				"  ?s nobel:laureate ?person.\n"   + 
				"  ?s nobel:year     ?year. \n"    + 
				"  ?person foaf:name ?name.\n"     + 
				"  ?person foaf:gender \"female\".\n" + 
				"FILTER(?year <= 2000)\n" + 
				"}\n" + 
				"";
		
// Query II - List the names of Nobel Laureates awarded the peace prize in 1911 
		
		
		String queryTwo = "PREFIX nobel: <http://data.nobelprize.org/terms/>\n" + 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + 
				"SELECT DISTINCT ?name \n" + 
				"WHERE \n" + 
				"{\n" + 
				"?s nobel:category <http://data.nobelprize.org/resource/category/Peace>.\n" + 
				"?s nobel:laureate ?person.   \n" + 
				"?s nobel:year ?year.  \n" + 
				"?person foaf:name ?name. \n" + 
				"FILTER(?year = 1911)\n" + 
				"}";
		
// Query III - Names and ages of Nobel Laureates for Chemistry
		
		String queryThree = "PREFIX nobel: <http://data.nobelprize.org/terms/>\n" + 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" + 
				"SELECT DISTINCT ?name ?winnerAge\n" + 
				"WHERE \n" + 
				"{\n" + 
				"?s nobel:category <http://data.nobelprize.org/resource/category/Chemistry>.\n" + 
				"?s nobel:laureate ?person.\n" + 
				"?s nobel:year ?year.\n" + 
				"?person foaf:name ?name.\n" + 
				"?person foaf:birthday ?birth.\n" + 
				"BIND (year(?birth) as ?yearBorn)\n" + 
				"BIND(?year- ?yearBorn AS ?winnerAge)\n" + 
				"FILTER(?year > 2010)  \n" + 
				"}";
		
		
// Query IV - Find the names and the disciplines of all the Nobel Laureates born in the US 
// and whose names (first and middle) include Ernest.
		
		
		String queryFour = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" + 
				"PREFIX nobel: <http://data.nobelprize.org/terms/>\n" + 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + "SELECT DISTINCT ?name ?category\n" + 
				"WHERE \n" + 
				"{ \n" + 
				"?s nobel:category ?category.\n" + 
				"?s nobel:laureate ?person.\n" + 
				"?person foaf:name ?name.\n" + 
				"?person dbpedia-owl:birthPlace <http://data.nobelprize.org/resource/country/USA>.\n" + 
				"FILTER contains(?name, \"Ernest\")\n" + 
				"}";

/*
 * 
 *  
 *  Execute Queries 
 *  
 *  
 */
		
		// Test Query 
		
		QueryExecution queryExampleTest = QueryExecutionFactory.create(queryExample, nobel);
		ResultSet resultsTest = queryExampleTest.execSelect();
		
		
		// Query 1 
		
		QueryExecution queryOneResult = QueryExecutionFactory.create(queryOne, nobel);
		ResultSet resultsQ1 = queryOneResult.execSelect();
		
		// Query 2
		
		QueryExecution queryTwoResult = QueryExecutionFactory.create(queryTwo, nobel);
		ResultSet resultsQ2 = queryTwoResult.execSelect();
		
		// Query 3
		
		QueryExecution queryThreeResult = QueryExecutionFactory.create(queryThree, nobel);
		ResultSet resultsQ3 = queryThreeResult.execSelect();
		
		// Query 4
		
		QueryExecution queryFourResult = QueryExecutionFactory.create(queryFour, nobel);
		ResultSet resultsQ4 = queryFourResult.execSelect();

				
/*
 * 
 *  
 *  Print Results 
 *  
 *  
 */
		
		printResults(questionEx, resultsTest);
		printResults(questionOne, resultsQ1);
		printResults(questionTwo, resultsQ2);
		printResults(questionThree, resultsQ3);
		printResults(questionFour, resultsQ4);
		
		
	}

}
