package d3;
import java.util.ArrayList;
import java.util.List;
public class Vehicle {

        private String licensePlate;
        private String colour;
        private double pricePerDay;
        private boolean isRented;
        private List<RentalRecord> rentalHistory;

        public Vehicle(String licensePlate, String colour, double pricePerDay) {
            this.licensePlate = licensePlate;
            this.colour = colour;
            this.pricePerDay = pricePerDay;
            this.isRented = false;
            this.rentalHistory = new ArrayList<>();
        }

        public double rentVehicle(Customer customer, int days) {
            if (!isRented) {
                isRented = true;
                double totalPrice = calculateRentalPrice(days);
                rentalHistory.add(new RentalRecord(customer, days, totalPrice));
                return totalPrice;
            } else {
                return -1; // Vehicle is currently rented
            }
        }

        public void returnVehicle() {
            isRented = false;
        }

        public double calculateRentalPrice(int days) {
            return pricePerDay * days;
        }

        public List<RentalRecord> getLastRentals() {
            int start = Math.max(0, rentalHistory.size() - 5);
            return rentalHistory.subList(start, rentalHistory.size());
        }

        public boolean isRented() {
            return isRented;
        }

        public String getLicensePlate() {
            return licensePlate;
        }
    }

    class Customer {
        private String name;
        private String address;
        private int age;

        public Customer(String name, String address, int age) {
            this.name = name;
            this.address = address;
            this.age = age;
        }

        public String getName() {
            return name;
        }
    }

    class RentalRecord {
        private Customer customer;
        private int days;
        private double totalPrice;

        public RentalRecord(Customer customer, int days, double totalPrice) {
            this.customer = customer;
            this.days = days;
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "Customer: " + customer.getName() + ", Days: " + days + ", Total Price: $" + totalPrice;
        }
    }

    class VehicleRentalSystem {
        private List<Vehicle> vehicles;
        private List<Customer> customers;

        public VehicleRentalSystem() {
            vehicles = new ArrayList<>();
            customers = new ArrayList<>();
        }

        public void addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
        }

        public void addCustomer(Customer customer) {
            customers.add(customer);
        }

        public Vehicle findVehicle(String licensePlate) {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getLicensePlate().equals(licensePlate)) {
                    return vehicle;
                }
            }
            return null;
        }

        public Customer findCustomer(String name) {
            for (Customer customer : customers) {
                if (customer.getName().equals(name)) {
                    return customer;
                }
            }
            return null;
        }

        public void rentVehicle(String licensePlate, String customerName, int days) {
            Vehicle vehicle = findVehicle(licensePlate);
            Customer customer = findCustomer(customerName);

            if (vehicle != null && customer != null) {
                double totalPrice = vehicle.rentVehicle(customer, days);
                if (totalPrice != -1) {
                    System.out.println(customer.getName() + " rented " + vehicle.getLicensePlate() + " for " + days + " days. Total price: $" + totalPrice + ".");
                } else {
                    System.out.println("Vehicle is currently rented out.");
                }
            } else {
                System.out.println("Vehicle or customer not found.");
            }
        }

        public void returnVehicle(String licensePlate) {
            Vehicle vehicle = findVehicle(licensePlate);
            if (vehicle != null) {
                vehicle.returnVehicle();
                System.out.println("Vehicle " + licensePlate + " returned.");
            } else {
                System.out.println("Vehicle not found.");
            }
        }

        public void getVehicleRentals(String licensePlate) {
            Vehicle vehicle = findVehicle(licensePlate);
            if (vehicle != null) {
                List<RentalRecord> rentals = vehicle.getLastRentals();
                System.out.println("Last rentals for " + licensePlate + ":");
                for (RentalRecord record : rentals) {
                    System.out.println(record);
                }
            } else {
                System.out.println("Vehicle not found.");
            }
        }

        public static void main(String[] args) {
            VehicleRentalSystem rentalSystem = new VehicleRentalSystem();

            // Add vehicles
            rentalSystem.addVehicle(new Vehicle("ABC123", "Red", 50));
            rentalSystem.addVehicle(new Vehicle("XYZ789", "Blue", 70));

            // Add customers
            rentalSystem.addCustomer(new Customer("dawa", "123 Elm St", 30));
            rentalSystem.addCustomer(new Customer("Jonathan", "456 Oak St", 25));

            // Rent a vehicle
            rentalSystem.rentVehicle("ABC123", "John Doe", 3);

            // Return a vehicle
            rentalSystem.returnVehicle("ABC123");

            // Get last rentals for a vehicle
            rentalSystem.getVehicleRentals("ABC123");
        }
    }

