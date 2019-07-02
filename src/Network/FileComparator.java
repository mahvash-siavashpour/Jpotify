package Network;

import java.io.File;
import java.util.*;

public class FileComparator implements Comparator<File> {

    public int compare(File f0, File f1) {
        long date1 = f0.lastModified();
        long date2 = f1.lastModified();

        if (date1 > date2)
            return 1;
        else if (date2 > date1)
            return -1;

        return 0;
    }

    public static void main(String[] args) {
        File dir = new File(".");
        List<File> files = Arrays.asList(dir.listFiles());

//Ascending order
        Collections.sort(files, new FileComparator());
        for (File file : files) {
            System.out.println(file.getName() + "\t" + new Date(file.lastModified()));
        }

//Descending order
        Collections.sort(files, Collections.reverseOrder(new FileComparator()));
        for (File file : files) {
            System.out.println(file.getName() + "\t" + new Date(file.lastModified()));
        }
    }
}