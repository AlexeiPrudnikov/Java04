import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
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

    public static void main(String[] args) {
        String path = "/home//foo/";
        path = "/a/./b/../../c/";

        System.out.println(simplifyPath(path));
    }
}