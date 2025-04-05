public class SumDouble {
    public static void main(String[] args) {
		StringBuilder number = new StringBuilder();
		double sum = 0;
		for (String row : args) {
			for (int i = 0; i < row.length(); i++) {
				char symbol = row.charAt(i);
				boolean beFigure = !Character.isWhitespace(symbol);
				if (beFigure) {
					number.append(symbol);
				}

				if ((!beFigure && !number.isEmpty()) || (i == row.length() - 1 && beFigure)) {
					sum += Double.parseDouble(number.toString());
					number.delete(0, number.length()); 
				}
			}
		}
		System.out.println(sum);
    }
}