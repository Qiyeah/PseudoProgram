package test;

/**
 * Created by sunline on 2016/9/10.
 */
public class TestData {
    public static void main(String[] args) {
       /* byte[] bt = new byte[]{0,0,1,123};
        String str= "";
        for (int i = 0; i < bt.length; i++) {
            str += Integer.toHexString(bt[i]);
        }
        System.out.println(Integer.parseInt("17D",16)/10f);*/
        for (int i = 0; i < 300; i++) {
            System.out.print(i+" ");
        }
        System.out.println();
        for (int i = 0; i < 300; i++) {
            byte b = (byte) i;
            if (b<0){
                System.out.print((b + 256) + " ");
            }else
                System.out.print(b+" ");

        }
    }
}
