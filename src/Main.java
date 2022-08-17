import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Task01 - 71. Simplify Path
    // https://leetcode.com/problems/simplify-path/
    public static String simplifyPath(String path) {
        StringBuilder result = new StringBuilder();
        String folders[] = path.split("/");
        Deque<String> deque = new ArrayDeque<>();
        for (String item : folders) {
            if (!item.equals("") && (!item.equals("."))) {
                if (!item.equals("..")) {
                    deque.addLast(item);
                } else {
                    if (deque.peekLast() != null) deque.pollLast();
                }
            }
        }
        while (deque.peekFirst() != null) {
            result.append("/");
            result.append(deque.pollFirst());
        }
        if (result.length() == 0) result.append("/");
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
            if (staples.containsValue(s.charAt(i))) deque.push(s.charAt(i));
            else if (staples.containsKey(s.charAt(i)))
                {
                    if (deque.peek() == null) return false;
                    char staple = deque.pop();
                    if (staple != staples.get(s.charAt(i))) return false;
                }
            else return false;

        }
        if (deque.peek() != null) return false;
        return true;
    }

    public static void main(String[] args) {
        String path = "/home//foo/";
        path = "/a/./b/../../c/";
        String s = "[({}())[]]";
        System.out.println(simplifyPath(path));
        System.out.println(isValid(s));
    }
}