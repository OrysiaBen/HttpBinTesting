import httpBinClient.HttpBinClient;

import static utils.HttpBinURL.BASE_URL;

public class Main {
    public static void main(String[] args) {
        HttpBinClient q1 = new HttpBinClient(BASE_URL);
        System.out.println(q1.Authorization("orysia", "Aadfgh0123"));
        System.out.println(q1.getAuthStatus());
    }
}