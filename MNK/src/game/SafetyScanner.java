package game;

import java.util.Scanner;

public class SafetyScanner {
    private Scanner sc;

    public SafetyScanner() {
        sc = new Scanner(System.in);
    }

    public String next() {
        Scanner sc2 = new Scanner(sc.nextLine());
        String ans = sc2.next();
        if (sc2.hasNext()) {
            throw new IllegalArgumentException();
        }
        return ans;
    }
}
