import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Task01 - 71. Simplify Path
    // https://leetcode.com/problems/simplify-path/
    public static String simplifyPath(String path) {
        StringBuilder result = new StringBuilder();
        String[] folders = path.split("/");
        Deque<String> deque = new ArrayDeque<>();
        for (String item : folders) {
            if (!item.equals("") && (!item.equals("."))) {
                if (!item.equals("..")) {
                    deque.addLast(item);
                } else {
                    if (deque.peekLast() != null) {
                        deque.pollLast();
                    }
                }
            }
        }
        while (deque.peekFirst() != null) {
            result.append("/");
            result.append(deque.pollFirst());
        }
        if (result.length() == 0) {
            result.append("/");
        }
        return result.toString();
    }

    // Task02 - 20. Valid Parentheses
    // https://leetcode.com/problems/valid-parentheses/
    public static boolean isValid(String s) {

        Deque<Character> deque = new ArrayDeque<>();
        Map<Character, Character> staples = new HashMap<>();
        staples.put(')', '(');
        staples.put(']', '[');
        staples.put('}', '{');
        for (int i = 0; i < s.length(); i++) {
            if (staples.containsValue(s.charAt(i))) {
                deque.push(s.charAt(i));
            } else if (staples.containsKey(s.charAt(i))) {
                if (deque.peek() == null) {
                    return false;
                }
                char staple = deque.pop();
                if (staple != staples.get(s.charAt(i))) {
                    return false;
                }
            } else {
                return false;
            }

        }
        if (deque.peek() != null) {
            return false;
        }
        return true;
    }

    // Task03
    //Написать программу вычисляющую значение сложного арифметического выражения.
    // Для простоты - выражение всегда вычисляемое
    public static String dequeToString(Deque deque) {
        StringBuilder sb = new StringBuilder();
        while (deque.peek() != null) {
            sb.append(deque.poll());
            sb.append(" ");
        }
        return sb.toString();
    }

    public static int calcPostfix(String expression, Map<Character, Integer> operations) {
        int result = 0;
        String[] tokens = expression.split(" ");
        Deque stack = new ArrayDeque();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].length() > 1) {
                stack.push(tokens[i]);
            } else {
                char[] token = tokens[i].toCharArray();
                if (Character.isDigit(token[0])) {
                    stack.push(tokens[i]);
                } else {

                    int b = Integer.parseInt((String) stack.poll());
                    int a = Integer.parseInt((String) stack.poll());
                    switch (token[0]) {
                        case '+':
                            a += b;
                            break;
                        case '-':
                            a -= b;
                            break;
                        case '*':
                            a *= b;
                            break;
                        case '/':
                            a /= b;
                            break;
                        case '^':
                            Double pow = Math.pow(a, b);
                            a = pow.intValue();
                            break;
                        default:
                            break;
                    }
                    stack.push(a + "");
                }
            }
        }
        result = Integer.parseInt((String) stack.peek());
        return result;
    }

    public static String infixToPostfix(String expression, Map<Character, Integer> operations) {
        String result = "";
        expression = expression.replaceAll("\\s+", "");
        StringBuilder sb = new StringBuilder();
        Deque stack = new ArrayDeque();
        Deque queue = new ArrayDeque();
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                sb.append(expression.charAt(i));
                if (i == (expression.length() - 1)) {
                    queue.addLast(sb);
                }
            } else {
                if (sb.length() != 0) {
                    queue.addLast(sb);
                    sb = new StringBuilder();
                }
                if (expression.charAt(i) == '(') {
                    stack.push('(');
                } else if (expression.charAt(i) == ')') {
                    while (((char) stack.peek() != '(') && stack.peek() != null) {
                        queue.addLast(stack.poll());
                    }
                    if ((char) stack.peek() == '(') {
                        stack.poll();
                    }
                } else if (operations.containsKey(expression.charAt(i))) {
                    if ((stack.peek() != null)
                            && (operations.get(stack.peek()) != null)) {
                        int priority = operations.get(expression.charAt(i));
                        while ((stack.peek() != null)
                                && (operations.get(stack.peek()) != null)
                                && (operations.get(stack.peek()) >= priority)) {
                            queue.addLast(stack.poll());
                        }
                    }
                    stack.push(expression.charAt(i));
                }
            }
        }
        while (stack.peek() != null) {
            queue.addLast(stack.poll());
        }
        return dequeToString(queue);
    }

    public static int calcExpression(String expression, Map<Character, Integer> operations) {
        return calcPostfix(infixToPostfix(expression, operations), operations);
    }

    public static void main(String[] args) {
        String path = "/home//foo/";
        path = "/a/./b/../../c/";
        String s = "[({}())[]]";
        System.out.println(simplifyPath(path));
        System.out.println(isValid(s));
        Map<Character, Integer> operations = new HashMap<>();
        operations.put('^', 3);
        operations.put('*', 2);
        operations.put('/', 2);
        operations.put('+', 1);
        operations.put('-', 1);
        String str = "3 + 4 * 2 / (1 - 3) ^ 2";
        System.out.println(str + " = " + calcExpression(str, operations));
        str = "(8 + 2 * 5)/(1 + 3 * 2 - 4)";
        System.out.println(str + " = " + calcExpression(str, operations));
        str = "(2^3 * (10 / (5-3)))";
        System.out.println(str + " = " + calcExpression(str, operations));
    }
}
