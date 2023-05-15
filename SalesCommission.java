public class SalesCommission {
 
     /**
      * This program calculates income of a commission-based salesperson
      
      * @param totalSales the total sales amount
      * @return commission-based income amount
      */
     public static double calculateCommission(double totalSales) {
         double incomeAmount = 5000; // Base salary
         while (totalSales != 0) {
             if (totalSales <= 5000) {
                 // 8% commission (Tier 1)
                 incomeAmount += totalSales * 0.08;
                 totalSales = 0;
             } else if (totalSales <= 10000) {
                 // 10% commission (Tier 2)
                 incomeAmount += (totalSales - 5000) * 0.10;
                 totalSales = 5000;
             } else {
                 // 12% commission (Tier 3)
                 incomeAmount += (totalSales - 10000) * 0.12;
                 totalSales = 10000;
             }
         }
         return incomeAmount;
     }
 
     public static void main(String[] args) {
         double totalSales = 1000;
         System.out.println("Total Sales\t\tCommission-Based Income");
         for (int i = 0; i < 20; ++i) {
             System.out.printf("$%.2f\t\t", totalSales);
             System.out.printf("$%.2f\n", calculateCommission(totalSales));
             totalSales += 1000;
         }
     }
 } 
 