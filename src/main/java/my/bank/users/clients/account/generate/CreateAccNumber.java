package my.bank.users.clients.account.generate;

public class CreateAccNumber {
    private int id;
    private int[] value = new int[]{64, 32, 16, 8, 4, 2, 1};
    private String acc_number;

    private void create(){
        this.acc_number = "";
        int a = this.id;
        for (int i=0; i<7; i++){
            this.acc_number += a / value[i];
            a = a % value[i];
        }
    }

    public CreateAccNumber(int id){
        if(id < 127) {
            this.id = id;
            create();
        }else {
            this.acc_number = "Invalid";
        }
    }

    public String getAcc_number(){
        return this.acc_number;
    }
}
