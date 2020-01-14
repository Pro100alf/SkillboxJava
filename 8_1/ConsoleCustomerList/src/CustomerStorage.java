import java.util.HashMap;
import java.util.stream.Stream;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {
        String[] components = data.split("\\s+");
        if (components.length != 4){
            throw new IllegalArgumentException("Wrong format. Correct format: \n"
                    + "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        if (!isValidMail(components[2])){
            throw new IllegalArgumentException("Wrong email. Correct format: \n"
                    + "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        if (!isValidPhone(components[3])){
            throw new IllegalArgumentException("Wrong phone. Correct format: \n"
                    + "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        String name = components[0] + " " + components[1];
        storage.put(name, new Customer(name, components[3], components[2]));
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        if (name == null){
            throw new IllegalArgumentException("Wrong format. Correct format:\nremove Василий Петров");
        }
        if (!storage.containsKey(name)){
            throw new IllegalArgumentException("Wrong name. No customer in database");
        }
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }

    private static boolean isValidMail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    private static boolean isValidPhone (String phone){
        String regex = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        return phone.matches(regex);
    }
}