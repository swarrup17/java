import java.util.Scanner;
public class SmallestOne {
    public static void main(String[] args) {
        int a[]=new int[4];
        Scanner swarup =new Scanner(System.in);
        for(int i=0;i<3;i++){
            System.out.println("Enter" +i+ "number:");
            a[i]=swarup.nextInt();
        }
        int smallest=a[0];
        for(int i=0;i<3;i++){
            if(smallest>a[i]){
                smallest=a[i];
            }
           
        }
        System.out.println("The smallest number is:"+smallest);
        swarup.close();
    }
    
}
