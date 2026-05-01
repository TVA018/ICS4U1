import java.util.List;

public final class OutputFormatter {
    private OutputFormatter() {}

    /**
     * Generates a table as a string from the provided rows. All rows must be of the same length.
     * @param rows the rows of the table. Each row is an array of integers
     * @return the table as a string
     */
    public static String generateTable(List<String[]> rows) {
        if(rows.size() < 1) return "";

        int[] columnWidths;

        // Calculate how wide each column has to be
        {
            columnWidths = new int[rows.get(0).length];

            for(String[] row: rows) {
                for(int cellIndex = 0; cellIndex < row.length; cellIndex++) {
                    columnWidths[cellIndex] = Math.max(columnWidths[cellIndex], row[cellIndex].length());
                }
            }
        }

        StringBuilder tableBuilder = new StringBuilder();

        for(String[] row : rows) {
            for(int cellIndex = 0; cellIndex < row.length; cellIndex++) {
                tableBuilder.append("|" + asPaddedString(row[cellIndex], columnWidths[cellIndex] + 2));
            }

            tableBuilder.append("|\n");
        }

        return tableBuilder.toString();
    }

    private static String asPaddedString(String string, int targetLength) {
        int originalLength = string.length();

        if(targetLength <= originalLength) return string;

        int marginLeft = (targetLength - originalLength) / 2;
        int marginRight = targetLength - marginLeft - originalLength;

        return " ".repeat(marginLeft) + string + " ".repeat(marginRight);
    }
}
