import java.util.*;

class Node {
    String type;  // "operator" (AND/OR) or "operand" (condition)
    String value; // Operand value or operator (e.g., "age > 30", "AND")
    Node left;    // Left child node
    Node right;   // Right child node

    // Constructor for leaf nodes (operands)
    public Node(String type, String value) {
        this.type = type;
        this.value = value;
    }

    // Constructor for internal nodes (operators)
    public Node(String type, String value, Node left, Node right) {
        this.type = type;
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

public class RuleEngine {

    // Method to create a rule and convert it to an AST
    public Node createRule(String ruleString) {
        // Example: ruleString = "age > 30 AND department = 'Sales'"
        String[] tokens = ruleString.split(" ");

        // Create operand nodes for the condition
        Node leftOperand = new Node("operand", tokens[0] + " " + tokens[1] + " " + tokens[2]);
        Node rightOperand = new Node("operand", tokens[4] + " " + tokens[5] + " " + tokens[6]);

        // Create operator node (AND)
        return new Node("operator", tokens[3], leftOperand, rightOperand);
    }

    // Method to combine multiple rules into a single AST
    public Node combineRules(List<String> ruleStrings) {
        Node root = null;

        for (String ruleString : ruleStrings) {
            Node subAst = createRule(ruleString);  // Convert each rule to an AST

            if (root == null) {
                root = subAst;
            } else {
                // Combine with existing AST using an AND operator
                root = new Node("operator", "AND", root, subAst);
            }
        }
        return root;
    }

    // Method to evaluate the AST against user data
    public boolean evaluateRule(Node node, Map<String, Object> data) {
        if (node.type.equals("operand")) {
            String[] parts = node.value.split(" ");
            String key = parts[0];
            String operator = parts[1];
            String value = parts[2].replace("'", "");  // Remove quotes if any

            // Perform comparison based on the operator
            switch (operator) {
                case ">":
                    return (int) data.get(key) > Integer.parseInt(value);
                case "<":
                    return (int) data.get(key) < Integer.parseInt(value);
                case "=":
                    return data.get(key).toString().equals(value);
            }
        } else if (node.type.equals("operator")) {
            boolean leftResult = evaluateRule(node.left, data);
            boolean rightResult = evaluateRule(node.right, data);

            switch (node.value) {
                case "AND":
                    return leftResult && rightResult;
                case "OR":
                    return leftResult || rightResult;
            }
        }
        return false;  // Default case if the rule is invalid
    }

    // Main method to test the rule engine
    public static void main(String[] args) {
        RuleEngine engine = new RuleEngine();

        // Test Case 1: Create individual rule
        Node rule1 = engine.createRule("age > 30 AND department = 'Sales'");
        System.out.println("AST for Rule 1: " + rule1.value);

        // Test Case 2: Combine multiple rules
        List<String> rules = Arrays.asList(
            "age > 30 AND department = 'Sales'",
            "age < 25 AND department = 'Marketing'"
        );
        Node combinedAST = engine.combineRules(rules);
        System.out.println("Combined AST Root: " + combinedAST.value);

        // Test Case 3: Evaluate rule with sample data
        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        data.put("department", "Sales");
        data.put("salary", 60000);
        data.put("experience", 3);

        boolean result = engine.evaluateRule(combinedAST, data);
        System.out.println("Evaluation Result: " + result);  // Should return true
    }
}
