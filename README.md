Rule Engine with Abstract Syntax Tree (AST)
Overview

This project implements a simple rule engine that determines user eligibility based on attributes like age, department, income, and experience. 
The engine uses an Abstract Syntax Tree (AST) to represent conditional rules, allowing for dynamic creation, combination, and modification of these rules.

Features

Rule Creation: Dynamically create rules from string representations.

Rule Combination: Combine multiple rules into a single AST for efficient evaluation.

Rule Evaluation: Evaluate combined rules against user attributes to determine eligibility.

Test Cases: Includes test cases to demonstrate functionality.

Data Structure

Node Class

The Node class represents each node in the AST.

Fields:

type: A string indicating the node type ("operator" for AND/OR, "operand" for conditions).

value: Optional value for operand nodes (e.g., comparison conditions).

left: Reference to the left child node.

right: Reference to the right child node.

    class Node {
    String type;  // "operator" or "operand"
    String value; // Operand value or operator
    Node left;    // Left child node
    Node right;   // Right child node
    }

API Methods

1. createRule(String ruleString)

Input: A string representing a rule (e.g., "age > 30 AND department = 'Sales'").

Output: Returns a Node object representing the corresponding AST.

2. combineRules(List<String> ruleStrings)

Input: A list of rule strings.

Output: Returns the root Node of the combined AST.

3. evaluateRule(Node node, Map<String, Object> data)
   
Input: A Node representing the combined rule's AST and a map containing user attributes.

Output: Returns true if the user meets the criteria specified by the rules, otherwise false.

Usage

Sample Code

Here is a basic example of how to use the Rule Engine:

    public static void main(String[] args) {
    RuleEngine engine = new RuleEngine();

    // Create individual rules
    Node rule1 = engine.createRule("age > 30 AND department = 'Sales'");

    // Combine rules
    List<String> rules = Arrays.asList(
        "age > 30 AND department = 'Sales'",
        "age < 25 AND department = 'Marketing'"
    );
    Node combinedAST = engine.combineRules(rules);

    // Evaluate rule with sample data
    Map<String, Object> data = new HashMap<>();
    data.put("age", 35);
    data.put("department", "Sales");
    data.put("salary", 60000);
    data.put("experience", 3);

    boolean result = engine.evaluateRule(combinedAST, data);
    System.out.println("Evaluation Result: " + result);  // Should return true
    }

Test Cases

The code includes test cases demonstrating:

Creating individual rules and verifying their AST representation.

Combining multiple rules and ensuring the resulting AST reflects the combined logic.

Evaluating rules against sample user data.

Future Improvements

Implement error handling for invalid rule strings or data formats.

Extend functionalities to modify existing rules.

Allow user-defined functions for advanced conditions.

Conclusion

This project serves as a foundation for a rule engine utilizing AST to manage conditional logic dynamically. 

It can be further enhanced by adding more complex rules and improving error handling.
