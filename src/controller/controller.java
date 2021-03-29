/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Gui.cal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JFrame;

/**
 *
 * @author mynameis
 */
public class controller {

    public controller(JFrame frame) {
    }

    public controller(cal cal) {
        this.cal = cal;

    }
    cal cal;

    private BigDecimal firstNum;
    private BigDecimal secondNum;

    private boolean newNumber;
    private boolean process;

    private BigDecimal temp = new BigDecimal("0");
    private int op = 0;

    public BigDecimal getValue() {
        String value = cal.getTxtDisplay().getText();//lay value từ màn hình
        if (value.equals("Error")) {
            return new BigDecimal("0");
        }
        return new BigDecimal(value);
    }
    //hàm này dùng đề nhấn dấu chấm 

    public void pressDecimal() {
        if (!cal.getTxtDisplay().getText().equals("Error")) {

            String value = cal.getTxtDisplay().getText();

            if (!value.contains(".")) {
                cal.getTxtDisplay().setText(value + ".");
            } else {
                if (process || newNumber) {
                    cal.getTxtDisplay().setText("0.");
                    process = false;
                    newNumber = false;
                }

            }
        }

    }

    public void pressNumber(String num) {
        if (cal.getTxtDisplay().getText().equals("Error")) {
            newNumber = true;
        }
        if (process || newNumber) {
            cal.getTxtDisplay().setText("0");
            process = false;
            newNumber = false;
        }
        if (cal.getTxtDisplay().getText().equals("0")) {
            cal.getTxtDisplay().setText(num);
        } else {
            cal.getTxtDisplay().setText(cal.getTxtDisplay().getText() + num);
        }

    }

    public void calculator(int op) {
        operator();
        this.op = op;
    }

    public void pressAllClear() {

        firstNum = new BigDecimal("0");
        secondNum = new BigDecimal("0");
        cal.getTxtDisplay().setText("0");
    }

    public void operator() {

        String value = cal.getTxtDisplay().getText();
        //khi man hình hiển thị value = error thì ko thực hiện các phép tính cộng trừ nhân chia
        if (!process && !value.contains("Error")) {
            if (op == 0) {
                firstNum = getValue();
                System.out.println("f" + firstNum);
            } else {
                secondNum = getValue();
                System.out.println("s+" + secondNum);
                if (op == 1) {
                    firstNum = firstNum.add(secondNum);
                } else if (op == 2) {

                    firstNum = firstNum.subtract(secondNum);
                    System.out.println(firstNum);
                } else if (op == 3) {

                    firstNum = firstNum.multiply(secondNum);
                } else if (op == 4) {

                    System.out.println(secondNum);
                    //neu chia cho 0 thì xảy ra lỗi
                    if (secondNum.toString().equals("0")) {
                        cal.getTxtDisplay().setText("Error");
                        newNumber = true;
                        return;
                    } else {
                        firstNum = firstNum.divide(secondNum, 10, RoundingMode.HALF_UP);
                    }
                }
            }

            process = true;
            newNumber = true;

            cal.getTxtDisplay().setText(firstNum.stripTrailingZeros().toPlainString());
        }
    }

    public void negationNumber() {

        String value = cal.getTxtDisplay().getText();
        //nêu màn hình hiển thị error thì ko đx add vào 
        if (value.equals("Error")) {
            return;
        }
        if (!value.contains("-") && !value.equals("0")) {
            //firstNum = new BigDecimal("-" + value);
            cal.getTxtDisplay().setText("-" + value);
        } else if (value.contains("-")) {
            //cắt bỏ dấu trừ vào xóa chuỗi
            cal.getTxtDisplay().setText(value.substring(1, value.length()));
            //firstNum = new BigDecimal(value.substring(1, value.length()));
        }

    }

    public void backSpace() {
        String value = cal.getTxtDisplay().getText();
        //nếu mà newnumber không pải true thì mới đx phép xóa
        if (!newNumber) {

            if (value.endsWith("-0.")) {
                value = "0";
            }
            if (value.contains("-") && value.length() == 2) {
                value = "0";
            }
            value = value.substring(0, value.length() - 1);

            if (value.length() == 0) {
                value = "0";

            }
            cal.getTxtDisplay().setText(value);
        }
    }

    public void pressPercent() {
        if (cal.getTxtDisplay().getText().equals("Error")) {

            return;
        } else {
            BigDecimal percent = getValue().divide(new BigDecimal("100"));
            cal.getTxtDisplay().setText(percent.toPlainString());
        }
        newNumber = true;
    }

    public void pressSqrt() {
        if (cal.getTxtDisplay().getText().equals("Error")) {
            return;
        } else if (cal.getTxtDisplay().getText().contains("-")) {
            cal.getTxtDisplay().setText("Error");
            newNumber = true;
            return;
        } else {

            String sqrt = (Math.sqrt(getValue().doubleValue()) + "");
            if (sqrt.endsWith(".0")) {
                cal.getTxtDisplay().setText(sqrt.substring(0, sqrt.length() - 2));
            } else {
                cal.getTxtDisplay().setText(sqrt);
            }
        }
        newNumber = true;
    }

    public void pressConvert() {
        String value = cal.getTxtDisplay().getText();
        if (value.equals("0") || value.equals("Error")) {
            cal.getTxtDisplay().setText("Error");
            return;
        } else {

            double tem = 1 / getValue().doubleValue();
            String txtString = (String.valueOf(tem));
            cal.getTxtDisplay().setText(tem + "");
            if (String.valueOf(tem).endsWith(".0")) {
                cal.getTxtDisplay().setText(String.valueOf(tem).substring(0, txtString.length() - 2));
            } else {
                cal.getTxtDisplay().setText(txtString);
            }
            newNumber = true;
        }
    }

    public void pressMplus() {
        newNumber = true;
        temp = temp.add(getValue());

    }

    public void pressMsub() {
        newNumber = true;
        temp = temp.subtract(getValue());
    }

    public void pressMC() {
        temp = new BigDecimal("0");
        newNumber = true;
    }

    public void pressMR() {
        // khi màn hình xuất hiện Error thì k làm gì cả
        if (cal.getTxtDisplay().getText().equals("Error")) {
            return;
        }
        cal.getTxtDisplay().setText(temp + "");
        newNumber = true;
    }

    public void frameSetting(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }

}
