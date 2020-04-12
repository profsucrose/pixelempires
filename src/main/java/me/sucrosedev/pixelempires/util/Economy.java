package me.sucrosedev.pixelempires.util;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Economy {
    private static Firestore db = FirestoreClient.getFirestore();

    private static DocumentReference getRef(String uuidString) {
        return db.collection("coins").document(uuidString);
    }

    private static void set(String uuidString, long coins) {
        Map<String, Object> data = new HashMap<>();
        data.put("value", coins);
        db.collection("coins").document(uuidString).set(data);
    }

    public static long get(String uuidString) throws ExecutionException, InterruptedException {
        DocumentReference userRef = getRef(uuidString);
        ApiFuture<DocumentSnapshot> future = userRef.get();
        DocumentSnapshot userData = future.get();

        Long currentBalance = 0L;
        if (userData.exists())
            currentBalance = (Long) userData.getData().get("value");
        return currentBalance;
    }

    public static void add(String uuidString, int coins) throws ExecutionException, InterruptedException {
        Long currentBalance = get(uuidString);
        set(uuidString, currentBalance + coins);
    }

    public static void minus(String uuidString, int coins) throws ExecutionException, InterruptedException {
        add(uuidString, -coins);
    }

    public static void exchange(String uuidStringSender, String uuidStringReceiver, int amount) throws Exception {
        Long senderBalance = get(uuidStringSender);

        if (amount > senderBalance)
            throw new Exception("Insufficient balance");

        add(uuidStringReceiver, amount);
        minus(uuidStringSender, amount);
    }
}
