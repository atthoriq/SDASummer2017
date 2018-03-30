import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Muhammad Rezki on 11/8/2017.
 */
public class SDA1606875812L6B {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Consultation consultation = new Consultation();

        String line = null;
        while((line = br.readLine()) != null){
            String input [] = line.split(" ");
            String command = input[0].toUpperCase();

            switch (command){
                case "ADD":
                    String name = input[1];
                    int slot = Integer.parseInt(input[2]);

                    if(consultation.isExist(name, slot)) {
                        System.out.println("permintaan gagal");
                        break;
                    }
                    else {
                    consultation.book(name, slot);
                    System.out.println("permintaan konsultasi oleh " + name + " di " + slot + " sukses");
                    break;
                    }
                case "GET_EVENTS":
                    name = input[1];

                    if(!consultation.isExist(name)) {
                        System.out.println("konsultasi " + name + " tidak ada");
                        break;
                    }
                    else {
                        ArrayList<Integer> events = consultation.getEventsByUser(name);

                        System.out.println("konsultasi " + name + ":");
                        for(int event: events){
                            System.out.println(event);
                        }
                        break;
                    }
                case "GET_USERS":
                    slot = Integer.parseInt(input[1]);

                    if(!consultation.isExist(slot)) {
                        System.out.println("jadwal kosong");
                        break;
                    }
                    else {
                        ArrayList<User> users = consultation.getUsersByEvent(slot);

                        System.out.println("jadwal " + slot + ":");
                        for(User user: users) {
                            System.out.println(user.getName());
                        }
                        break;
                }
            }
        }
    }
}

class Consultation {
    private static final int MAX_SIZE = 100003;
    private User[] users;
    private ArrayList<User>[] schedules;

    public Consultation(){
        this.users = new User[MAX_SIZE];
        this.schedules = (ArrayList<User>[])new ArrayList[MAX_SIZE];
    }

    public void book(String name, int slot) {
        int hashValue = hashFunction(name);
        hashValue = Math.abs(hashValue);

        User user = this.users[hashValue];
        if(user == null) {
            user = new User(name);
        }
        user.addEvent(slot);
        this.users[hashValue] = user;

        ArrayList<User> scheds = this.schedules[slot];
        if(scheds == null) {
            scheds = new ArrayList<User>();
        }
        scheds.add(user);
        this.schedules[slot] = scheds;
    }

    public boolean isExist(String name, int slot) {
        int hashValue = hashFunction(name);
        hashValue = Math.abs(hashValue);

        User user = this.users[hashValue];

        if(user != null) {
            if(user.hasEvent(slot)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExist(String name) {
        int hashValue = hashFunction(name);
        hashValue = Math.abs(hashValue);

        User user = this.users[hashValue];
        return user != null;
    }

    public boolean isExist(int slot) {
        ArrayList<User> scheds = this.schedules[slot];
        return scheds != null;
    }

    public ArrayList<Integer> getEventsByUser(String name) {
        int hashValue = hashFunction(name);
        hashValue = Math.abs(hashValue);

        User user = this.users[hashValue];
        return user.getAllEvents();
    }

    public ArrayList<User> getUsersByEvent(int slot) {
        ArrayList<User> scheds = this.schedules[slot];
        return scheds;
    }

    private int hashFunction(String st) {
        int hash = 0;
        for(int i = 0; i < st.length(); i++) {
            int val = st.charAt(i) - 'a' + 1;
            if(Character.isUpperCase(st.charAt(i))) {
                val = st.charAt(i) - 'A' + 1;
                val += 26;
            }
            hash = (53 * hash) + val;
        }
        return hash % MAX_SIZE;
    }
}

class User {
    private static final int MAX_SIZE = 100003;
    private String name;
    private boolean[] events;

    public User(String name) {
        this.name = name;
        this.events = new boolean[MAX_SIZE];
        Arrays.fill(this.events, false);
    }

    public String getName() {
        return this.name;
    }

    public void addEvent(int slot) {
        this.events[slot] = true;
    }

    public boolean hasEvent(int slot) {
        return this.events[slot];
    }

    public ArrayList<Integer> getAllEvents() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        for(int i = 0; i < MAX_SIZE; i++) {
            if(events[i]) {
                res.add(i);
            }
        }
        return res;
    }
}
