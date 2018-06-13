package bestgymever.view;

import java.util.*;

public class ConsoleView implements IView {

    @Override
    public String display(List<String> viewList) {
        viewList.forEach((string) -> {
            System.out.println(string);
        });
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        return line;
    }
}
