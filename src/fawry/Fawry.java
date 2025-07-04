package fawry;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Fawry {
   
    
   static public class Product{
        String name;
        double price;
        int quantity;
        boolean expired, ship; 
        double weight;
    
        Product(String name , double price, int quantity, boolean expired, boolean ship, double weight){
              this.name = name;
              this.price = price;
              this.quantity = quantity;
              this.expired = expired;
              this.ship = ship;
              this.weight = weight;
        }

        public String getName() { return name; }
        public double getWeight() { return weight; }
     }
    
   static public class Customer{
          int money;
          Customer(int money){
              this.money = money;
         }
    }
    
    
   static class ItemSale{
         Product pro;
         int quantity;
         
         ItemSale(Product pro,int quantity){
             this.pro = pro;
             this.quantity = quantity;
         }
    }
    
   static class Cart{
        List<ItemSale> items = new ArrayList<>();
        
        void add(Product pro, int q){
             if(q <= pro.quantity)
                 items.add(new ItemSale(pro, q));
             else
                 System.out.println("Error - the quantity is exceeded!  " + pro.name);
        }
    }
    
    
   static void checkout(Cart cart, Customer customer){
         if(cart.items.isEmpty()){
             System.out.println("Cart is empty"); 
             return;
         }
         
         long subtotal = 0, shipping = 0, amount = 0;
         
         List<Pair<String,Pair<Double,Double>>>shipable=new ArrayList<>();
            
         for (ItemSale i : cart.items) {
            if(i.pro.expired){
                System.out.println(i.pro.getName() + " expired");
                return;
            }
            
            subtotal += i.pro.price * i.quantity;
            if(i.pro.ship)
                 shipable.add(new Pair<>(i.pro.getName(),new Pair<>(i.pro.getWeight(),i.pro.price)));
        }
         
        if(!shipable.isEmpty()){
            System.out.println("\n----------------------\n ** Checkout Shipment ** \n");
            
            for(Pair<String,Pair<Double,Double>> i: shipable){
                shipping+= 1000*i.getValue().getKey();
                System.out.println(i.getKey() + ", " + i.getValue().getKey()*1000 +"Kg");
            }
        
         System.out.println("\n----------------------\n ** Checkout Receipt ** \n ");
         for(ItemSale out: cart.items)
                System.out.println("quantity = " + out.pro.quantity + " ,"+ 
                                                  "Name = " +
                                                   out.pro.getName() + " ," +
                                                  "price = " + 
                                                   out.pro.price);
         
            amount = shipping + subtotal;
            customer.money -= amount;
            
            if(customer.money<0){
                System.out.println("Error not enough money");
                return;
            }          
            
            System.out.println("----------------------\n" +
                               "Subtotal = " + subtotal + '\n' +
                               "Shipping = " + shipping + '\n' +
                               "Amount = " + amount + '\n'  );
            
            System.out.println("----------------------\n " + "Money after Sales = " + customer.money);
        }        
        
    }
    
    public static void main(String[] args) {
        Product cheese = new Product("Cheese",200, 3, false, true, 0.5);
        Product biscuits = new Product("Biscuits",150, 2, false, true, 0.9);
        Product card = new Product("ScratchCard",50, 10, false, true, 0);
        Product TV = new Product("Television",5005, 2, false, true, 30.3);

        Customer person = new Customer(1000000);
        Cart cart = new Cart();
        
        cart.add(TV, 1);
        cart.add(biscuits, 5);
        cart.add(cheese,2);
        cart.add(card, 3);
        
        checkout(cart, person);
    }
    
    
}
