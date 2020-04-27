package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Core {
	@FXML
	private Label answer;
	private double n1 = 0;
	private double n2 = 0;
	private String op = "";
	private boolean start = true;
	private boolean nextNum = false;
	
	@FXML
	public void calcNums(ActionEvent event)
	{
		if(start)
		{
			n1 = 0;
			n2 = 0;
			op = "";
			answer.setText("");
		}
		start = false;
		if(nextNum)
		{
			answer.setText("");
			nextNum = false;
		}
		String val = ((Button)event.getSource()).getText();
		answer.setText(answer.getText() + val);
		
	}
	
	@FXML
	public void calcOps(ActionEvent event) throws ParseException
	{
		DecimalFormat format = new DecimalFormat();
		NumberFormat nFormat = NumberFormat.getInstance(Locale.US);
	    format.setDecimalSeparatorAlwaysShown(false);
		String val = ((Button)event.getSource()).getText();
		if(val.contentEquals("log10"))
		{
			Number n = nFormat.parse(answer.getText());
			n2 = Math.log10(n.doubleValue());
			answer.setText(format.format(n2));
			return;
		}
		if(val.equals("C"))
		{
			start = true;
			nextNum = false;
			n1 = 0;
			n2 = 0;
			op = "";
			answer.setText("");
			return;
		}
		if(val.equals("<-"))
		{
			if(!answer.getText().isEmpty())
				answer.setText(answer.getText().substring(0,answer.getText().length()-1));
			return;
		}
		else
		{		
			if(op.isEmpty())
			{
				n1 = Double.parseDouble(answer.getText());
				answer.setText("");
				op = val;
				return;
			}
			else
			{
				if(val.equals("="))
				{
					n2 = Double.parseDouble(answer.getText());
					answer.setText(calculate(n1,n2,op));
					try
					{
						n1 = Double.parseDouble(calculate(n1,n2,op));
					}catch (NumberFormatException nfe) {
						start = true;
						nextNum = false;
					}
					n2 = 0;
					op = "";
					start = true;
					nextNum = false;
					return;
				}
				else if(!start)
				{
					n2 = Double.parseDouble(answer.getText());
					try
					{
					n1 = Double.parseDouble(calculate(n1,n2,op));
					}
					catch (NumberFormatException nfe) {
						start = true;
						nextNum = false;
						answer.setText("Divide by 0");
						return;
					}
					answer.setText(Double.toString(n1));
					n2 = Double.parseDouble(answer.getText());
					nextNum = true;
					op = val;
				}
			}
		}
	}

	public String calculate(double n1, double n2, String op)
	{
		DecimalFormat format = new DecimalFormat();
	    format.setDecimalSeparatorAlwaysShown(false);
		double ans = 0;
		switch(op)
		{
			case "+":
				ans = n1 + n2;
				break;
			case "-":
				ans = n1 - n2;
				break;
			case "*":
				ans = n1 * n2;
				break;
			case "/":
				if(n2 == 0)
				{
					return "Divide by 0";
				}
				else
				{
					ans = n1 / n2;
					break;
				}
		}
		return format.format(ans);
	}
}
