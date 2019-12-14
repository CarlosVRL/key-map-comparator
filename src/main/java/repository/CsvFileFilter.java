package repository;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class CsvFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getAbsolutePath().endsWith(".csv");
    }

    @Override
    public String getDescription() {
        return "*.csv";
    }
}
