import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        String a = "2 * ( 5 + {      5 *  10 +   [ 15 - 20 ] - 25 } * 9 )";
        Checker c = new Checker();
        Converter u = new Converter();
        Evaluater e = new Evaluater();
        if (c.check(a)) {
            String rversedPolish = u.convert(a);
            System.out.println(e.evaluate(rversedPolish));
        }
        else
            System.out.println("Invalid input String");
    }
}

class Checker {

    private Stack<Character> stack = new Stack<Character>();

    public boolean check(String expression) {
        int count = 0;
        stack.clear();
        while (count < expression.length()) {
            char c = expression.charAt(count);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                else if((c == ')' && stack.peek() != '(') || (c == '}' && stack.peek() != '{') || (c == ']' && stack.peek() != '['))
                    return false;
                stack.pop();
            }
            count = count + 1;
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }
}

class Converter {

    private Stack<String> stack = new Stack<String>();
    private String precedence = "-+%/*";

    public String convert(String expression) {
        stack.clear();
        String postfix = "";
        int count = 0;
        String split[] = expression.split(" ");
        while (split.length > count) {
            String s = split[count];
            if (s.matches("[0-9]+")) {
                postfix = postfix + s + " ";
            } else if (s.equalsIgnoreCase("-") || s.equalsIgnoreCase("+") || s.equalsIgnoreCase("%") || s.equalsIgnoreCase("/") || s.equalsIgnoreCase("*")) {
                if (stack.isEmpty()) {
                    stack.push(s);
                } else if (precedence.indexOf(s) > precedence.indexOf(stack.peek())) {
                    stack.push(s);
                } else {
                    String poped = stack.pop();
                    while (!(poped.equalsIgnoreCase("(") || poped.equalsIgnoreCase("[") || poped.equalsIgnoreCase("{")) && !(precedence.indexOf(s) > precedence.indexOf(poped)) && !stack.isEmpty()) {
                        postfix = postfix + poped + " ";
                        poped = stack.pop();
                    }
                    stack.push(poped);
                    stack.push(s);
                }
            } else if (s.equalsIgnoreCase("(") || s.equalsIgnoreCase("[") || s.equalsIgnoreCase("{")) {
                stack.push(s);
            } else if (s.equalsIgnoreCase(")") || s.equalsIgnoreCase("]") || s.equalsIgnoreCase("}")) {
                String poped = stack.pop();
                while (!(poped.equalsIgnoreCase("(") || poped.equalsIgnoreCase("[") || poped.equalsIgnoreCase("{"))) {
                    postfix = postfix + poped + " ";
                    poped = stack.pop().toString();
                }
            }
            count = count + 1;
        }
        while (!stack.empty()) {
            postfix = postfix + stack.pop() + " ";
        }
        System.out.println(postfix);
        return postfix;
    }
}

class Evaluater {

    private Stack stack = new Stack();

    public int evaluate(String postfix) {
        stack.clear();
        int value = 0;
        int count = 0;
        String split[] = postfix.split(" ");
        while (count < split.length) {
            String s = split[count];
            if (s.matches("[0-9]+")) {
                stack.push(s);
            } else {
                int t1 = Integer.parseInt(stack.pop().toString());
                int t2 = Integer.parseInt(stack.pop().toString());
                if (s.equalsIgnoreCase("*")) {
                    stack.push(t2 * t1);
                } else if (s.equalsIgnoreCase("/")) {
                    stack.push(t2 / t1);
                } else if (s.equalsIgnoreCase("%")) {
                    stack.push(t2 % t1);
                } else if (s.equalsIgnoreCase("+")) {
                    stack.push(t2 + t1);
                } else {
                    stack.push(t2 - t1);
                }
            }
            count = count + 1;
        }
        value = Integer.parseInt(stack.pop().toString());
        return value;
    }
}
