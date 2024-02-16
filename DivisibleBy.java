public class DivisibleBy {
 
        public static void main(String[] args) {
            int count=0;
            int sum=0;
            
            for(int i=101; i<200;i++){
                if(i%7==0 && i%5!=0){
                    count++;
                    sum+=i;
                
                }
            }
            System.out.println("Number of integers:"+count);
            System.out.println("Sum of integers:"+sum);
            
        }
        
    }

