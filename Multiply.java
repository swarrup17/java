import java.util.*;
public class Multiply {
    static void mul(int a){
        for(int i=0;i<11;i++){
            System.out.println(a+"*"+i+"="+(a*i));
        }
    }
    public static void main(String[] args){
        int a;
        Scanner hehe=new Scanner(System.in);
        System.out.println("Enter a number:");
        a=hehe.nextInt();
        mul(a);
        hehe.close();
    }
    
}
